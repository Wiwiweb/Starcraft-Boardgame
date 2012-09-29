package abstraction;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import presentation.IHM;
import presentation.text.TextIHM;
import tools.PlanetEntrance;
import abstraction.creators.FactionCreator;
import abstraction.creators.PlanetCreator;

public class Game {

	private final int NB_PLANETS_PER_PLAYER = 2;

	public static IHM ihm = new TextIHM();

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
			Faction faction = p.selectFactionFromList(factionList);
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
		Player nextPlayer;
		for (int i = 0; i < NB_PLANETS_PER_PLAYER; i++) {

			continueToNextPlayer = true;
			while (continueToNextPlayer) {

				Planet chosenPlanet;

				// If the round is even
				if (i % 2 == 0) {
					// Player order is normal
					nextPlayer = it.next();
					continueToNextPlayer = it.hasNext();
				} else {
					// Player order is reversed
					nextPlayer = it.previous();
					continueToNextPlayer = it.hasPrevious();
				}

				// Choose planet
				if (nextPlayer.getPlanetTokens().size() == 1) {
					chosenPlanet = nextPlayer.getPlanetTokens().get(0);
				} else {
					chosenPlanet = Game.ihm.selectPlanetToPlace(nextPlayer, nextPlayer.getPlanetTokens());
				}

				// Rotate planet
				boolean stopLooping = false;
				while (!stopLooping) {

					int rotation = Game.ihm.choosePlanetRotation(nextPlayer, chosenPlanet);
					switch (rotation) {
					case 1:
						chosenPlanet.rotateClockwise();
						break;
					case 2:
						chosenPlanet.rotateCounterClockwise();
						break;
					case 3:
						stopLooping = true;
						break;
					case 4:
						// TODO cancel
						break;
					default:
						throw new IllegalStateException("This should never happen.");
					}

				}

				// Choose where to place the planet
				if (galaxy.isEmpty()) {
					galaxy.add(chosenPlanet);
				} else {
					List<PlanetEntrance> availableEntrances = galaxy.getAvailableSpots();

					// Remove entrances that cannot be linked
					List<PlanetEntrance> unavailableEntrances = new ArrayList<PlanetEntrance>();
					for (PlanetEntrance entrance : availableEntrances) {
						if (!chosenPlanet.isLinkable(entrance.getEntrance().opposite())) {
							unavailableEntrances.add(entrance);
						}
					}
					for (PlanetEntrance entrance : unavailableEntrances) {
						availableEntrances.remove(entrance);
					}

					PlanetEntrance chosenSpot = Game.ihm.selectSpotToPlacePlanet(nextPlayer, availableEntrances, chosenPlanet);
					if (chosenSpot == null) {
						// TODO cancel
					}

					galaxy.add(chosenPlanet, chosenSpot);
					nextPlayer.removePlanetToken(chosenPlanet);
				}

				// Ask to place a base (except if it's the last round)
				boolean placeBase = false;
				if (nextPlayer.getBaseNumber() == 0 && i != NB_PLANETS_PER_PLAYER - 1) {
					placeBase = Game.ihm.askToPlaceBase(nextPlayer, chosenPlanet);
				}

				// Place base if it hasn't been placed yet, and we chose to place it or it's the last round
				if (nextPlayer.getBaseNumber() == 0 && (placeBase || i == NB_PLANETS_PER_PLAYER - 1)) {
					Area chosenArea = Game.ihm.selectAreaToPlaceBase(nextPlayer, chosenPlanet.getAreas());
					nextPlayer.placeBase(chosenArea);
				}
			}
		}

		System.out.println(galaxy.toString());

		// 5. Place Z-Axis navigation Routes
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
