package tools;

import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class PlanetPosition {

	private int x;
	private int y;

	public PlanetPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean moreLeftThan(PlanetPosition p) {
		return this.getX() < p.getX();
	}

	public boolean moreRightThan(PlanetPosition p) {
		return this.getX() > p.getX();
	}

	public boolean moreTopThan(PlanetPosition p) {
		return this.getY() < p.getY();
	}

	public boolean moreBottomThan(PlanetPosition p) {
		return this.getY() > p.getY();
	}

	public PlanetPosition plusCardinal(Cardinal c) {
		int plusX = 0, plusY = 0;
		switch (c) {
		case NORTH:
			plusY = -1;
			break;
		case EAST:
			plusX = 1;
			break;
		case SOUTH:
			plusY = 1;
			break;
		case WEST:
			plusX = -1;
			break;
		default:
			throw new IllegalStateException("This should never happen: " + this + " has no point.");
		}
		return new PlanetPosition(x + plusX, y + plusY);
	}

	@Override
	public String toString() {
		return "" + getX() + "," + getY();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PlanetPosition)) {
			return false;
		} else {
			PlanetPosition p = (PlanetPosition) o;
			return getX() == p.getX() && getY() == p.getY();
		}
	}

	@Override
	public int hashCode() {
		String stringPos = Integer.toString(getX()) + Integer.toString(getY());
		return stringPos.hashCode();
	}

}
