package abstraction.menus.states;

import abstraction.Order.OrderType;
import abstraction.Planet;


/**
 * @author William Gautier
 */
public class MultiMenuPlaceOrderChoices {
	
	private OrderType orderType;
	private Planet planet;
	
	public OrderType getOrderType() {
		return orderType;
	}
	
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	
	public Planet getPlanet() {
		return planet;
	}
	
	public void setPlanet(Planet planet) {
		this.planet = planet;
	}
}
