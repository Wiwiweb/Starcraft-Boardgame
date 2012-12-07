package tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import abstraction.Area;
import abstraction.Building;
import abstraction.Galaxy;
import abstraction.Planet;
import abstraction.Player;
import abstraction.Unit;
import abstraction.creators.BuildingCreator;
import abstraction.creators.PlanetCreator;
import abstraction.creators.UnitCreator;
import abstraction.patterns.PlanetPattern.Cardinal;

/**
 * @author William Gautier
 */
public class CreatorTests extends Tests {

	@Test
	public void testCreateBuilding() {
		Building pool = BuildingCreator.createBuilding("Spawning Pool");

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
		Player wiwi = new Player("Wiwi");
		Player lolo = new Player("Lolo");

		Planet pridewater = PlanetCreator.createPlanet("Pridewater");
		Area area0 = pridewater.getArea(0);
		Area area1 = pridewater.getArea(1);

		area0.addUnit(UnitCreator.createUnit("Zergling", wiwi));
		area0.addUnit(UnitCreator.createUnit("Zergling", wiwi));
		area0.addUnit(UnitCreator.createUnit("Hydralisk", wiwi));

		area1.addUnit(UnitCreator.createUnit("Hydralisk", lolo));
		area1.addUnit(UnitCreator.createUnit("Hydralisk", lolo));

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
		Galaxy galaxy = new Galaxy();
		Planet pridewater = PlanetCreator.createPlanet("Pridewater");
		Planet chauSara = PlanetCreator.createPlanet("Chau Sara");
		Planet tarsonis = PlanetCreator.createPlanet("Tarsonis");
		Planet braken = PlanetCreator.createPlanet("Braken");

		galaxy.add(pridewater);
		tarsonis.rotateClockwise();
		galaxy.add(tarsonis, pridewater, Cardinal.EAST);
		chauSara.rotateCounterClockwise();
		galaxy.add(chauSara, pridewater, Cardinal.NORTH);
		galaxy.add(braken, tarsonis, Cardinal.SOUTH);

		assertEquals(" | \n" + " P-\n" + " | \n", pridewater.showTextGraph());
		assertEquals("C \n" + "PT\n" + " B\n", galaxy.toString());
		assertEquals("Chau Sara's #2 area", chauSara.getArea(1).toString());
	}

}
