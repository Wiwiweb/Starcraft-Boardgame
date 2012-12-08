package abstraction.menus;

import java.util.ArrayList;
import java.util.List;

import tools.PlanetEntrance;
import abstraction.Area;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.Player;
import abstraction.menus.AMenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.AMenuStaticChoices.StaticChoice;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName;
import abstraction.menus.states.MultiMenuPlacePlanetChoices;

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
	protected AMenu<?> getMenu(int i) {
		AMenu<?> menu;

		switch (i) {

		case 1:
			menu = Game.factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_PLANET_TO_PLACE, player.getPlanetTokens(),
					player);
			break;

		case 2:
			menu = Game.factory.newMenuStaticChoices(StaticChoicesMenuName.ROTATE_PLANET, player);
			break;

		case 3:
			List<PlanetEntrance> availableEntrances = new ArrayList<PlanetEntrance>(galaxy.getAvailableSpots());

			// Remove entrances that cannot be linked
			List<PlanetEntrance> unavailableEntrances = new ArrayList<PlanetEntrance>();
			for (PlanetEntrance entrance : availableEntrances) {
				if (!getChoices().getChosenPlanet().isLinkable(entrance.getEntrance().opposite())) {
					unavailableEntrances.add(entrance);
				}
			}
			for (PlanetEntrance entrance : unavailableEntrances) {
				availableEntrances.remove(entrance);
			}
			menu = Game.factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_PLANET_SPOT, availableEntrances, player);
			break;

		case 4:
			menu = Game.factory.newMenuStaticChoices(StaticChoicesMenuName.PLACE_FIRST_BASE, player);
			break;

		case 5:
			menu = Game.factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_BASE_AREA, getChoices().getChosenPlanet()
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
					getChoices().getChosenPlanet().rotateClockwise();
					nextState = 2;
					break;
				case ROTATE_PLANET_COUNTERCLOCKWISE:
					getChoices().getChosenPlanet().rotateCounterClockwise();
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
			if (getChoices().getChosenSpot() == null) { // Cancel
				nextState = 2;
			} else if (player.getBaseNumber() == 0 && roundNumber != Game.NB_PLANETS_PER_PLAYER - 1) {
				nextState = 4;
			} else {
				if (player.getBaseNumber() == 1) {
					getChoices().setPlaceFirstBase(false);
					nextState = -1;
				} else {
					getChoices().setPlaceFirstBase(true);
					nextState = 5;
				}
			}
			break;

		// After Place First Base - 3 paths
		case 4:
			if (getChoices().isPlaceFirstBase() == null) { // Cancel
				nextState = 3;
			} else if (getChoices().isPlaceFirstBase() == true) {
				nextState = 5;
			} else {
				nextState = -1;
			}
			break;

		// After Choose Base Area - 2 paths
		case 5:
			if (getChoices().getChosenBaseArea() == null) { // Cancel
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
				getChoices().setChosenPlanet((Planet) menu.selectChoice());
				break;
			case 2:
				rotatePlanetChoice = (StaticChoice) menu.selectChoiceWithCancel();
				break;
			case 3:
				getChoices().setChosenSpot((PlanetEntrance) menu.selectChoiceWithCancel());
				break;
			case 4:
				StaticChoice placeFirstBaseChoice = (StaticChoice) menu.selectChoiceWithCancel();
				if (placeFirstBaseChoice == null) {
					getChoices().setPlaceFirstBase(null);
				} else if (placeFirstBaseChoice == StaticChoice.PLACE_FIRST_BASE_YES) {
					getChoices().setPlaceFirstBase(true);
				} else {
					getChoices().setPlaceFirstBase(false);
				}
				break;
			case 5:
				getChoices().setChosenBaseArea((Area) menu.selectChoiceWithCancel());
				break;
			default:
				throw new IllegalStateException("This state shouldn't happen.");
			}
			updateState();
		}
	}

	public MultiMenuPlacePlanetChoices getChoices() {
		return choices;
	}

}
