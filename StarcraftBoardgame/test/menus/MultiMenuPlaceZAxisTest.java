package menus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import presentation.text.TextIHM;
import tests.Tests;
import tools.PlanetEntrance;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.Player;
import abstraction.creators.PlanetCreator;
import abstraction.menus.MultiMenuPlaceZAxis;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class MultiMenuPlaceZAxisTest extends Tests {

	private Game game;
	private Galaxy galaxy;
	private Player player;
	private MultiMenuPlaceZAxis menu;

	@Before
	public void setUp() throws Exception {
		game = new Game();
		galaxy = game.getGalaxy();
		player = new Player("Player");
	}

	/**
	 * Test method for {@link abstraction.menus.MultiMenuPlaceZAxis#doSelection()}.
	 */
	@Test
	public void testDoSelection() {
		Planet abaddon = PlanetCreator.createPlanet("Abaddon");
		Planet tarsonis = PlanetCreator.createPlanet("Tarsonis");
		Planet pridewater = PlanetCreator.createPlanet("Pridewater");

		generateBasicGalaxy(galaxy, abaddon, tarsonis, pridewater);

		// Abaddon west, cancel, pridewater east, cancel, pridewater east, abaddon west
		String data = "1 4 2 3 2 1 ";
		TextIHM.scanner = new Scanner(data);
		menu = new MultiMenuPlaceZAxis(galaxy.getAvailableSpots(), player);
		menu.doSelection();

		PlanetEntrance entrance = menu.getChoices().getEntrance();
		PlanetEntrance exit = menu.getChoices().getExit();
		entrance.getPlanet().connect(exit.getPlanet(), entrance.getEntrance(), exit.getEntrance(), true);

		assertSame(abaddon, pridewater.getRoute(Cardinal.EAST).getDestinationFrom(pridewater));
		assertSame(pridewater, abaddon.getRoute(Cardinal.WEST).getDestinationFrom(abaddon));
		assertSame(pridewater.getRoute(Cardinal.EAST), abaddon.getRoute(Cardinal.WEST));
		assertTrue(pridewater.getRoute(Cardinal.EAST).isZAxis());
	}

	/**
	 * Using reflection to test Game's private methods Experimental...
	 */
	// @Ignore
	@Test
	public void testPlaceZAxisNoMoreSpots() {
		Player a = new Player("A");
		Player b = new Player("B");
		Player c = new Player("C");
		game.addPlayer(a);
		game.addPlayer(b);
		game.addPlayer(c);
		game.setFirstPlayer(a);

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
					method.setAccessible(false);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Using reflection to test Game's private methods Experimental...
	 */
	// @Ignore
	@Test
	public void testPlaceZAxisNoMoreLegalSpots() {
		Player a = new Player("A");
		Player b = new Player("B");
		Player c = new Player("C");
		game.addPlayer(a);
		game.addPlayer(b);
		game.addPlayer(c);
		game.setFirstPlayer(a);

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
					method.setAccessible(false);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getClass().toString());
		}
	}

}
