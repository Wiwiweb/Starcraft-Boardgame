package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import abstraction.Area;
import abstraction.Planet;
import abstraction.Player;
import abstraction.Resource;
import abstraction.Resource.ResourceType;
import abstraction.Unit;
import abstraction.creators.PlanetCreator;
import abstraction.creators.UnitCreator;

/**
 * @author William Gautier
 */
public class AreaTest extends Tests {

	private Player player;
	private Planet abaddon;

	private Area area;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		abaddon = PlanetCreator.createPlanet("Abaddon");
		player = new Player("Player");
		player.setFaction("Overmind");
		area = abaddon.getArea(0);
	}

	/**
	 * Test method for {@link abstraction.Area#addUnit(abstraction.Unit)}.
	 */
	@Test
	public void testAddUnit1() {
		Unit zergling = UnitCreator.createUnit("Zergling", player);
		area.addUnit(zergling);

		assertEquals(1, area.getUnitNumber());
		assertTrue(area.getUnitList().contains(zergling));
	}

	/**
	 * Test method for {@link abstraction.Area#addUnit(abstraction.Unit)}.
	 * Test exception when area is full.
	 */
	@Test(expected = IllegalStateException.class)
	public void testAddUnit2() {
		Unit zergling1 = UnitCreator.createUnit("Zergling", player);
		Unit zergling2 = UnitCreator.createUnit("Zergling", player);
		Unit zergling3 = UnitCreator.createUnit("Zergling", player);
		Unit zergling4 = UnitCreator.createUnit("Zergling", player);
		area.addUnit(zergling1);
		area.addUnit(zergling2);
		area.addUnit(zergling3);
		area.addUnit(zergling4);
	}
	
	/**
	 * Test method for {@link abstraction.Area#addUnit(abstraction.Unit)}.
	 * Test exception when adding enemy unit.
	 */
	@Test(expected = IllegalStateException.class)
	public void testAddUnit3() {
		Player player2 = new Player("Player 2");
		Unit zergling1 = UnitCreator.createUnit("Zergling", player);
		Unit zergling2 = UnitCreator.createUnit("Zergling", player2);
		area.addUnit(zergling1);
		area.addUnit(zergling2);
	}

	/**
	 * Test method for {@link abstraction.Area#removeUnit(abstraction.Unit)}.
	 */
	@Test
	public void testRemoveUnit() {
		Unit zergling = UnitCreator.createUnit("Zergling", player);
		area.addUnit(zergling);
		area.removeUnit(zergling);

		assertEquals(0, area.getUnitNumber());
		assertFalse(area.getUnitList().contains(zergling));
	}

	/**
	 * Test method for {@link abstraction.Area#buildBaseOwnedBy(abstraction.Player)}.
	 */
	@Test
	public void testBuildBaseOwnedBy() {
		area.buildBaseOwnedBy(player);
		assertSame(player, area.getBaseOwner());
	}

	/**
	 * Test method for {@link abstraction.Area#getPlanet()}.
	 */
	@Test
	public void testGetPlanet() {
		assertSame(abaddon, area.getPlanet());
	}

	/**
	 * Test method for {@link abstraction.Area#getResource()}.
	 */
	@Test
	public void testGetResource() {
		Resource resource = area.getResource();

		assertSame(ResourceType.MINERALS, resource.getResourceType());
		assertEquals(3, resource.getResourceNum());
	}

	/**
	 * Test method for {@link abstraction.Area#isControlledBy(abstraction.Player)}.
	 * Test with a base. Returns true.
	 */
	@Test
	public void testIsControlledBy1() {
		area.buildBaseOwnedBy(player);

		assertTrue(area.isControlledBy(player));
	}

	/**
	 * Test method for {@link abstraction.Area#isControlledBy(abstraction.Player)}.
	 * Test with a unit. Returns true.
	 */
	@Test
	public void testIsControlledBy2() {
		Unit zergling = UnitCreator.createUnit("Zergling", player);
		area.addUnit(zergling);

		assertTrue(area.isControlledBy(player));
	}

	/**
	 * Test method for {@link abstraction.Area#isControlledBy(abstraction.Player)}.
	 * Test with an enemy unit. Returns false.
	 */
	@Test
	public void testIsControlledBy3() {
		Player player2 = new Player("Player 2");
		Unit zergling = UnitCreator.createUnit("Zergling", player2);
		area.addUnit(zergling);

		assertFalse(area.isControlledBy(player));
	}

	/**
	 * Test method for {@link abstraction.Area#isControlledBy(abstraction.Player)}.
	 * Test with nothing. Returns false.
	 */
	@Test
	public void testIsControlledBy4() {
		assertFalse(area.isControlledBy(player));
	}

	/**
	 * Test method for {@link abstraction.Area#getUnitNumber()}.
	 */
	@Test
	public void testGetUnitNumber() {
		Unit zergling1 = UnitCreator.createUnit("Zergling", player);
		Unit zergling2 = UnitCreator.createUnit("Zergling", player);
		area.addUnit(zergling1);
		area.addUnit(zergling2);

		assertEquals(2, area.getUnitNumber());
	}

	/**
	 * Test method for {@link abstraction.Area#getUnitList()}.
	 */
	@Test
	public void testGetUnitList() {
		Unit zergling1 = UnitCreator.createUnit("Zergling", player);
		Unit zergling2 = UnitCreator.createUnit("Zergling", player);
		area.addUnit(zergling1);
		area.addUnit(zergling2);

		List<Unit> list = area.getUnitList();
		assertTrue(list.contains(zergling1));
		assertTrue(list.contains(zergling2));
	}

	/**
	 * Test method for {@link abstraction.Area#isEmpty()}.
	 * Returns true.
	 */
	@Test
	public void testIsEmpty1() {
		assertTrue(area.isEmpty());
	}

	/**
	 * Test method for {@link abstraction.Area#isEmpty()}.
	 * Returns false.
	 */
	@Test
	public void testIsEmpty2() {
		Unit zergling = UnitCreator.createUnit("Zergling", player);
		area.addUnit(zergling);

		assertFalse(area.isEmpty());
	}

	/**
	 * Test method for {@link abstraction.Area#isFull()}.
	 */
	@Test
	public void testIsFull() {
		Unit zergling1 = UnitCreator.createUnit("Zergling", player);
		Unit zergling2 = UnitCreator.createUnit("Zergling", player);
		Unit zergling3 = UnitCreator.createUnit("Zergling", player);
		area.addUnit(zergling1);
		area.addUnit(zergling2);
		area.addUnit(zergling3);
		
		assertEquals(3, area.getUnitLimit());
		assertTrue(area.isFull());
	}

}
