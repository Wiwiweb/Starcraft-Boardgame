package abstraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import abstraction.patterns.PlanetPattern;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class Planet implements Comparable<Planet> {

	private final PlanetPattern pattern;

	private boolean[] entrances = new boolean[4];
	private final Route[] routes = { null, null, null, null };
	private final List<Area> areas;
	private final List<Order> orderPile = new ArrayList<Order>();

	public Planet(PlanetPattern pattern, List<Area> areas) {
		this.pattern = pattern;
		this.areas = areas;
		for (Cardinal e : pattern.getStartingEntrances()) {
			this.entrances[e.ordinal()] = true;
		}
		int i = 1;
		for (Area a : this.areas) {
			a.setPlanet(this);
			a.setAreaNumber(i);
			i++;
		}

	}

	public String getName() {
		return pattern.getName();
	}

	public boolean hasEntrance(Cardinal c) {
		return entrances[c.ordinal()];
	}

	public boolean hasRoute(Cardinal c) {
		return !(getRoute(c) == null);
	}

	public boolean isLinkable(Cardinal c) {
		return hasEntrance(c) && !hasRoute(c);
	}

	public List<Cardinal> getLinkableEntrances() {
		List<Cardinal> result = new ArrayList<Cardinal>();
		for (Cardinal c : Cardinal.values()) {
			if (isLinkable(c)) {
				result.add(c);
			}
		}
		return result;
	}

	public Route getRoute(Cardinal cardinal) {
		return routes[cardinal.ordinal()];
	}

	public void addRoute(Route route, Cardinal cardinal) {
		if (this == route.getPlanet1() || this == route.getPlanet2()) {
			routes[cardinal.ordinal()] = route;
		} else {
			throw new IllegalStateException("This route does not include this planet.");
		}
	}

	public void removeRoute(Cardinal cardinal) {
		routes[cardinal.ordinal()] = null;
	}

	public Planet getDestination(Cardinal cardinal) {
		if (hasRoute(cardinal)) {
			return getRoute(cardinal).getDestinationFrom(this);
		} else {
			throw new IllegalStateException("This route does not exist.");
		}
	}

	public Area getArea(int i) {
		if (i < areas.size()) {
			return areas.get(i);
		} else {
			throw new IllegalArgumentException("This zone does not exist.");
		}
	}

	public List<Area> getAreas() {
		return areas;
	}

	public List<Area> getBuildableAreas(Player player) {
		List<Area> result = new ArrayList<Area>();
		for (Area area : getAreas()) {
			if (area.isEmpty() || (area.isControlledBy(player) && !area.isFull())) {
				result.add(area);
			}
		}
		return result;
	}

	public List<Area> getBuildableAreasPlusUnits(Player player, List<Area> areasWithAVirtualUnit) {
		List<Area> result = new ArrayList<Area>();
		for (Area area : getAreas()) {
			int nbUnitsVirtuallyAddedToThisZone = 0;
			for (Area a : areasWithAVirtualUnit) {
				if (a == area) {
					nbUnitsVirtuallyAddedToThisZone++;
				}
			}

			if ((area.getUnitNumber() + nbUnitsVirtuallyAddedToThisZone) > area.getUnitLimit()) {
				throw new IllegalStateException("Too many units are going to be added to this area");
			}

			boolean isFull = (area.getUnitNumber() + nbUnitsVirtuallyAddedToThisZone) == area.getUnitLimit();

			if ((area.isEmpty() || area.isControlledBy(player)) && !isFull) {
				result.add(area);
			}
		}
		return result;
	}

	public List<Route> getRoutes() {
		List<Route> result = new ArrayList<Route>();
		for (Route r : routes) {
			if (r != null) {
				result.add(r);
			}
		}
		return result;
	}

	/**
	 * Returns routes with transports owned by the player
	 */
	public List<Route> getRoutesWithTransports(Player player) {
		List<Route> result = new ArrayList<Route>();
		for (Route r : getRoutes()) {
			if (player.hasTransport(r)) {
				result.add(r);
			}
		}
		return result;
	}

	/**
	 * Returns routes without transports owned by the player
	 */
	public List<Route> getRoutesWithNoTransports(Player player) {
		List<Route> result = new ArrayList<Route>();
		for (Route r : getRoutes()) {
			if (!player.hasTransport(r)) {
				result.add(r);
			}
		}
		return result;
	}

	/**
	 * Returns true if the player has a base on the planet.
	 */
	public boolean hasBaseOnPlanet(Player player) {
		boolean result = false;
		for (Area a : areas) {
			if (a.getBaseOwner() == player) {
				result = true;
				break;
			}
		}
		return result;
	}

	public List<Order> getOrderPile() {
		return orderPile;
	}

	public Order getTopOrder() {
		return orderPile.get(orderPile.size() - 1);
	}

	public void addOrderToTop(Order o) {
		orderPile.add(o);
	}

	public void removeTopOrder() {
		orderPile.remove(orderPile.size() - 1);
	}

	/**
	 * Permanently rotates the planet clockwise by switching entrances.</br>
	 * Cannot be done after routes are set. (If you want to rotate the whole galaxy, check Galaxy)
	 */
	public void rotateClockwise() {
		if (!Arrays.equals(routes, new Route[] { null, null, null, null })) {
			throw new IllegalStateException("Routes have already been set.");
		} else {
			boolean[] newEntrances = new boolean[4];
			newEntrances[Cardinal.NORTH.ordinal()] = entrances[Cardinal.WEST.ordinal()];
			newEntrances[Cardinal.EAST.ordinal()] = entrances[Cardinal.NORTH.ordinal()];
			newEntrances[Cardinal.SOUTH.ordinal()] = entrances[Cardinal.EAST.ordinal()];
			newEntrances[Cardinal.WEST.ordinal()] = entrances[Cardinal.SOUTH.ordinal()];
			entrances = newEntrances;
		}
	}

	/**
	 * Permanently rotates the planet counterclockwise by switching entrances.</br>
	 * Cannot be done after routes are set. (If you want to rotate the whole galaxy, check Galaxy)
	 */
	public void rotateCounterClockwise() {
		if (!Arrays.equals(routes, new Route[] { null, null, null, null })) {
			throw new IllegalStateException("Routes have already been set.");
		} else {
			boolean[] newEntrances = new boolean[4];
			newEntrances[Cardinal.NORTH.ordinal()] = entrances[Cardinal.EAST.ordinal()];
			newEntrances[Cardinal.EAST.ordinal()] = entrances[Cardinal.SOUTH.ordinal()];
			newEntrances[Cardinal.SOUTH.ordinal()] = entrances[Cardinal.WEST.ordinal()];
			newEntrances[Cardinal.WEST.ordinal()] = entrances[Cardinal.NORTH.ordinal()];
			entrances = newEntrances;
		}
	}

	/**
	 * Connect this planet to another planet by creating a new Route and adding the route to both planet's lists.
	 * 
	 * @param p - The planet to connect to.
	 * @param thisCardinal - The direction we'll connect from.
	 * @param pCardinal - The direction of planet p we'll connect to.
	 * @param isZAxis - If the Route should be created as a ZAxis.
	 */
	public void connect(Planet p, Cardinal thisCardinal, Cardinal pCardinal, boolean isZAxis, Factory factory) {
		if (this == p) {
			throw new IllegalStateException("The planets are the same : " + p.getName());
		} else if (!(isLinkable(thisCardinal) && p.isLinkable(pCardinal))) {
			throw new IllegalStateException("The entrances are not available.");
		} else if (!isZAxis && !(thisCardinal.opposite() == pCardinal)) {
			throw new IllegalStateException("The entrances are not opposite.");
		} else {
			Route route = factory.newRoute(this, p, isZAxis);
			addRoute(route, thisCardinal);
			p.addRoute(route, pCardinal);
		}
	}

	/**
	 * Returns true if the player has at least one friendly unit or base on any area of the planet.
	 * 
	 * @param player - The player that needs a friendly unit or base.
	 * @return A List of all the Planets.
	 */
	public boolean hasFriendlyUnitOrBase(Player player) {
		boolean result = false;
		for (Area a : areas) {
			if (a.isControlledBy(player)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public String showTextGraph() {
		String string = "";

		if (hasEntrance(Cardinal.NORTH)) {
			string += " | \n";
		} else {
			string += "   \n";
		}

		if (hasEntrance(Cardinal.WEST)) {
			string += "-";
		} else {
			string += " ";
		}
		string += getName().charAt(0);
		if (hasEntrance(Cardinal.EAST)) {
			string += "-";
		} else {
			string += " ";
		}
		string += "\n";

		if (hasEntrance(Cardinal.SOUTH)) {
			string += " | \n";
		} else {
			string += "   \n";
		}
		return string;
	}

	@Override
	public String toString() {
		return "Planet " + getName();
	}

	@Override
	public int compareTo(Planet o) {
		return this.pattern.getName().compareTo(o.pattern.getName());
	}

}
