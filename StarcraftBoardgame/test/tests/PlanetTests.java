package tests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import abstraction.Area;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.Player;
import abstraction.Route;
import abstraction.creators.PlanetCreator;
import abstraction.creators.UnitCreator;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class PlanetTests extends Tests {

	private Game game;
	private Galaxy galaxy;
	private Player player;
	private Planet abaddon;
	private Planet tarsonis;
	private Planet pridewater;

	@Before
	public void setUp() {
		game = new Game();
		galaxy = game.getGalaxy();

		player = new Player("Player");

		abaddon = PlanetCreator.createPlanet("Abaddon");
		tarsonis = PlanetCreator.createPlanet("Tarsonis");
		pridewater = PlanetCreator.createPlanet("Pridewater");

		galaxy.add(abaddon);
		tarsonis.rotateClockwise();
		galaxy.add(tarsonis, abaddon, Cardinal.EAST);
		galaxy.add(pridewater, tarsonis, Cardinal.SOUTH);
	}

	/**
	 * Test method for {@link abstraction.Planet#getBuildableAreas(abstraction.Player)}.
	 */
	@Test
	public void testGetBuildableAreas() {
		Player player = new Player("Player");

		pridewater.getArea(2).addUnit(UnitCreator.createUnit("Zergling", player));
		pridewater.getArea(2).addUnit(UnitCreator.createUnit("Zergling", player));
		pridewater.getArea(2).addUnit(UnitCreator.createUnit("Zergling", player));

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
		pridewater.getArea(1).addUnit(UnitCreator.createUnit("Zergling", player));
		pridewater.getArea(1).addUnit(UnitCreator.createUnit("Zergling", player));

		pridewater.getArea(2).addUnit(UnitCreator.createUnit("Zergling", player));
		pridewater.getArea(2).addUnit(UnitCreator.createUnit("Zergling", player));
		pridewater.getArea(2).addUnit(UnitCreator.createUnit("Zergling", player));

		List<Area> additionnalUnits = Arrays.asList(pridewater.getArea(1), pridewater.getArea(1));

		List<Area> buildableAreas = pridewater.getBuildableAreasPlusUnits(player, additionnalUnits);

		assertEquals(Arrays.asList(pridewater.getArea(0)), buildableAreas);
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

}
