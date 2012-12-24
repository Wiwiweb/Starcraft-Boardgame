package abstraction.menus.multimenus;

import java.util.ArrayList;
import java.util.List;

import tools.PlanetEntrance;
import abstraction.Area;
import abstraction.Factory;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.Player;
import abstraction.menus.Menu;
import abstraction.menus.MenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.MenuStaticChoices.StaticChoice;
import abstraction.menus.MenuStaticChoices.StaticChoicesMenuName;
import abstraction.menus.multimenus.states.MultiMenuPlacePlanetChoices;

/**
 * @author William Gautier
 */
public class MultiMenuPlacePlanet extends MultiMenu {

	private final Galaxy galaxy;
	private final int roundNumber;
	private StaticChoice rotatePlanetChoice;

	private final MultiMenuPlacePlanetChoices choices = new MultiMenuPlacePlanetChoices();

	public MultiMenuPlacePlanet(Galaxy galaxy, int roundNumber, Player player) {
		super(player);
		this.galaxy = galaxy;
		this.roundNumber = roundNumber;
	}

	@Override
	protected Menu<?> getMenu(int i, Factory factory) {
		Menu<?> menu;

		switch (i) {

		case 1:
			menu = factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_PLANET_TO_PLACE, player.getPlanetTokens(),
					player);
			break;

		case 2:
			menu = factory.newMenuStaticChoices(StaticChoicesMenuName.ROTATE_PLANET, player);
			break;

		case 3:
			List<PlanetEntrance> availableEntrances = new ArrayList<PlanetEntrance>(galaxy.getAvailableSpots());

			// Remove entrances that cannot be linked
			List<PlanetEntrance> unavailableEntrances = new ArrayList<PlanetEntrance>();
			for (PlanetEntrance entrance : availableEntrances) {
				if (!choices.getChosenPlanet().isLinkable(entrance.getEntrance().opposite())) {
					unavailableEntrances.add(entrance);
				}
			}
			for (PlanetEntrance entrance : unavailableEntrances) {
				availableEntrances.remove(entrance);
			}
			menu = factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_PLANET_SPOT, availableEntrances, player);
			break;

		case 4:
			menu = factory.newMenuStaticChoices(StaticChoicesMenuName.PLACE_FIRST_BASE, player);
			break;

		case 5:
			menu = factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_BASE_AREA, choices.getChosenPlanet()
					.getAreas(), player);
			break;

		default:
			throw new IllegalArgumentException("No such menu in this multi menu.");
		}

		return menu;
	}

	@Override
	protected void updateState() {
		int nextState;

		switch (state) {

		// Entry Point - 1 path
		case 0:
			// Maybe make it automatic if only 1 planet
			// if (player.getPlanetTokens().size() == 1) {
			// chosenPlanet = player.getPlanetTokens().get(0);
			// nextState = 2;
			// } else {
			// nextState = 1;
			// }
			nextState = 1;
			break;

		// After Choose Planet - Only 1 path
		case 1:
			nextState = 2;
			break;

		// After Rotate Planet - 4 paths
		case 2:
			if (rotatePlanetChoice == null) { // Cancel
				nextState = 1;
			} else {

				switch (rotatePlanetChoice) {
				case ROTATE_PLANET_CLOCKWISE:
					choices.getChosenPlanet().rotateClockwise();
					nextState = 2;
					break;
				case ROTATE_PLANET_COUNTERCLOCKWISE:
					choices.getChosenPlanet().rotateCounterClockwise();
					nextState = 2;
					break;
				case ROTATE_PLANET_PLACE:
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
			if (choices.getChosenSpot() == null) { // Cancel
				nextState = 2;
			} else if (player.getBaseNumber() == 0 && roundNumber != Game.NB_PLANETS_PER_PLAYER - 1) {
				nextState = 4;
			} else {
				if (player.getBaseNumber() == 1) {
					choices.setPlaceFirstBase(false);
					nextState = -1;
				} else {
					choices.setPlaceFirstBase(true);
					nextState = 5;
				}
			}
			break;

		// After Place First Base - 3 paths
		case 4:
			if (choices.isPlaceFirstBase() == null) { // Cancel
				nextState = 3;
			} else if (choices.isPlaceFirstBase() == true) {
				nextState = 5;
			} else {
				nextState = -1;
			}
			break;

		// After Choose Base Area - 2 paths
		case 5:
			if (choices.getChosenBaseArea() == null) { // Cancel
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
	public MultiMenuPlacePlanetChoices doSelection(Factory factory) {
		updateState();
		while (state != -1) {
			Menu<?> menu = getMenu(state, factory);

			switch (state) {
			case 1:
				choices.setChosenPlanet((Planet) menu.selectChoice(false));
				break;
			case 2:
				rotatePlanetChoice = (StaticChoice) menu.selectChoice(true);
				break;
			case 3:
				choices.setChosenSpot((PlanetEntrance) menu.selectChoice(true));
				break;
			case 4:
				StaticChoice placeFirstBaseChoice = (StaticChoice) menu.selectChoice(true);
				if (placeFirstBaseChoice == null) {
					choices.setPlaceFirstBase(null);
				} else if (placeFirstBaseChoice == StaticChoice.PLACE_FIRST_BASE_YES) {
					choices.setPlaceFirstBase(true);
				} else {
					choices.setPlaceFirstBase(false);
				}
				break;
			case 5:
				choices.setChosenBaseArea((Area) menu.selectChoice(true));
				break;
			default:
				throw new IllegalStateException("This state shouldn't happen.");
			}
			updateState();
		}

		return choices;
	}

}
