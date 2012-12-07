package abstraction.menus;

import java.util.ArrayList;
import java.util.List;

import abstraction.Area;
import abstraction.Game;
import abstraction.Planet;
import abstraction.Player;
import abstraction.Route;
import abstraction.Unit;
import abstraction.menus.AMenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.AMenuStaticChoices.StaticChoice;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName;
import abstraction.menus.states.MultiMenuPlaceStartingForcesChoices;

/**
 * @author William Gautier
 */
public class MultiMenuPlaceStartingForces extends MultiMenu {

	private final Planet startingPlanet;
	private List<Unit> remainingUnits;
	private int remainingTransports;

	private StaticChoice unitPlaceRemoveChoice;
	private Unit chosenUnit;
	private Area chosenArea;
	private Route chosenRoute;

	private final MultiMenuPlaceStartingForcesChoices choices = new MultiMenuPlaceStartingForcesChoices();

	public MultiMenuPlaceStartingForces(Planet startingPlanet, List<Unit> startingUnits, int startingTransports, Player player) {
		super(player);
		this.startingPlanet = startingPlanet;
		this.remainingUnits = startingUnits;
		this.remainingTransports = startingTransports;
	}

	@Override
	protected AMenu<?> getMenu(int i) {
		AMenu<?> menu;

		switch (i) {

		case 1:
			List<StaticChoice> disabledChoices = new ArrayList<StaticChoice>();
			if (remainingUnits.isEmpty()) {
				disabledChoices.add(StaticChoice.PLACE_REMOVE_UNIT_PLACE_UNIT);
			}
			if (getChoices().getPlacedUnits().isEmpty()) {
				disabledChoices.add(StaticChoice.PLACE_REMOVE_UNIT_REMOVE_UNIT);
			}
			if (remainingTransports == 0) {
				disabledChoices.add(StaticChoice.PLACE_REMOVE_UNIT_PLACE_TRANSPORT);
			}
			if (getChoices().getPlacedTransports().isEmpty()) {
				disabledChoices.add(StaticChoice.PLACE_REMOVE_UNIT_REMOVE_TRANSPORT);
			}

			menu = Game.factory.newMenuStaticChoices(StaticChoicesMenuName.PLACE_REMOVE_UNIT, disabledChoices, player);
			break;

		case 2:
			menu = Game.factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_UNIT_TO_PLACE, remainingUnits, player);
			break;

		case 3:
			menu = Game.factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_UNIT_PLACEMENT,
					startingPlanet.getBuildableAreasPlusUnits(player, getChoices().getPlacedUnitsAreas()), player);
			break;

		case 4:
			menu = Game.factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_UNIT_TO_REMOVE,
					getChoices().getPlacedUnits(), player);
			break;

		case 5:
			menu = Game.factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_TRANSPORT_PLACEMENT,
					startingPlanet.getRoutesWithNoTransports(player), player);
			break;

		case 6:
			menu = Game.factory
					.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_TRANSPORT_PLACEMENT, getChoices().getPlacedTransports(),
							player);
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
			nextState = 1;
			break;

		// After Choose Unit/Transport Place/Remove - 4 paths
		case 1:
			switch (unitPlaceRemoveChoice) {
			case PLACE_REMOVE_UNIT_PLACE_UNIT:
				nextState = 2;
				break;
			case PLACE_REMOVE_UNIT_REMOVE_UNIT:
				nextState = 4;
				break;
			case PLACE_REMOVE_UNIT_PLACE_TRANSPORT:
				nextState = 5;
				break;
			case PLACE_REMOVE_UNIT_REMOVE_TRANSPORT:
				nextState = 6;
				break;
			default:
				throw new IllegalStateException("This menu should not return this value.");
			}
			break;

		// After Choose Unit to Place - 2 paths
		case 2:
			if (chosenUnit == null) { // Cancel
				nextState = 1;
			} else {
				nextState = 3;
			}
			break;

		// After Choose Area to place unit - 3 paths
		case 3:
			if (chosenArea == null) { // Cancel
				nextState = 2;
			} else {
				getChoices().getPlacedUnits().add(chosenUnit);
				getChoices().getPlacedUnitsAreas().add(chosenArea);
				if (!remainingUnits.remove(chosenUnit)) {
					throw new IllegalStateException();
				}
				if (remainingUnits.isEmpty() && remainingTransports == 0) { // It's over
					nextState = -1;
				} else {
					nextState = 1;
				}
			}
			break;

		// After Choose Unit to Remove - 1 path but 2 choices
		case 4:
			if (chosenUnit == null) { // Cancel
				nextState = 1;
			} else {
				int index = getChoices().getPlacedUnits().indexOf(chosenUnit);
				if (index == -1) {
					throw new IllegalStateException();
				}
				getChoices().getPlacedUnits().remove(index);
				getChoices().getPlacedUnitsAreas().remove(index);
				remainingUnits.add(chosenUnit);
				nextState = 1;
			}
			break;

		// After Choose Route to place transport - 2 path but 3 choices
		case 5:
			if (chosenRoute == null) { // Cancel
				nextState = 1;
			} else {
				getChoices().getPlacedTransports().add(chosenRoute);
				remainingTransports--;
				if (remainingUnits.isEmpty() && remainingTransports == 0) { // It's over
					nextState = -1;
				} else {
					nextState = 1;
				}
			}
			break;

		// After Choose Route to remove transport - 1 path but 2 choices
		case 6:
			if (chosenRoute == null) { // Cancel
				nextState = 1;
			} else {
				if (!getChoices().getPlacedTransports().remove(chosenRoute)) {
					throw new IllegalStateException();
				}
				remainingTransports++;
				nextState = 1;
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
				unitPlaceRemoveChoice = (StaticChoice) menu.selectChoice();
				break;
			case 2:
				chosenUnit = (Unit) menu.selectChoiceWithCancel();
				break;
			case 3:
				chosenArea = (Area) menu.selectChoiceWithCancel();
				break;
			case 4:
				chosenUnit = (Unit) menu.selectChoiceWithCancel();
				break;
			case 5:
				chosenRoute = (Route) menu.selectChoiceWithCancel();
				break;
			case 6:
				chosenRoute = (Route) menu.selectChoiceWithCancel();
				break;
			default:
				throw new IllegalStateException("This state shouldn't happen.");
			}
			updateState();
		}
	}

	public MultiMenuPlaceStartingForcesChoices getChoices() {
		return choices;
	}

}
