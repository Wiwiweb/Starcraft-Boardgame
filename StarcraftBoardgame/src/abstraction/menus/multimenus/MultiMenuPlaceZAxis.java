package abstraction.menus.multimenus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tools.PlanetEntrance;
import abstraction.Factory;
import abstraction.Planet;
import abstraction.Player;
import abstraction.menus.Menu;
import abstraction.menus.MenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.multimenus.states.MultiMenuPlaceZAxisChoices;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class MultiMenuPlaceZAxis extends MultiMenu {

	private final Set<PlanetEntrance> availableEntrances;

	private final MultiMenuPlaceZAxisChoices choices = new MultiMenuPlaceZAxisChoices();

	public MultiMenuPlaceZAxis(Set<PlanetEntrance> availableEntrances, Player player) {
		super(player);
		this.availableEntrances = availableEntrances;
	}

	@Override
	protected Menu<?> getMenu(int i, Factory factory) {
		Menu<?> menu;

		switch (i) {

		case 1:
			menu = factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_ZAXIS_ENTRANCE, availableEntrances, player);
			break;

		case 2:
			List<PlanetEntrance> availableExits = new ArrayList<PlanetEntrance>(availableEntrances);
			availableExits.remove(choices.getEntrance());
			Planet planet = choices.getEntrance().getPlanet();
			for (Cardinal c : planet.getLinkableEntrances()) {
				availableExits.remove(new PlanetEntrance(planet, c));
			}

			menu = factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_ZAXIS_EXIT, availableExits, player);
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

		// After Select Entrance - 1 path
		case 1:
			nextState = 2;
			break;

		// After Select Exit - 2 paths
		case 2:
			if (choices.getExit() == null) {
				nextState = 1;
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
	public MultiMenuPlaceZAxisChoices doSelection(Factory factory) {
		updateState();
		while (state != -1) {
			Menu<?> menu = getMenu(state, factory);
			switch (state) {
			case 1:
				choices.setEntrance((PlanetEntrance) menu.selectChoice(false));
				break;
			case 2:
				choices.setExit((PlanetEntrance) menu.selectChoice(true));
				break;
			default:
				throw new IllegalStateException("This state shouldn't happen.");
			}
			updateState();
		}

		return choices;
	}

}
