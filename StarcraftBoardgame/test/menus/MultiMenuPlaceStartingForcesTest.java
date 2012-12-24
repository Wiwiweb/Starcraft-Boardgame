package menus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import presentation.text.TextIHM;
import tests.Tests;
import abstraction.Area;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.Player;
import abstraction.Route;
import abstraction.Unit;
import abstraction.menus.multimenus.MultiMenuPlaceStartingForces;
import abstraction.menus.multimenus.states.MultiMenuPlaceStartingForcesChoices;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class MultiMenuPlaceStartingForcesTest extends Tests {

	private Game game;
	private Galaxy galaxy;
	private Player player;
	private MultiMenuPlaceStartingForces menu;

	@Before
	public void setUp() throws Exception {
		game = factory.newGame();
		galaxy = game.getGalaxy();
		player = factory.newPlayer("Player");
	}

	/**
	 * Test method for {@link abstraction.menus.multimenus.MultiMenuPlaceStartingForces#doSelection()}.
	 */
	@Test
	public void testDoSelection() {
		player.setFaction("Overmind", factory);

		Planet abaddon = factory.newPlanet("Abaddon");
		Planet tarsonis = factory.newPlanet("Tarsonis");
		Planet pridewater = factory.newPlanet("Pridewater");

		generateBasicGalaxy(galaxy, abaddon, tarsonis, pridewater);

		List<Unit> startingUnits = new ArrayList<Unit>();
		for (int i = 0; i < player.getFaction().getStartingUnitTypes().length; i++) {

			String unitType = player.getFaction().getStartingUnitTypes(i);
			for (int j = 0; j < player.getFaction().getStartingUnitNumbers(i); j++) {
				startingUnits.add(factory.newUnit(unitType, player));
			}
		}

		assertEquals(3, pridewater.getArea(2).getUnitLimit());

		// Add zergling to #1, add hydra to #1, remove hydra from #1
		String data = "";
		data += "1 2 1 ";
		data += "1 1 1 ";
		data += "2 1 ";
		// Add hydra to #3, add 2 zerglings to #3, add zergling and cancel twice
		data += "1 1 3 1 1 3 1 1 3 ";
		data += "1 1 3 2 ";
		// Place transport
		data += "3 1 ";
		// Remove transport
		data += "3 1 ";
		// Place transport
		data += "3 1 ";
		// Add zergling to #1
		data += "1 1 1 ";

		TextIHM.scanner = new Scanner(data);

		menu = new MultiMenuPlaceStartingForces(pridewater, startingUnits, player.getFaction().getStartingTransports(), player);
		MultiMenuPlaceStartingForcesChoices choices = menu.doSelection(factory);

		for (int i = 0; i < choices.getPlacedUnits().size(); i++) {
			Unit unit = choices.getPlacedUnits().get(i);
			Area area = choices.getPlacedUnitsAreas().get(i);
			area.addUnit(unit);
		}

		for (Route r : choices.getPlacedTransports()) {
			r.addTransport(player);
		}

		assertEquals(Arrays.asList("Zergling", "Zergling"), pridewater.getArea(0).getUnitNamesList());
		assertEquals(Arrays.asList("Hydralisk", "Zergling", "Zergling"), pridewater.getArea(2).getUnitNamesList());
		assertTrue(pridewater.getArea(2).isFull());
		assertTrue(pridewater.getRoute(Cardinal.NORTH).hasTransport(player));
	}

}
