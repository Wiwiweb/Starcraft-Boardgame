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
import abstraction.creators.FactionCreator;
import abstraction.creators.PlanetCreator;
import abstraction.menus.MultiMenuPlacePlanet;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class MultiMenuPlacePlanetTests extends Tests {

	private Game game;
	private Galaxy galaxy;
	private Player player;
	private MultiMenuPlacePlanet menu;

	@Before
	public void setUp() throws Exception {
		game = new Game();
		galaxy = game.getGalaxy();
		player = new Player("Player");
	}

	/**
	 * Test method for {@link abstraction.menus.MultiMenuPlacePlanet#doSelection()}.
	 */
	@Test
	public void testDoSelection() {
		player.setFaction(FactionCreator.getFaction("Overmind"));

		Planet abaddon = PlanetCreator.createPlanet("Abaddon");
		Planet tarsonis = PlanetCreator.createPlanet("Tarsonis");
		Planet pridewater = PlanetCreator.createPlanet("Pridewater");

		player.addPlanetToken(abaddon);
		player.addPlanetToken(tarsonis);
		player.addPlanetToken(pridewater);

		// Pridewater, rotate, place, cancel, place, place base, area 1
		String data = "2 1 3 1 3 1 1 ";
		TextIHM.scanner = new Scanner(data);
		menu = new MultiMenuPlacePlanet(galaxy, 0, player);
		menu.doSelection();

		Planet chosenPlanet = menu.getChosenPlanet();
		if (galaxy.isEmpty()) {
			galaxy.add(chosenPlanet);
		} else {
			galaxy.add(chosenPlanet, menu.getChosenSpot());
		}
		player.removePlanetToken(chosenPlanet);

		if (menu.isPlaceFirstBase()) {
			player.placeBase(menu.getChosenBaseArea());
			player.setStartingPlanet(menu.getChosenBaseArea().getPlanet());

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
