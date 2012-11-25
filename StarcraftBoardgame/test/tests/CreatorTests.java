package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import abstraction.Area;
import abstraction.Building;
import abstraction.Galaxy;
import abstraction.Planet;
import abstraction.Player;
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

		assertEquals("Wiwi's Zergling\n" + "Wiwi's Zergling\n" + "Wiwi's Hydralisk\n", area0.listUnits());
		assertEquals("Lolo's Hydralisk\n" + "Lolo's Hydralisk\n", area1.listUnits());
		assertEquals("Wiwi's army:\n" + "Zergling\n" + "Zergling\n" + "Hydralisk\n", wiwi.listUnits());
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
