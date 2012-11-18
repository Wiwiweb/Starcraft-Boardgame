import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import presentation.text.TextIHM;

import tools.XmlParser;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.Player;
import abstraction.creators.FactionCreator;
import abstraction.creators.PlanetCreator;
import abstraction.patterns.PlanetPattern.Cardinal;

public class MenuTests {

	@BeforeClass
	public static void initializeXml() {
		XmlParser.getAll();
		Game.IS_TEST = true;
	}
	
	@Test
	public void testPlaceZAxisNoMoreSpots() {
		Game game = new Game();
		Player a = new Player("A");
		Player b = new Player("B");
		Player c = new Player("C");
		game.addPlayer(a);
		game.addPlayer(b);
		game.addPlayer(c);
		game.setFirstPlayer(a);

		Galaxy galaxy = game.getGalaxy();

		Planet abaddon = PlanetCreator.createPlanet("Abaddon");
		Planet tarsonis = PlanetCreator.createPlanet("Tarsonis");
		Planet pridewater = PlanetCreator.createPlanet("Pridewater");

		galaxy.add(abaddon);
		galaxy.add(tarsonis, abaddon, Cardinal.WEST);
		galaxy.add(pridewater, tarsonis, Cardinal.NORTH);

		assertEquals("P \nTA\n", galaxy.toString());

		try {
			final Method[] methods = game.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals("placeZAxis")) {
					method.setAccessible(true);
					final Object[] args = { game.getPlayerListByOrder() };
					String data = "";
					// A: Pridewater north, abaddon east
					data += "2 1 ";
					// B: Pridewater east, tarsonic south
					data += "1 1 ";
					// C: No more spots left
							
					TextIHM.scanner = new Scanner(data);
					method.invoke(game, args);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testPlaceZAxisNoMoreLegalSpots() {
		Game game = new Game();
		Player a = new Player("A");
		Player b = new Player("B");
		Player c = new Player("C");
		game.addPlayer(a);
		game.addPlayer(b);
		game.addPlayer(c);
		game.setFirstPlayer(a);

		Galaxy galaxy = game.getGalaxy();

		Planet abaddon = PlanetCreator.createPlanet("Abaddon");
		Planet tarsonis = PlanetCreator.createPlanet("Tarsonis");
		Planet pridewater = PlanetCreator.createPlanet("Pridewater");

		galaxy.add(abaddon);
		galaxy.add(tarsonis, abaddon, Cardinal.WEST);
		galaxy.add(pridewater, tarsonis, Cardinal.NORTH);

		assertEquals("P \nTA\n", galaxy.toString());

		try {
			final Method[] methods = game.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals("placeZAxis")) {
					method.setAccessible(true);
					final Object[] args = { game.getPlayerListByOrder() };
					String data = "";
					// A: Pridewater north, cancel, abaddon east, tarsonis south
					data += "3 3 1 3 ";
					// B: No more legal spots, stop zaxis placement

					TextIHM.scanner = new Scanner(data);
					method.invoke(game, args);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getClass().toString());
		}
	}

	@Test
	public void testPlacePlanets() {
		Game game = new Game();
		Player a = new Player("A");
		Player b = new Player("B");
		game.addPlayer(a);
		game.addPlayer(b);
		game.setFirstPlayer(a);

		a.setFaction(FactionCreator.getFaction("Overmind"));
		b.setFaction(FactionCreator.getFaction("Overmind"));

		Planet chauSara = PlanetCreator.createPlanet("Chau Sara");
		Planet abaddon = PlanetCreator.createPlanet("Abaddon");
		Planet braken = PlanetCreator.createPlanet("Braken");
		Planet pridewater = PlanetCreator.createPlanet("Pridewater");

		a.addPlanetToken(chauSara);
		a.addPlanetToken(abaddon);
		b.addPlanetToken(braken);
		b.addPlanetToken(pridewater);

		try {
			final Method[] methods = game.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals("placePlanets")) {
					method.setAccessible(true);
					final Object[] args = { game.getPlayerListByOrder() };
					String data = "";
					// A: Place planet, rotate, place base
					data += "1 2 3 1 1 ";
					// B: Place planet, do not rotate, cancel, rotate, do not place base
					data += "1 3 2 1 3 1 2 ";
					// B: Place planet, do not rotate, place base;
					data += "1 3 1 1 ";
					// A: Place planet, do not rotate
					data += "1 3 1 ";

					TextIHM.scanner = new Scanner(data);
					method.invoke(game, args);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getClass().toString());
		}
	}
}
