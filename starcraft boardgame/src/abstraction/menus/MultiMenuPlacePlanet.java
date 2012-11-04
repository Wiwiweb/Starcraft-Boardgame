package abstraction.menus;

import java.util.ArrayList;
import java.util.List;

import tools.PlanetEntrance;
import abstraction.Area;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.Player;
import abstraction.menus.AMenu.MenuName;

public class MultiMenuPlacePlanet extends MultiMenu {

	private final Galaxy galaxy;
	private final int roundNumber;
	private Integer rotatePlanetSelection;

	private Planet chosenPlanet;
	private PlanetEntrance chosenSpot;
	private Boolean placeFirstBase;
	private Area chosenBaseArea;

	private MenuName[] menuNames = { MenuName.SELECT_PLANET_TO_PLACE, MenuName.ROTATE_PLANET, MenuName.SELECT_PLANET_SPOT,
			MenuName.PLACE_FIRST_BASE, MenuName.SELECT_BASE_AREA };

	public MultiMenuPlacePlanet(Galaxy galaxy, int roundNumber, Player player) {
		super(player);
		this.galaxy = galaxy;
		this.roundNumber = roundNumber;
	}

	@Override
	public AMenu<?> getMenu(int i) {
		AMenu<?> menu;
		switch (i) {

		case 1:
			menu = Game.factory.newMenuChooseFromList(menuNames[0], player.getPlanetTokens(), player);
			break;

		case 2:
			menu = Game.factory.newMenuStaticChoices(menuNames[1], player);
			break;

		case 3:
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
			menu = Game.factory.newMenuChooseFromList(menuNames[2], availableEntrances, player);
			break;

		case 4:
			menu = Game.factory.newMenuStaticChoices(menuNames[3], player);
			break;

		case 5:
			menu = Game.factory.newMenuChooseFromList(menuNames[4], chosenPlanet.getAreas(), player);
			break;

		default:
			throw new IllegalArgumentException("No such menu in this multi menu.");
		}

		return menu;
	}

	@Override
	public void updateState() {
		int nextState;

		switch (state) {

		// Entry Point - 2 paths
		case 0:
			if (player.getPlanetTokens().size() == 1) {
				chosenPlanet = player.getPlanetTokens().get(0);
				nextState = 2;
			} else {
				nextState = 1;
			}
			break;

		// After Choose Planet - Only 1 path
		case 1:
			nextState = 2;
			break;

		// After Rotate Planet - 4 paths
		case 2:
			if (rotatePlanetSelection == null) { // Cancel
				nextState = 1;
			} else {

				switch (rotatePlanetSelection) {
				case 1:
					chosenPlanet.rotateClockwise();
					nextState = 2;
					break;
				case 2:
					chosenPlanet.rotateCounterClockwise();
					nextState = 2;
					break;
				case 3:
					if (galaxy.isEmpty()) {
						nextState = 4;
					} else {
						nextState = 3;
					}
					break;
				default:
					throw new IllegalStateException("This menu should not return this value.");
				}
			}
			break;

		// After Place Planet - 4 paths
		case 3:
			if (chosenSpot == null) { // Cancel
				nextState = 2;
			} else if (player.getBaseNumber() == 0 && roundNumber != Game.NB_PLANETS_PER_PLAYER - 1) {
				nextState = 4;
			} else {
				if (player.getBaseNumber() == 1) {
					placeFirstBase = false;
					nextState = -1;
				} else {
					placeFirstBase = true;
					nextState = 5;
				}
			}
			break;

		// After Place First Base - 3 paths
		case 4:
			if (placeFirstBase == null) { // Cancel
				nextState = 3;
			} else if (placeFirstBase == true) {
				nextState = 5;
			} else {
				nextState = -1;
			}
			break;

		// After Choose Base Area - 2 paths
		case 5:
			if (chosenBaseArea == null) { // Cancel
				nextState = 4;
			} else {
				nextState = -1;
			}
			break;

		default:
			throw new IllegalStateException("This state shouldn't happen.");
		}

		state = nextState;
	}

	@Override
	public void doSelection() {
		updateState();
		while (state != -1) {
			AMenu<?> menu = getMenu(state);

			switch (state) {
			case 1:
				chosenPlanet = (Planet) menu.selectChoice();
				break;
			case 2:
				rotatePlanetSelection = (Integer) menu.selectChoiceWithCancel();
				break;
			case 3:
				chosenSpot = (PlanetEntrance) menu.selectChoiceWithCancel();
				break;
			case 4:
				Integer placeFirstBaseInt = (Integer) menu.selectChoiceWithCancel();
				if (placeFirstBaseInt == null) {
					placeFirstBase = null;
				} else if (placeFirstBaseInt == 1) {
					placeFirstBase = true;
				} else {
					placeFirstBase = false;
				}
				break;
			case 5:
				chosenBaseArea = (Area) menu.selectChoiceWithCancel();
				break;
			default:
				throw new IllegalStateException("This state shouldn't happen.");
			}
			updateState();
		}
	}

	public Planet getChosenPlanet() {
		return chosenPlanet;
	}

	public PlanetEntrance getChosenSpot() {
		return chosenSpot;
	}

	public boolean isPlaceFirstBase() {
		return placeFirstBase;
	}

	public Area getChosenBaseArea() {
		return chosenBaseArea;
	}
}
