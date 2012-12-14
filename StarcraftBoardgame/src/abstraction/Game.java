package abstraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import presentation.IHM;
import presentation.text.TextIHM;
import tools.PlanetEntrance;
import abstraction.Order.OrderType;
import abstraction.Resource.ResourceType;
import abstraction.creators.FactionCreator;
import abstraction.creators.PlanetCreator;
import abstraction.menus.MenuChooseFromList;
import abstraction.menus.MenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.multimenus.MultiMenuPlaceOrder;
import abstraction.menus.multimenus.MultiMenuPlacePlanet;
import abstraction.menus.multimenus.MultiMenuPlaceStartingForces;
import abstraction.menus.multimenus.MultiMenuPlaceZAxis;
import abstraction.menus.multimenus.states.MultiMenuPlaceOrderChoices;
import abstraction.menus.multimenus.states.MultiMenuPlacePlanetChoices;
import abstraction.menus.multimenus.states.MultiMenuPlaceStartingForcesChoices;
import abstraction.menus.multimenus.states.MultiMenuPlaceZAxisChoices;
import control.text.CFactory;

/**
 * @author William Gautier
 */
public class Game {

	public static boolean IS_TEST = false;

	public static enum Phase {
		PLANNING, EXECUTION, REGROUPING
	}

	public final static int NB_PLANETS_PER_PLAYER = 2;
	public final static int NB_ORDERS_PER_PLAYER = 4;
	public final static int NB_ORDERS_OF_EACH_TYPE = 2;
	public final static int NB_SPECIAL_ORDERS_OF_EACH_TYPE = 1;

	public static IHM ihm = new TextIHM();
	public static Factory factory = new CFactory();

	private final List<Player> playerList = new ArrayList<Player>();
	private final Galaxy galaxy = factory.newGalaxy();

	private Player firstPlayer = null;
	private int roundNumber = 0;
	private Phase currentPhase = null;

	public void setupGame() {

		final int numberOfPlayers = getPlayerList().size();

		// 1. Choose the First Player
		final int firstPlayerIndex = (int) (Math.random() * numberOfPlayers);
		setFirstPlayer(getPlayerList().get(firstPlayerIndex));

		// 2. Choose Factions
		List<Faction> factionList = FactionCreator.getFactionList();
		for (Player p : getPlayerListByOrder()) {

			MenuChooseFromList<Faction> menu = Game.factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_FACTION,
					factionList, p);
			Faction faction = menu.selectChoice();
			p.setFaction(faction, factory);
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

		startGame();

	}

