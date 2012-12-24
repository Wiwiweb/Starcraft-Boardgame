package abstraction.menus.multimenus.states;

import abstraction.Order;

/**
 * @author William Gautier
 */
public class MultiMenuChooseOrderChoices implements MultiMenuChoices {

	private Order orderChosen;
	private boolean eventCard;

	public Order getOrderChosen() {
		return orderChosen;
	}

	public void setOrderChosen(Order orderChosen) {
		this.orderChosen = orderChosen;
	}

	public boolean isEventCard() {
		return eventCard;
	}

	public void setEventCard(boolean eventCard) {
		this.eventCard = eventCard;
	}
}
