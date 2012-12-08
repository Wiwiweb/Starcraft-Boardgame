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

	/**
	 * Useful shortcut for tests</br>
	 * Generates this :
	 * 
	 * <pre>
	 * -A-T-
	 *    |
	 *    P-
	 *    |
	 * </pre>
	 * 
	 * First Tarsonis, then Abaddon, then Pridewater
	 */
	protected void generateBasicGalaxy(Galaxy galaxy, Planet abaddon, Planet tarsonis, Planet pridewater) {
		tarsonis.rotateClockwise();
		galaxy.add(tarsonis);
		galaxy.add(abaddon, tarsonis, Cardinal.WEST);
		galaxy.add(pridewater, tarsonis, Cardinal.SOUTH);
	}

	protected void generateBasicGalaxy(Galaxy galaxy) {
		Planet abaddon = PlanetCreator.createPlanet("Abaddon");
		Planet tarsonis = PlanetCreator.createPlanet("Tarsonis");
		Planet pridewater = PlanetCreator.createPlanet("Pridewater");

		generateBasicGalaxy(galaxy, abaddon, tarsonis, pridewater);
	}
}