	private void setUpGalaxy() {

		final List<Player> orderedPlayerList = getPlayerListByOrder();

		// 1. Receive Planets
		List<String> planetNamesList = PlanetCreator.getPlanetNamesList();
		for (Player p : orderedPlayerList) {
			for (int i = 0; i < NB_PLANETS_PER_PLAYER; i++) {
				final int randomPlanetIndex = (int) (Math.random() * planetNamesList.size());
				final String randomPlanetName = planetNamesList.get(randomPlanetIndex);
				p.addPlanetToken(factory.newPlanet(randomPlanetName));
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
				MultiMenuPlacePlanetChoices choices = placePlanetMenu.doSelection();

				Planet chosenPlanet = choices.getChosenPlanet();
				if (galaxy.isEmpty()) {
					galaxy.add(chosenPlanet);
				} else {
					galaxy.add(chosenPlanet, choices.getChosenSpot(), factory);
				}
				player.removePlanetToken(chosenPlanet);

				if (choices.isPlaceFirstBase()) {
					player.placeBase(choices.getChosenBaseArea());
					player.setStartingPlanet(choices.getChosenBaseArea().getPlanet());

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
			Set<PlanetEntrance> availableEntrances = galaxy.getAvailableSpots();

			if (availableEntrances.size() < 2) {
				break;
			} else {

				hasLegalSpot = false;
				checkLegal:
				for (PlanetEntrance p : availableEntrances) {
					Set<PlanetEntrance> otherEntrances = new HashSet<PlanetEntrance>(availableEntrances);
					otherEntrances.remove(p);

					for (PlanetEntrance p2 : otherEntrances) {
						if (p.getPlanet() != p2.getPlanet()) {
							hasLegalSpot = true;
							break checkLegal;
						}
					}
				}

			}

			if (hasLegalSpot) {
				MultiMenuPlaceZAxis placeZAxisMenu = new MultiMenuPlaceZAxis(galaxy.getAvailableSpots(), player);
				MultiMenuPlaceZAxisChoices choices = placeZAxisMenu.doSelection();
				PlanetEntrance entrance = choices.getEntrance();
				PlanetEntrance exit = choices.getExit();
				entrance.getPlanet().connect(exit.getPlanet(), entrance.getEntrance(), exit.getEntrance(), true, factory);
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
					startingUnits.add(factory.newUnit(unitType, player));
				}
			}

			MultiMenuPlaceStartingForces placeForcesMenu = new MultiMenuPlaceStartingForces(startingPlanet, startingUnits,
					player.getFaction().getStartingTransports(), player);
			MultiMenuPlaceStartingForcesChoices choices = placeForcesMenu.doSelection();

			for (int i = 0; i < choices.getPlacedUnits().size(); i++) {
				Unit unit = choices.getPlacedUnits().get(i);
				Area area = choices.getPlacedUnitsAreas().get(i);
				area.addUnit(unit);
			}

			for (Route r : choices.getPlacedTransports()) {
				r.addTransport(player);
			}
		}
	}

	private void startGame() {
		roundNumber = 1;
		boolean gameIsOver = false;

		while (!gameIsOver) {
			List<Player> orderedPlayerList = getPlayerListByOrder();
			planningPhase(orderedPlayerList);
			// TODO
		}

	}

	private void planningPhase(final List<Player> orderedPlayerList) {
		currentPhase = Phase.PLANNING;

		for (Player p : orderedPlayerList) {
			p.setOrdersLeftToPlace(NB_ORDERS_PER_PLAYER);
		}

		boolean ordersRemaining = true;

		while (ordersRemaining) {

			ordersRemaining = false;
			ListIterator<Player> it = orderedPlayerList.listIterator();
			while (it.hasNext()) {
				Player player = it.next();

				if (player.getOrdersLeftToPlace() > 0) {
					ordersRemaining = true;

					// Get available orders
					Set<OrderType> availableOrders = new HashSet<OrderType>(Arrays.asList(OrderType.values()));
					if (player.getOrderTypePlaced(OrderType.BUILD) >= Game.NB_ORDERS_OF_EACH_TYPE) {
						availableOrders.remove(OrderType.BUILD);
					}
					if (player.getOrderTypePlaced(OrderType.MOBILIZE) >= Game.NB_ORDERS_OF_EACH_TYPE) {
						availableOrders.remove(OrderType.MOBILIZE);
					}
					if (player.getOrderTypePlaced(OrderType.RESEARCH) >= Game.NB_ORDERS_OF_EACH_TYPE) {
						availableOrders.remove(OrderType.RESEARCH);
					}

					if (player.getSpecialOrdersAvailable() <= 0) {
						availableOrders.remove(OrderType.SPECIAL_BUILD);
						availableOrders.remove(OrderType.SPECIAL_MOBILIZE);
						availableOrders.remove(OrderType.SPECIAL_RESEARCH);
					} else {
						if (player.getOrderTypePlaced(OrderType.SPECIAL_BUILD) >= Game.NB_SPECIAL_ORDERS_OF_EACH_TYPE) {
							availableOrders.remove(OrderType.SPECIAL_BUILD);
						}
						if (player.getOrderTypePlaced(OrderType.SPECIAL_MOBILIZE) >= Game.NB_SPECIAL_ORDERS_OF_EACH_TYPE) {
							availableOrders.remove(OrderType.SPECIAL_MOBILIZE);
						}
						if (player.getOrderTypePlaced(OrderType.SPECIAL_RESEARCH) >= Game.NB_SPECIAL_ORDERS_OF_EACH_TYPE) {
							availableOrders.remove(OrderType.SPECIAL_RESEARCH);
						}
					}

					MultiMenuPlaceOrder placeOrderMenu = new MultiMenuPlaceOrder(galaxy.getAvailableOrderPlanets(player),
							availableOrders, player);
					MultiMenuPlaceOrderChoices choices = placeOrderMenu.doSelection();

					player.decrementOrdersLeftToPlace();
					Planet planet = choices.getPlanet();
					OrderType orderType = choices.getOrderType();
					planet.addOrderToTop(factory.newOrder(orderType, player, planet));
				}

			}

		}
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public List<Player> getPlayerListByOrder() {
		List<Player> orderedPlayerList = new ArrayList<Player>(playerList);

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
