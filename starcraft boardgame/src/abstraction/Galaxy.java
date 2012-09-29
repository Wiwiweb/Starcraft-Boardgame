package abstraction;

import java.util.ArrayList;
import java.util.List;

import tools.BidirectionalMap;
import tools.PlanetEntrance;
import tools.PlanetPosition;
import abstraction.patterns.PlanetPattern.Cardinal;

public class Galaxy {

	private BidirectionalMap<Planet, PlanetPosition> planetPositions = new BidirectionalMap<Planet, PlanetPosition>();
	private PlanetPosition topLeft = new PlanetPosition(0, 0);
	private PlanetPosition bottomRight = new PlanetPosition(0, 0);

	public void add(Planet p) {
		if (isEmpty() == true) {
			planetPositions.put(p, new PlanetPosition(0, 0));
		} else {
			throw new IllegalStateException("There is already an initial planet.");
		}
	}

	public void add(Planet add, Planet link, Cardinal from) {
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
			link.connect(add, from, from.opposite(), false);

			// Connect close planets to the planet that was added
			for (Cardinal c : Cardinal.values()) {
				Planet closePlanet = planetPositions.getKey(addPosition.plusCardinal(c));
				if (closePlanet != null && add.isLinkable(c) && closePlanet.isLinkable(c.opposite())) {
					add.connect(closePlanet, c, c.opposite(), false);
				}
			}

		}
	}
	
	public void add(Planet add, PlanetEntrance entrance) {
		add(add, entrance.getPlanet(), entrance.getEntrance());
	}

	public Planet getPlanetAt(PlanetPosition pos) {
		return planetPositions.getKey(pos);
	}

	public boolean isEmpty() {
		return planetPositions.isEmpty();
	}

	public List<PlanetEntrance> getAvailableSpots() {
		final List<PlanetEntrance> result = new ArrayList<PlanetEntrance>();
		for (Planet p : planetPositions.keySet()) {
			final List<Cardinal> entrances = p.getLinkableEntrances();
			for (Cardinal c : entrances) {
				result.add(new PlanetEntrance(p, c));
			}
		}
		return result;
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
