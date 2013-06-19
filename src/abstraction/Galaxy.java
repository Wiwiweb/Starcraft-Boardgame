package abstraction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tools.BidirectionalMap;
import tools.PlanetEntrance;
import tools.PlanetPosition;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class Galaxy {

	private final BidirectionalMap<Planet, PlanetPosition> planetPositions = new BidirectionalMap<Planet, PlanetPosition>();
	private final PlanetPosition topLeft = new PlanetPosition(0, 0);
	private final PlanetPosition bottomRight = new PlanetPosition(0, 0);

	/**
	 * Adds the first Planet to the Galaxy.
	 * 
	 * @param p - The Planet to add.
	 */
	public void add(Planet p) {
		if (isEmpty() == true) {
			planetPositions.put(p, new PlanetPosition(0, 0));
		} else {
			throw new IllegalStateException("There is already an initial planet.");
		}
	}

	/**
	 * Adds a planet to the Galaxy to an existing Planet.
	 * 
	 * @param add - The Planet to add.
	 * @param link - The Planet to add to.
	 * @param from - The direction we'll add the new Planet from.
	 */
	public void add(Planet add, Planet link, Cardinal from, Factory factory) {
		if (!link.isLinkable(from) || !add.isLinkable(from.opposite())) {
			throw new IllegalStateException("The planets are not linkable from this side.");
		} else if (!planetPositions.containsKey(link)) {
			throw new IllegalStateException(link.getName() + " doesn't exist in the galaxy.");
		} else {

			PlanetPosition linkPosition = planetPositions.get(link);
			PlanetPosition addPosition = linkPosition.plusCardinal(from);

			// Update galaxy boundaries
			if (addPosition.moreLeftThan(topLeft)) {
				topLeft.setX(addPosition.getX());
			}
			if (addPosition.moreRightThan(bottomRight)) {
				bottomRight.setX(addPosition.getX());
			}
			if (addPosition.moreTopThan(topLeft)) {
				topLeft.setY(addPosition.getY());
			}
			if (addPosition.moreBottomThan(bottomRight)) {
				bottomRight.setY(addPosition.getY());
			}

			// Connect added planet
			planetPositions.put(add, addPosition);
			link.connect(add, from, from.opposite(), false, factory);

			// Connect close planets to the planet that was added
			for (Cardinal c : Cardinal.values()) {
				Planet closePlanet = planetPositions.getKey(addPosition.plusCardinal(c));
				if (closePlanet != null && add.isLinkable(c) && closePlanet.isLinkable(c.opposite())) {
					add.connect(closePlanet, c, c.opposite(), false, factory);
				}
			}

		}
	}

	public void add(Planet add, PlanetEntrance entrance, Factory factory) {
		add(add, entrance.getPlanet(), entrance.getEntrance(), factory);
	}

	public Planet getPlanetAt(PlanetPosition pos) {
		return planetPositions.getKey(pos);
	}

	public boolean isEmpty() {
		return planetPositions.isEmpty();
	}

	/**
	 * Get all the PlanetEntrances where a new Route could be placed.
	 * 
	 * @return A Set of all the PlanetEntrances
	 */
	public Set<PlanetEntrance> getAvailableSpots() {
		final Set<PlanetEntrance> result = new HashSet<PlanetEntrance>();

		for (Planet p : getPlanets()) {
			final List<Cardinal> entrances = p.getLinkableEntrances();
			final PlanetPosition position = planetPositions.get(p);

			for (Cardinal c : entrances) {
				if (planetPositions.getKey(position.plusCardinal(c)) == null) {
					result.add(new PlanetEntrance(p, c));
				}
			}
		}
		return result;
	}

	/**
	 * Get all the Planets where player could place an order. (ie. he has at least a friendly unit or base on them)
	 * 
	 * @param player - The player to check for available planets.
	 * @return A Set of all the Planets.
	 */
	public Set<Planet> getAvailableOrderPlanets(Player player) {
		Set<Planet> result = new HashSet<Planet>();

		for (Planet p : getPlanets()) {
			if (p.hasFriendlyUnitOrBase(player)) {
				result.add(p);
				for (Route r : p.getRoutes()) {
					result.add(r.getDestinationFrom(p));
				}
			}
		}

		return result;
	}

	public Set<Planet> getPlanets() {
		return planetPositions.keySet();
	}

	@Override
	public String toString() {

		String result = "";
		for (int i = topLeft.getY(); i <= bottomRight.getY(); i++) {
			for (int j = topLeft.getX(); j <= bottomRight.getX(); j++) {
				Planet p = getPlanetAt(new PlanetPosition(j, i));
				if (p == null) {
					result += " ";
				} else {
					result += p.getName().charAt(0);
				}
			}
			result += "\n";
		}
		return result;
	}
}
