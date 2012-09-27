package abstraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import abstraction.patterns.PlanetPattern;
import abstraction.patterns.PlanetPattern.Cardinal;

public class Planet {

	private PlanetPattern pattern;

	private boolean[] entrances = new boolean[4];
	private Route[] routes = { null, null, null, null };
	private final List<Area> areas;

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

	public void connect(Planet p, Cardinal thisCardinal, Cardinal pCardinal, boolean isZAxis) {
		if (!(isLinkable(thisCardinal) && p.isLinkable(pCardinal))) {
			throw new IllegalStateException("The entrances are not available.");
		} else if (!isZAxis && !(thisCardinal.opposite() == pCardinal)) {
			throw new IllegalStateException("The entrances are not opposite.");
		} else {
			Route route = new Route(this, p, isZAxis);
			addRoute(route, thisCardinal);
			p.addRoute(route, pCardinal);
		}
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

}
