package abstraction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import testsSetup.GameTests;
import tools.PlanetEntrance;
import tools.PlanetPosition;
import abstraction.patterns.PlanetPattern.Cardinal;
import control.text.CFactory;

/**
 * @author William Gautier
 */
public class GalaxyTest extends GameTests {

	private Game game;
	private Player player;
	private Planet abaddon;
	private Planet tarsonis;
	private Planet pridewater;

	private Galaxy galaxy;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Factory factory = new CFactory();
		game = factory.newGame();
		player = factory.newPlayer("Player");
		abaddon = factory.newPlanet("Abaddon");
		tarsonis = factory.newPlanet("Tarsonis");
		pridewater = factory.newPlanet("Pridewater");
		galaxy = game.getGalaxy();
		player.setFaction("Overmind", factory);
	}

	/**
	 * Test method for {@link abstraction.Galaxy#add(abstraction.Planet)}.
	 */
	@Test
	public void testAddPlanet() {
		galaxy.add(abaddon);
		Set<Planet> planets = galaxy.getPlanets();

		assertTrue(planets.contains(abaddon));
		assertSame(abaddon, galaxy.getPlanetAt(new PlanetPosition(0, 0)));
	}

	/**
	 * Test method for
	 * {@link abstraction.Galaxy#add(abstraction.Planet, abstraction.Planet, abstraction.patterns.PlanetPattern.Cardinal)}.
	 */
	@Test
	public void testAddPlanetPlanetCardinal() {
		galaxy.add(abaddon);
		tarsonis.rotateClockwise();
		galaxy.add(tarsonis, abaddon, Cardinal.WEST, factory);
		Set<Planet> planets = galaxy.getPlanets();

		assertTrue(planets.contains(tarsonis));
		assertSame(tarsonis, galaxy.getPlanetAt(new PlanetPosition(-1, 0)));
		assertSame(tarsonis, abaddon.getRoute(Cardinal.WEST).getDestinationFrom(abaddon));
		assertSame(abaddon, tarsonis.getRoute(Cardinal.EAST).getDestinationFrom(tarsonis));
		assertSame(abaddon.getRoute(Cardinal.WEST), tarsonis.getRoute(Cardinal.EAST));
	}

	/**
	 * Test method for {@link abstraction.Galaxy#add(abstraction.Planet, tools.PlanetEntrance)}.
	 */
	@Test
	public void testAddPlanetPlanetEntrance() {
		galaxy.add(abaddon);
		tarsonis.rotateClockwise();
		galaxy.add(tarsonis, new PlanetEntrance(abaddon, Cardinal.EAST), factory);
		Set<Planet> planets = galaxy.getPlanets();

		assertTrue(planets.contains(tarsonis));
		assertSame(tarsonis, galaxy.getPlanetAt(new PlanetPosition(1, 0)));
		assertSame(tarsonis, abaddon.getRoute(Cardinal.EAST).getDestinationFrom(abaddon));
		assertSame(abaddon, tarsonis.getRoute(Cardinal.WEST).getDestinationFrom(tarsonis));
		assertSame(abaddon.getRoute(Cardinal.EAST), tarsonis.getRoute(Cardinal.WEST));
	}

	/**
	 * Test method for {@link abstraction.Galaxy#getPlanetAt(tools.PlanetPosition)}.
	 */
	@Test
	public void testGetPlanetAt() {
		generateBasicGalaxy(galaxy, abaddon, tarsonis, pridewater);

		assertSame(tarsonis, galaxy.getPlanetAt(new PlanetPosition(0, 0)));
		assertSame(abaddon, galaxy.getPlanetAt(new PlanetPosition(-1, 0)));
		assertSame(pridewater, galaxy.getPlanetAt(new PlanetPosition(0, 1)));

	}

	/**
	 * Test method for {@link abstraction.Galaxy#isEmpty()}.
	 * When the galaxy is empty, return true.
	 */
	@Test
	public void testIsEmpty1() {
		assertTrue(galaxy.isEmpty());
	}

	/**
	 * Test method for {@link abstraction.Galaxy#isEmpty()}.
	 * When the galaxy has a planet, return false.
	 */
	@Test
	public void testIsEmpty2() {
		galaxy.add(tarsonis);
		assertFalse(galaxy.isEmpty());
	}

	/**
	 * Test method for {@link abstraction.Galaxy#getAvailableSpots()}.
	 */
	@Test
	public void testGetAvailableSpots() {
		generateBasicGalaxy(galaxy, abaddon, tarsonis, pridewater);
		Planet chauSara = factory.newPlanet("Chau Sara");
		Planet braken = factory.newPlanet("Braken");
		galaxy.add(chauSara, pridewater, Cardinal.EAST, factory);
		galaxy.add(braken, tarsonis, Cardinal.EAST, factory);

		Set<PlanetEntrance> spots = galaxy.getAvailableSpots();

		assertTrue(spots.contains(new PlanetEntrance(abaddon, Cardinal.WEST)));
		assertTrue(spots.contains(new PlanetEntrance(pridewater, Cardinal.SOUTH)));
		assertTrue(spots.contains(new PlanetEntrance(braken, Cardinal.NORTH)));
		assertFalse(spots.contains(new PlanetEntrance(chauSara, Cardinal.NORTH)));
		assertEquals(3, spots.size());
	}

	/**
	 * Test method for {@link abstraction.Galaxy#getAvailableOrderPlanets(abstraction.Player)}.
	 * Test with 2 out of 3 planets. Player has a base on Pridewater.
	 */
	@Test
	public void testGetAvailableOrderPlanet1() {
		generateBasicGalaxy(galaxy, abaddon, tarsonis, pridewater);
		player.placeBase(pridewater.getArea(0));
		Set<Planet> planets = galaxy.getAvailableOrderPlanets(player);

		assertFalse(planets.contains(abaddon));
		assertTrue(planets.contains(pridewater));
		assertTrue(planets.contains(tarsonis));
	}

	/**
	 * Test method for {@link abstraction.Galaxy#getAvailableOrderPlanets(abstraction.Player)}.
	 * Test with 3 out of 3 planets. Player has a unit on Tarsonis.
	 */
	@Test
	public void testGetAvailableOrderPlanet2() {
		generateBasicGalaxy(galaxy, abaddon, tarsonis, pridewater);
		Unit zergling = factory.newUnit("Zergling", player);
		tarsonis.getArea(1).addUnit(zergling);
		Set<Planet> planets = galaxy.getAvailableOrderPlanets(player);

		assertTrue(planets.contains(tarsonis));
		assertTrue(planets.contains(abaddon));
		assertTrue(planets.contains(pridewater));
	}

	/**
	 * Test method for {@link abstraction.Galaxy#getPlanets()}.
	 */
	@Test
	public void testGetPlanets() {
		galaxy.add(abaddon);
		galaxy.add(tarsonis, new PlanetEntrance(abaddon, Cardinal.WEST), factory);
		Set<Planet> planets = galaxy.getPlanets();
		assertTrue(planets.contains(abaddon));
		assertTrue(planets.contains(tarsonis));
		assertFalse(planets.contains(pridewater));
	}

}
