package abstraction;

public class Route {
	
	private Planet planet1;
	private Planet planet2;
	private final boolean isZAxis;

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

	public Planet getPlanet1() {return planet1;}
	public Planet getPlanet2() {return planet2;}

	public void setPlanet1(Planet planet1) {this.planet1 = planet1;}
	public void setPlanet2(Planet planet2) {this.planet2 = planet2;}

	public boolean isZAxis() {
		return isZAxis;
	}
}
