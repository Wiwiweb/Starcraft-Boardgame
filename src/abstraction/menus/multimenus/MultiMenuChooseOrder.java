package abstraction.menus.multimenus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import abstraction.Factory;
import abstraction.Order;
import abstraction.Order.OrderType;
import abstraction.Player;
import abstraction.menus.Menu;
import abstraction.menus.MenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.MenuStaticChoices.StaticChoice;
import abstraction.menus.MenuStaticChoices.StaticChoicesMenuName;
import abstraction.menus.multimenus.states.MultiMenuChooseOrderChoices;

/**
 * @author William Gautier
 */
public class MultiMenuChooseOrder extends MultiMenu {

	private final Set<Order> executableOrders;

	private StaticChoice executeOrCardChoice;

	private final MultiMenuChooseOrderChoices choices = new MultiMenuChooseOrderChoices();

	public MultiMenuChooseOrder(Set<Order> executableOrders, Player player) {
		super(player);
		this.executableOrders = executableOrders;
	}

	@Override
	protected Menu<?> getMenu(int i, Factory factory) {
		Menu<?> menu;

		switch (i) {

		case 1:
			menu = factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_ORDER_TO_EXECUTE, executableOrders, player);
			break;

		case 2:
			if (choices.getOrderChosen().getType() == OrderType.RESEARCH
					&& choices.getOrderChosen().getPlanet().hasBaseOnPlanet(player)) {

				menu = factory.newMenuStaticChoices(StaticChoicesMenuName.EXECUTE_OR_CARD,
						new ArrayList<StaticChoice>(Arrays.asList(StaticChoice.EXECUTE_OR_CARD_EXECUTE)), player, this);

			} else {
				menu = factory.newMenuStaticChoices(StaticChoicesMenuName.EXECUTE_OR_CARD, player);
			}
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

		// After Choose Order
		case 1:
			nextState = 2;
			break;

		// After Execute or Card - 2 path
		case 2:
			if (executeOrCardChoice == null) {
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
	public MultiMenuChooseOrderChoices doSelection(Factory factory) {
		updateState();
		while (state != -1) {
			Menu<?> menu = getMenu(state, factory);
			switch (state) {
			case 1:
				choices.setOrderChosen((Order) menu.selectChoice(false));
				break;
			case 2:
				executeOrCardChoice = (StaticChoice) menu.selectChoice(true);
				break;
			default:
				throw new IllegalStateException("This state shouldn't happen.");
			}
			updateState();
		}

		return choices;
	}

}
