package tests;


import org.junit.BeforeClass;

import tools.XmlParser;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.creators.PlanetCreator;
import abstraction.patterns.PlanetPattern.Cardinal;


public class Tests {
	
	@BeforeClass
	public static void initializeXml() {
		XmlParser.getAll();
		Game.IS_TEST = true;
	}
	
	public void generateBasicGalaxy(Galaxy galaxy) {
		Planet abaddon = PlanetCreator.createPlanet("Abaddon");
		Planet tarsonis = PlanetCreator.createPlanet("Tarsonis");
		Planet pridewater = PlanetCreator.createPlanet("Pridewater");
		
		galaxy.add(abaddon);
		tarsonis.rotateClockwise();
		galaxy.add(tarsonis, abaddon, Cardinal.EAST);
		galaxy.add(pridewater, tarsonis, Cardinal.SOUTH);
	}
	
	public void generateBasicGalaxy(Galaxy galaxy, Planet abaddon, Planet tarsonis, Planet pridewater) {
		galaxy.add(abaddon);
		tarsonis.rotateClockwise();
		galaxy.add(tarsonis, abaddon, Cardinal.EAST);
		galaxy.add(pridewater, tarsonis, Cardinal.SOUTH);
	}
}
