package abstraction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import presentation.IHM;
import presentation.text.TextIHM;
import tools.PlanetEntrance;
import abstraction.Resource.ResourceType;
import abstraction.creators.FactionCreator;
import abstraction.creators.PlanetCreator;
import abstraction.creators.UnitCreator;
import abstraction.menus.AMenuChooseFromList;
import abstraction.menus.AMenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.MultiMenuPlacePlanet;
import abstraction.menus.MultiMenuPlaceStartingForces;
import abstraction.menus.MultiMenuPlaceZAxis;
import control.text.CFactory;

/**
 * @author William Gautier
 */
public class Game {

	public static boolean IS_TEST = false;

	public final static int NB_PLANETS_PER_PLAYER = 2;

	public static IHM ihm = new TextIHM();
	public static AFactory factory = new CFactory();

	private final List<Player> playerList = new ArrayList<Player>();
	private final Galaxy galaxy = new Galaxy();

	private Player firstPlayer = null;

	public void setupGame() {

		final int numberOfPlayers = getPlayerList().size();

		// 1. Choose the First Player
		final int firstPlayerIndex = (int) (Math.random() * numberOfPlayers);
		setFirstPlayer(getPlayerList().get(firstPlayerIndex));

		// 2. Choose Factions
		List<Faction> factionList = FactionCreator.getFactionList();
		for (Player p : getPlayerListByOrder()) {

			AMenuChooseFromList<Faction> menu = Game.factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_FACTION,
					factionList, p);
			Faction faction = menu.selectChoice();
			p.setFaction(faction);
			factionList.remove(faction);
		}

		// 3. Gather Faction Components - DONE
		// 4. Place Conquest Point Track - DONE
		// 5. Place Depletion Tokens - DONE

		// 6. Prepare the Event Deck
		// TODO

		// 7. Set Up the Galaxy
		setUpGalaxy();

		// 8. Separate Technology Cards from Combat Cards - DONE

