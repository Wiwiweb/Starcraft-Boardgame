package menus;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import presentation.text.TextIHM;
import tests.Tests;
import tools.PlanetPosition;
import abstraction.Area;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.Player;
import abstraction.Resource.ResourceType;
import abstraction.menus.multimenus.MultiMenuPlacePlanet;
import abstraction.menus.multimenus.states.MultiMenuPlacePlanetChoices;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class MultiMenuPlacePlanetTest extends Tests {

	private Game game;
	private Galaxy galaxy;
	private Player player;
	private MultiMenuPlacePlanet menu;

	@Before
	public void setUp() throws Exception {
		game = factory.newGame();
		galaxy = game.getGalaxy();
		player = factory.newPlayer("Player");
	}

	/**
	 * Test method for {@link abstraction.menus.multimenus.MultiMenuPlacePlanet#doSelection()}.
	 */
	@Test
	public void testDoSelection() {
		player.setFaction("Overmind", factory);

		Planet abaddon = factory.newPlanet("Abaddon");
		Planet tarsonis = factory.newPlanet("Tarsonis");
		Planet pridewater = factory.newPlanet("Pridewater");

		player.addPlanetToken(abaddon);
		player.addPlanetToken(tarsonis);
		player.addPlanetToken(pridewater);

		// Pridewater, rotate, place, cancel, place, place base, area 1
		String data = "2 1 3 1 3 1 1 ";
		TextIHM.scanner = new Scanner(data);
		menu = new MultiMenuPlacePlanet(galaxy, 0, player);
		MultiMenuPlacePlanetChoices choices = menu.doSelection(factory);

		Planet chosenPlanet = choices.getChosenPlanet();
		if (galaxy.isEmpty()) {
			galaxy.add(chosenPlanet);
		} else {
			galaxy.add(chosenPlanet, choices.getChosenSpot(), factory);
		}
		player.removePlanetToken(chosenPlanet);

		if (choices.isPlaceFirstBase()) {
			player.placeBase(choices.getChosenBaseArea());
			player.setStartingPlanet(choices.getChosenBaseArea().getPlanet());

			for (Area a : chosenPlanet.getAreas()) {
				if (a.getResource().getResourceType() != ResourceType.CONTROL) {
					player.addControlledResource(a.getResource());
				}
			}
		}

		assertFalse(pridewater.isLinkable(Cardinal.NORTH));
		assertTrue(pridewater.isLinkable(Cardinal.WEST));
		assertSame(pridewater, galaxy.getPlanetAt(new PlanetPosition(0, 0)));
	}

	/**
	 * Using reflection to test Game's private methods Experimental...
	 */
	// @Ignore
	@Test
	public void testPlacePlanets() {
		Game game = factory.newGame();
		Player a = factory.newPlayer("A");
		Player b = factory.newPlayer("B");
		game.addPlayer(a);
		game.addPlayer(b);
		game.setFirstPlayer(a);

		a.setFaction("Overmind", factory);
		b.setFaction("Overmind", factory);

		Planet chauSara = factory.newPlanet("Chau Sara");
		Planet abaddon = factory.newPlanet("Abaddon");
		Planet braken = factory.newPlanet("Braken");
		Planet pridewater = factory.newPlanet("Pridewater");

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
