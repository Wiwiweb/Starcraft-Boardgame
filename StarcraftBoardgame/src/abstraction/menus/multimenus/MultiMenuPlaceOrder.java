package abstraction.menus.multimenus;

import java.util.Set;

import abstraction.Game;
import abstraction.Order.OrderType;
import abstraction.Planet;
import abstraction.Player;
import abstraction.menus.Menu;
import abstraction.menus.MenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.multimenus.states.MultiMenuPlaceOrderChoices;

/**
 * @author William Gautier
 */
public class MultiMenuPlaceOrder extends MultiMenu {

	private final Set<Planet> availablePlanets;
	private final Set<OrderType> availableOrders;

	private final MultiMenuPlaceOrderChoices choices = new MultiMenuPlaceOrderChoices();

	public MultiMenuPlaceOrder(Set<Planet> availablePlanets, Set<OrderType> availableOrders, Player player) {
		super(player);
		this.availablePlanets = availablePlanets;
		this.availableOrders = availableOrders;
	}

	@Override
	protected Menu<?> getMenu(int i) {
		Menu<?> menu;

		switch (i) {

		case 1:
			menu = Game.factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_ORDER_TYPE, availableOrders, player);
			break;

		case 2:
			menu = Game.factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_PLANET_FOR_ORDER, availablePlanets, player);
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

		// After Choose Order Type // 1 path
		case 1:
			nextState = 2;
			break;

		// After Choose Planet // 2 paths
		case 2:
			if (choices.getPlanet() == null) {
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
	public MultiMenuPlaceOrderChoices doSelection() {
		updateState();
		while (state != -1) {
			Menu<?> menu = getMenu(state);
			switch (state) {
			case 1:
				choices.setOrderType((OrderType) menu.selectChoice());
				break;
			case 2:
				choices.setPlanet((Planet) menu.selectChoice());
				break;
			default:
				throw new IllegalStateException("This state shouldn't happen.");
			}
			updateState();
		}

		return choices;
	}

}
