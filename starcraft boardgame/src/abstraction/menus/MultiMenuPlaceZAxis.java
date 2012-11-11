package abstraction.menus;

import java.util.ArrayList;
import java.util.List;

import tools.PlanetEntrance;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.Player;
import abstraction.menus.AMenu.MenuName;
import abstraction.patterns.PlanetPattern.Cardinal;

public class MultiMenuPlaceZAxis extends MultiMenu {

	private final List<PlanetEntrance> availableEntrances;

	private PlanetEntrance entrance;
	private PlanetEntrance exit;

	private final MenuName[] menuNames = { MenuName.SELECT_ZAXIS_ENTRANCE, MenuName.SELECT_ZAXIS_EXIT };

	public MultiMenuPlaceZAxis(Galaxy galaxy, Player player) {
		super(player);
		this.availableEntrances = galaxy.getAvailableSpots();
	}

	@Override
	protected AMenu<?> getMenu(int i) {
		AMenu<?> menu;
		switch (i) {

		case 1:
			menu = Game.factory.newMenuChooseFromList(menuNames[0], availableEntrances, player);
			break;

		case 2:
			List<PlanetEntrance> availableExits = new ArrayList<PlanetEntrance>(availableEntrances);
			availableExits.remove(entrance);
			Planet planet = entrance.getPlanet();
			for (Cardinal c : planet.getLinkableEntrances()) {
				availableExits.remove(new PlanetEntrance(planet, c));
			}

			menu = Game.factory.newMenuChooseFromList(menuNames[1], availableExits, player);
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
			if (exit == null) {
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
	public void doSelection() {
		updateState();
		while (state != -1) {
			AMenu<?> menu = getMenu(state);
			switch (state) {
			case 1:
				entrance = (PlanetEntrance) menu.selectChoice();
				break;
			case 2:
				exit = (PlanetEntrance) menu.selectChoiceWithCancel();
				break;
			default:
				throw new IllegalStateException("This state shouldn't happen.");
			}
			updateState();
		}
	}

	public PlanetEntrance getEntrance() {
		return entrance;
	}

	public PlanetEntrance getExit() {
		return exit;
	}

}
