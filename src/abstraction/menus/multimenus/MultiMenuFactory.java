package abstraction.menus.multimenus;

import java.util.List;
import java.util.Set;

import tools.PlanetEntrance;
import abstraction.Galaxy;
import abstraction.Order;
import abstraction.Order.OrderType;
import abstraction.Planet;
import abstraction.Player;
import abstraction.Unit;

/**
 * @author William Gautier
 */
public class MultiMenuFactory {

	public MultiMenuPlacePlanet newMultiMenuPlacePlanet(Galaxy galaxy, int roundNumber, Player player) {
		return new MultiMenuPlacePlanet(galaxy, roundNumber, player);
	}

	public MultiMenuPlaceZAxis newMultiMenuPlaceZAxis(Set<PlanetEntrance> availableEntrances, Player player) {
		return new MultiMenuPlaceZAxis(availableEntrances, player);
	}

	public MultiMenuPlaceStartingForces newMultiMenuPlaceStartingForces(Planet startingPlanet, List<Unit> startingUnits,
			int startingTransports, Player player) {
		return new MultiMenuPlaceStartingForces(startingPlanet, startingUnits, startingTransports, player);
	}

	public MultiMenuPlaceOrder newMultiMenuPlaceOrder(Set<Planet> availablePlanets, Set<OrderType> availableOrders, Player player) {
		return new MultiMenuPlaceOrder(availablePlanets, availableOrders, player);
	}

	public MultiMenuChooseOrder newMultiMenuChooseOrder(Set<Order> executableOrders, Player player) {
		return new MultiMenuChooseOrder(executableOrders, player);
	}

	public MultiMenuBuildOrder newMultiMenuBuildOrder(Player player) {
		return new MultiMenuBuildOrder(player);
	}
}
