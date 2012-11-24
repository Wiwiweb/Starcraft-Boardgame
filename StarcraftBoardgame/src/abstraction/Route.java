package abstraction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author William Gautier
 */
public class Route implements Comparable<Route> {

	private Planet planet1;
	private Planet planet2;
	private final boolean isZAxis;

	private List<Player> transports = new ArrayList<Player>();

	public Route(Planet planet1, Planet planet2, boolean isZAxis) {
		this.setPlanet1(planet1);
		this.setPlanet2(planet2);
		this.isZAxis = isZAxis;
	}

	public Planet getDestinationFrom(Planet p) {
		if (p == planet1) {
			return planet2;
		} else if (p == planet2) {
			return planet1;
		} else {
			System.out.println("This planet isn't on this route");
			return null;
		}
	}

	public Planet getPlanet1() {
		return planet1;
	}

	public Planet getPlanet2() {
		return planet2;
	}

	public void setPlanet1(Planet planet1) {
		this.planet1 = planet1;
	}

	public void setPlanet2(Planet planet2) {
		this.planet2 = planet2;
	}

	public boolean isZAxis() {
		return isZAxis;
	}

	public List<Player> getTransports() {
		return transports;
	}

	public void addTransport(Player player) {
		transports.add(player);
	}

	@Override
	public String toString() {
		String result = "Route between " + planet1.getName() + " and " + planet2.getName();
		if (isZAxis) {
			result += " (Z-Axis)";
		}
		return result;
	}

	@Override
	public int compareTo(Route o) {
		int result = this.planet1.compareTo(o.planet1);
		if (result == 0) {
			result = this.planet2.compareTo(o.planet2);
		}
		return result;
	}
}
