package abstraction;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import testsSetup.GameTests;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class CreatorTest extends GameTests {

	@Test
	public void testCreateBuilding() {
		Building pool = factory.newBuilding("Spawning Pool");

		assertEquals("Zergling", pool.getUnitForLevel(1));
		assertEquals("Hydralisk", pool.getUnitForLevel(2));
		assertEquals("Ultralisk", pool.getUnitForLevel(3));
		assertEquals(0, pool.getPriceForLevel(1).getMinerals());
		assertEquals(0, pool.getPriceForLevel(1).getGas());
		assertEquals(1, pool.getPriceForLevel(2).getMinerals());
		assertEquals(0, pool.getPriceForLevel(2).getGas());
		assertEquals(2, pool.getPriceForLevel(3).getMinerals());
		assertEquals(0, pool.getPriceForLevel(3).getGas());
	}

	@Test
	public void testCreateUnit() {
		Player wiwi = factory.newPlayer("Wiwi");
		Player lolo = factory.newPlayer("Lolo");

		Planet pridewater = factory.newPlanet("Pridewater");
		Area area0 = pridewater.getArea(0);
		Area area1 = pridewater.getArea(1);

		area0.addUnit(factory.newUnit("Zergling", wiwi));
		area0.addUnit(factory.newUnit("Zergling", wiwi));
		area0.addUnit(factory.newUnit("Hydralisk", wiwi));

		area1.addUnit(factory.newUnit("Hydralisk", lolo));
		area1.addUnit(factory.newUnit("Hydralisk", lolo));

		List<Unit> area0Units = area0.getUnitList();
		assertEquals("Zergling", area0Units.get(0).getName());
		assertEquals("Zergling", area0Units.get(1).getName());
		assertEquals("Hydralisk", area0Units.get(2).getName());
		assertEquals("Wiwi", area0Units.get(0).getOwner().getName());

		List<Unit> area1Units = area1.getUnitList();
		assertEquals("Hydralisk", area1Units.get(0).getName());
		assertEquals("Hydralisk", area1Units.get(1).getName());
		assertEquals("Lolo", area1Units.get(0).getOwner().getName());

		List<Unit> wiwiUnits = wiwi.getUnits();
		assertEquals("Zergling", wiwiUnits.get(0).getName());
		assertEquals("Zergling", wiwiUnits.get(1).getName());
		assertEquals("Hydralisk", wiwiUnits.get(2).getName());
		assertEquals("Pridewater's #1 area", wiwiUnits.get(0).getArea().toString());
	}

	@Test
	public void testCreatePlanet() {
		Galaxy galaxy = factory.newGalaxy();
		Planet pridewater = factory.newPlanet("Pridewater");
		Planet chauSara = factory.newPlanet("Chau Sara");
		Planet tarsonis = factory.newPlanet("Tarsonis");
		Planet braken = factory.newPlanet("Braken");

		galaxy.add(pridewater);
		tarsonis.rotateClockwise();
		galaxy.add(tarsonis, pridewater, Cardinal.EAST, factory);
		chauSara.rotateCounterClockwise();
		galaxy.add(chauSara, pridewater, Cardinal.NORTH, factory);
		galaxy.add(braken, tarsonis, Cardinal.SOUTH, factory);

		assertEquals(" | \n" + " P-\n" + " | \n", pridewater.showTextGraph());
		assertEquals("C \n" + "PT\n" + " B\n", galaxy.toString());
		assertEquals("Chau Sara's #2 area", chauSara.getArea(1).toString());
	}

}
