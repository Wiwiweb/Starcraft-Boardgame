package tools;

import abstraction.Planet;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class PlanetEntrance implements Comparable<PlanetEntrance> {

	private final Planet planet;
	private final Cardinal entrance;

	public PlanetEntrance(Planet planet, Cardinal entrance) {
		this.planet = planet;
		this.entrance = entrance;
	}

	public Planet getPlanet() {
		return planet;
	}

	public Cardinal getEntrance() {
		return entrance;
	}

	@Override
	public String toString() {
		return entrance + " entrance of " + planet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entrance == null) ? 0 : entrance.hashCode());
		result = prime * result + ((planet == null) ? 0 : planet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlanetEntrance other = (PlanetEntrance) obj;
		if (entrance != other.entrance)
			return false;
		if (planet == null) {
			if (other.planet != null)
				return false;
		} else if (!planet.equals(other.planet))
			return false;
		return true;
	}

	@Override
	public int compareTo(PlanetEntrance p) {
		int result = this.planet.compareTo(p.planet);
		if (result == 0) {
			result = this.entrance.compareTo(p.entrance);
		}
		return result;
	}
}
