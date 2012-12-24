package tests;

import org.junit.BeforeClass;

import tools.XmlParser;
import abstraction.Factory;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.patterns.PlanetPattern.Cardinal;
import control.text.CFactory;

public abstract class Tests {

	protected static Factory factory;

	@BeforeClass
	public static void initializeXml() {
		factory = new CFactory();
		new XmlParser(factory).getAll();
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
		galaxy.add(abaddon, tarsonis, Cardinal.WEST, factory);
		galaxy.add(pridewater, tarsonis, Cardinal.SOUTH, factory);
	}

	protected void generateBasicGalaxy(Galaxy galaxy) {
		Planet abaddon = factory.newPlanet("Abaddon");
		Planet tarsonis = factory.newPlanet("Tarsonis");
		Planet pridewater = factory.newPlanet("Pridewater");

		generateBasicGalaxy(galaxy, abaddon, tarsonis, pridewater);
	}
}