		// 9. Draw Combat Cards
		// TODO

	}

	private void setUpGalaxy() {

		final List<Player> orderedPlayerList = getPlayerListByOrder();

		// 1. Receive Planets
		List<String> planetNamesList = PlanetCreator.getPlanetNamesList();
		for (Player p : orderedPlayerList) {
			for (int i = 0; i < NB_PLANETS_PER_PLAYER; i++) {
				final int randomPlanetIndex = (int) (Math.random() * planetNamesList.size());
				final String randomPlanetName = planetNamesList.get(randomPlanetIndex);
				p.addPlanetToken(PlanetCreator.createPlanet(randomPlanetName));
				planetNamesList.remove(randomPlanetName);
			}
		}

		// 2. Return Unused Components - DONE

		// 3. First Round of Planet Placement
		// 4. Second Round of Planet Placement
		placePlanets(orderedPlayerList);

		// 5. Place Z-Axis navigation Routes
		placeZAxis(orderedPlayerList);

		// 6. Distribute Resource Cards - DONE

		// 7. Place Starting Forces
		placeStartingForces(orderedPlayerList);
	}

	private void placePlanets(final List<Player> orderedPlayerList) {
		ListIterator<Player> it = orderedPlayerList.listIterator();
		boolean continueToNextPlayer;
		Player player;
		for (int i = 0; i < NB_PLANETS_PER_PLAYER; i++) {

			continueToNextPlayer = true;
			while (continueToNextPlayer) {

				// If the round is even
				if (i % 2 == 0) {
					// Player order is normal
					player = it.next();
					continueToNextPlayer = it.hasNext();
				} else {
					// Player order is reversed
					player = it.previous();
					continueToNextPlayer = it.hasPrevious();
				}

				MultiMenuPlacePlanet placePlanetMenu = new MultiMenuPlacePlanet(galaxy, i, player);
				placePlanetMenu.doSelection();

				Planet chosenPlanet = placePlanetMenu.getChosenPlanet();
				if (galaxy.isEmpty()) {
					galaxy.add(chosenPlanet);
				} else {
					galaxy.add(chosenPlanet, placePlanetMenu.getChosenSpot());
				}
				player.removePlanetToken(chosenPlanet);

				if (placePlanetMenu.isPlaceFirstBase()) {
					player.placeBase(placePlanetMenu.getChosenBaseArea());
					player.setStartingPlanet(placePlanetMenu.getChosenBaseArea().getPlanet());

					for (Area a : chosenPlanet.getAreas()) {
						if (a.getResource().getResourceType() != ResourceType.CONTROL) {
							player.addControlledResource(a.getResource());
						}
					}
				}
			}
		}
	}

	private void placeZAxis(final List<Player> orderedPlayerList) {
		ListIterator<Player> it = orderedPlayerList.listIterator();
		boolean hasLegalSpot = true;
		while (it.hasNext() && hasLegalSpot) {
			Player player = it.next();
			List<PlanetEntrance> availableEntrances = galaxy.getAvailableSpots();

			if (availableEntrances.size() < 2) {
				hasLegalSpot = false;
				break;
			} else {

				Iterator<PlanetEntrance> spotIt = availableEntrances.iterator();

				checkLegal:
				while (spotIt.hasNext()) {
					PlanetEntrance entrance = spotIt.next();
					List<PlanetEntrance> otherEntrances = availableEntrances;
					otherEntrances.remove(entrance);
					hasLegalSpot = false;
					for (PlanetEntrance p : otherEntrances) {
						if (entrance.getPlanet() != p.getPlanet()) {
							hasLegalSpot = true;
							break checkLegal;
						}
					}
				}

			}

			if (hasLegalSpot) {
				MultiMenuPlaceZAxis placeZAxisMenu = new MultiMenuPlaceZAxis(galaxy, player);
				placeZAxisMenu.doSelection();
				PlanetEntrance entrance = placeZAxisMenu.getEntrance();
				PlanetEntrance exit = placeZAxisMenu.getExit();
				entrance.getPlanet().connect(exit.getPlanet(), entrance.getEntrance(), exit.getEntrance(), true);
			}

		}
	}

	private void placeStartingForces(final List<Player> orderedPlayerList) {
		ListIterator<Player> it = orderedPlayerList.listIterator();
		while (it.hasNext()) {

			Player player = it.next();
			Planet startingPlanet = player.getStartingPlanet();
			List<Unit> startingUnits = new ArrayList<Unit>();

			for (int i = 0; i < player.getFaction().getStartingUnitTypes().length; i++) {

				String unitType = player.getFaction().getStartingUnitTypes(i);
				for (int j = 0; j < player.getFaction().getStartingUnitNumbers(i); j++) {
					startingUnits.add(UnitCreator.createUnit(unitType, player));
				}
			}

			MultiMenuPlaceStartingForces placeForcesMenu = new MultiMenuPlaceStartingForces(startingPlanet, startingUnits,
					player.getFaction().getStartingTransports(), player);
			placeForcesMenu.doSelection();

			for (int i = 0; i < placeForcesMenu.getPlacedUnits().size(); i++) {
				Unit unit = placeForcesMenu.getPlacedUnits().get(i);
				Area area = placeForcesMenu.getPlacedUnitsAreas().get(i);
				area.addUnit(unit);
			}

			for (Route r : placeForcesMenu.getTransportsPlaced()) {
				r.addTransport(player);
			}
		}
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public List<Player> getPlayerListByOrder() {
		@SuppressWarnings("unchecked")
		List<Player> orderedPlayerList = (ArrayList<Player>) ((ArrayList<Player>) playerList).clone();

		Player nextPlayer = null;
		while (nextPlayer != firstPlayer) {
			nextPlayer = orderedPlayerList.get(0);
			if (nextPlayer != firstPlayer) {
				orderedPlayerList.remove(nextPlayer);
				orderedPlayerList.add(nextPlayer);
			}
		}
		return orderedPlayerList;
	}

	public void addPlayer(Player player) {
		this.playerList.add(player);
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(Player firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public Galaxy getGalaxy() {
		return galaxy;
	}
}
