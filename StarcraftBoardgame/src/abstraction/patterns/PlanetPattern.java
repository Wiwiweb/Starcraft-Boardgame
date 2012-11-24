package abstraction.patterns;

/**
 * @author William Gautier
 */
public class PlanetPattern {

	public static enum Cardinal {
		NORTH, EAST, SOUTH, WEST;

		public Cardinal opposite() {
			switch (this) {
			case NORTH:
				return SOUTH;
			case EAST:
				return WEST;
			case SOUTH:
				return NORTH;
			case WEST:
				return EAST;
			default:
				throw new IllegalStateException("This should never happen: " + this + " has no opposite.");
			}
		}
	}

	private final String name;
	private final Cardinal[] startingEntrances;
	private final AreaPattern[] areas;

	public PlanetPattern(String name, Cardinal[] startingEntrances, AreaPattern[] areas) {
		this.name = name;
		this.startingEntrances = startingEntrances;
		this.areas = areas;
	}

	public String getName() {
		return name;
	}

	public AreaPattern[] getAreas() {
		return areas;
	}

	public Cardinal[] getStartingEntrances() {
		return startingEntrances;
	}

	@Override
	public String toString() {
		return name + "(P)";
	}
}
