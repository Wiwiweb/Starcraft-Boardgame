package tools;

import abstraction.Planet;
import abstraction.patterns.PlanetPattern.Cardinal;

public class PlanetEntrance {

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

}
