package abstraction;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import presentation.IHM;
import presentation.text.TextIHM;
import tools.PlanetEntrance;
import abstraction.creators.FactionCreator;
import abstraction.creators.PlanetCreator;
import abstraction.menus.AMenu.MenuName;
import abstraction.menus.AMenuChooseFromList;
import abstraction.menus.MultiMenu;
import abstraction.menus.MultiMenuPlacePlanet;
import control.text.CFactory;

public class Game {

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

			AMenuChooseFromList<Faction> menu = Game.factory.newMenuChooseFromList(MenuName.SELECT_FACTION, factionList, p);
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
				}

				// // Choose planet
				// if (player.getPlanetTokens().size() == 1) {
				// chosenPlanet = player.getPlanetTokens().get(0);
				// } else {
				// chosenPlanet = Game.ihm.selectPlanetToPlace(player, player.getPlanetTokens());
				// }
				//
				// // Rotate planet
				// boolean stopLooping = false;
				// while (!stopLooping) {
				//
				// int rotation = Game.ihm.choosePlanetRotation(player, chosenPlanet);
				// switch (rotation) {
				// case 1:
				// chosenPlanet.rotateClockwise();
				// break;
				// case 2:
				// chosenPlanet.rotateCounterClockwise();
				// break;
				// case 3:
				// stopLooping = true;
				// break;
				// case 4:
				// // TODO cancel
				// break;
				// default:
				// throw new IllegalStateException("This should never happen.");
				// }
				//
				// }
				//
				// // Choose where to place the planet
				// if (galaxy.isEmpty()) {
				// galaxy.add(chosenPlanet);
				// } else {
				// List<PlanetEntrance> availableEntrances = galaxy.getAvailableSpots();
				//
				// // Remove entrances that cannot be linked
				// List<PlanetEntrance> unavailableEntrances = new ArrayList<PlanetEntrance>();
				// for (PlanetEntrance entrance : availableEntrances) {
				// if (!chosenPlanet.isLinkable(entrance.getEntrance().opposite())) {
				// unavailableEntrances.add(entrance);
				// }
				// }
				// for (PlanetEntrance entrance : unavailableEntrances) {
				// availableEntrances.remove(entrance);
				// }
				//
				// PlanetEntrance chosenSpot = Game.ihm.selectSpotToPlacePlanet(player, availableEntrances,
				// chosenPlanet);
				// if (chosenSpot == null) {
				// // TODO cancel
				// }
				//
				// galaxy.add(chosenPlanet, chosenSpot);
				// player.removePlanetToken(chosenPlanet);
				// }
				//
				// // Ask to place a base (except if it's the last round)
				// boolean placeBase = false;
				// if (player.getBaseNumber() == 0 && i != NB_PLANETS_PER_PLAYER - 1) {
				// placeBase = Game.ihm.askToPlaceBase(player, chosenPlanet);
				// }
				//
				// // Place base if it hasn't been placed yet, and we chose to place it or it's the last round
				// if (player.getBaseNumber() == 0 && (placeBase || i == NB_PLANETS_PER_PLAYER - 1)) {
				// Area chosenArea = Game.ihm.selectAreaToPlaceBase(player, chosenPlanet.getAreas());
				// player.placeBase(chosenArea);
				// }
			}
		}

		System.out.println(galaxy.toString());

		// 5. Place Z-Axis navigation Routes
		it = orderedPlayerList.listIterator();

		while (it.hasNext()) {
			player = it.next();
			List<PlanetEntrance> availableEntrances = galaxy.getAvailableSpots();
			// TODO

		}
		// 6. Distribute Resource Cards
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
}
