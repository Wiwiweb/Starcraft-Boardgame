package abstraction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import testsSetup.GameTests;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class PlanetTest extends GameTests {

	private Game game;
	private Galaxy galaxy;
	private Player player;

	private Planet abaddon;
	private Planet tarsonis;
	private Planet pridewater;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		game = factory.newGame();
		galaxy = game.getGalaxy();
		player = factory.newPlayer("Player");
		abaddon = factory.newPlanet("Abaddon");
		tarsonis = factory.newPlanet("Tarsonis");
		pridewater = factory.newPlanet("Pridewater");

		generateBasicGalaxy(galaxy, abaddon, tarsonis, pridewater);
	}

	/**
	 * Test method for {@link abstraction.Planet#getBuildableAreas(abstraction.Player)}.
	 */
	@Test
	public void testGetBuildableAreas() {
		Player player = factory.newPlayer("Player");

		pridewater.getArea(2).addUnit(factory.newUnit("Zergling", player));
		pridewater.getArea(2).addUnit(factory.newUnit("Zergling", player));
		pridewater.getArea(2).addUnit(factory.newUnit("Zergling", player));

		List<Area> buildableAreas = pridewater.getBuildableAreas(player);

		assertEquals(Arrays.asList(pridewater.getArea(0), pridewater.getArea(1)), buildableAreas);
	}

	/**
	 * Test method for {@link abstraction.Planet#getBuildableAreasPlusUnits(abstraction.Player, java.util.List)}.
	 */
	@Test
	public void testGetBuildableAreasPlusUnits2() {
		List<Area> additionnalUnits = Arrays.asList(pridewater.getArea(2), pridewater.getArea(2), pridewater.getArea(2));

		List<Area> buildableAreas = pridewater.getBuildableAreasPlusUnits(player, additionnalUnits);

		assertEquals(Arrays.asList(pridewater.getArea(0), pridewater.getArea(1)), buildableAreas);
	}

	/**
	 * Test method for {@link abstraction.Planet#getBuildableAreasPlusUnits(abstraction.Player, java.util.List)}.
	 */
	@Test
	public void testGetBuildableAreasPlusUnits1() {
		pridewater.getArea(1).addUnit(factory.newUnit("Zergling", player));
		pridewater.getArea(1).addUnit(factory.newUnit("Zergling", player));

		pridewater.getArea(2).addUnit(factory.newUnit("Zergling", player));
		pridewater.getArea(2).addUnit(factory.newUnit("Zergling", player));
		pridewater.getArea(2).addUnit(factory.newUnit("Zergling", player));

		List<Area> additionnalUnits = Arrays.asList(pridewater.getArea(1), pridewater.getArea(1));

		List<Area> buildableAreas = pridewater.getBuildableAreasPlusUnits(player, additionnalUnits);

		assertEquals(Arrays.asList(pridewater.getArea(0)), buildableAreas);
	}

	/**
	 * Test method for {@link abstraction.Planet#getRoutes(abstraction.Player)}.
	 */
	@Test
	public void testGetRoutes() {
		List<Route> routes = tarsonis.getRoutes();

		assertTrue(routes.contains(tarsonis.getRoute(Cardinal.WEST)));
		assertTrue(routes.contains(tarsonis.getRoute(Cardinal.SOUTH)));
		assertEquals(2, routes.size());
	}

	/**
	 * Test method for {@link abstraction.Planet#getRoutesWithTransports(abstraction.Player)}.
	 */
	@Test
	public void testGetRoutesWithTransports() {
		tarsonis.getRoute(Cardinal.WEST).addTransport(player);
		List<Route> routes = tarsonis.getRoutesWithTransports(player);

		assertEquals(Arrays.asList(tarsonis.getRoute(Cardinal.WEST)), routes);
	}

	/**
	 * Test method for {@link abstraction.Planet#getRoutesWithNoTransports(abstraction.Player)}.
	 */
	@Test
	public void testGetRoutesWithNoTransports() {
		tarsonis.getRoute(Cardinal.WEST).addTransport(player);
		List<Route> routes = tarsonis.getRoutesWithNoTransports(player);

		assertEquals(Arrays.asList(tarsonis.getRoute(Cardinal.SOUTH)), routes);
	}

	/**
	 * Test method for {@link abstraction.Planet#hasFriendlyUnitOrBase(abstraction.Player)}.
	 * Test with a base. Returns true.
	 */
	@Test
	public void testHasFriendlyUnitOrBase1() {
		player.setFaction("Overmind", factory);
		player.placeBase(tarsonis.getArea(0));

		assertTrue(tarsonis.hasFriendlyUnitOrBase(player));
	}

	/**
	 * Test method for {@link abstraction.Planet#hasFriendlyUnitOrBase(abstraction.Player)}.
	 * Test with a unit. Returns true.
	 */
	@Test
	public void testHasFriendlyUnitOrBase2() {
		Unit zergling = factory.newUnit("Zergling", player);
		tarsonis.getArea(1).addUnit(zergling);

		assertTrue(tarsonis.hasFriendlyUnitOrBase(player));
	}

	/**
	 * Test method for {@link abstraction.Planet#hasFriendlyUnitOrBase(abstraction.Player)}.
	 * Test with enemy unit. Returns false.
	 */
	@Test
	public void testHasFriendlyUnitOrBase3() {
		Player player2 = factory.newPlayer("Player2");

		Unit zergling = factory.newUnit("Zergling", player2);
		tarsonis.getArea(1).addUnit(zergling);

		assertFalse(tarsonis.hasFriendlyUnitOrBase(player));
	}

	/**
	 * Test method for {@link abstraction.Planet#hasFriendlyUnitOrBase(abstraction.Player)}.
	 * Test with nothing. Returns false.
	 */
	@Test
	public void testHasFriendlyUnitOrBase4() {
		assertFalse(tarsonis.hasFriendlyUnitOrBase(player));
	}

}
