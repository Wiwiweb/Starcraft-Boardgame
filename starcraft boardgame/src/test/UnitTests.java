package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import tools.XmlParser;
import abstraction.Area;
import abstraction.Base;
import abstraction.Building;
import abstraction.Faction;
import abstraction.Galaxy;
import abstraction.Planet;
import abstraction.Player;
import abstraction.Planet.Cardinal;
import abstraction.creators.BuildingCreator;
import abstraction.creators.UnitCreator;

public class UnitTests {

	@BeforeClass
	public static void initializeXml() {
		XmlParser.getAll();
	}
	
//	@Test
//	public void testBuyUnit() {
//		Player wiwi = new Player("Wiwi", Faction.getFaction("Overmind"));
//		Planet tarsonis = Galaxy.getPlanet("Tarsonis");
//		wiwi.buyUnit("Zergling", tarsonis);
//	}

	
	@Test
	public void testBuildUnit() {
		Player wiwi = new Player("Wiwi", Faction.getFaction("Overmind"));
		
		assertEquals("Zergling", wiwi.getBase().getBuilding(0).getUnitForLevel(1));
		
		assertTrue(wiwi.getBase().isAvailableUnit("Zergling"));
		assertFalse(wiwi.getBase().isAvailableUnit("Hydralisk"));
		
		assertEquals(1, wiwi.getBase().getBuilding(0).getLevel());
		wiwi.getBase().getBuilding(0).increaseLevel();
		assertEquals(2, wiwi.getBase().getBuilding(0).getLevel());
		
		assertTrue(wiwi.getBase().isAvailableUnit("Hydralisk"));
	}
	
	@Test
	public void testFactions() {		
		Player wiwi = new Player("Wiwi", Faction.getFaction("Overmind"));
		Player lolo = new Player("Lolo", Faction.getFaction("Queen of Blades"));

		assertEquals("Wiwi", wiwi.getName());
		assertEquals("Overmind", wiwi.getFaction().getName());
		assertEquals("Hydralisk", lolo.getFaction().getStartingUnitTypes(1));
		assertEquals("Zerg Base", lolo.getFaction().getBaseName());
	}

	@Test
	public void testBases() {
		Player wiwi = new Player("Wiwi", Faction.getFaction("Overmind"));
		Base zergBase = wiwi.getBase();
		
		assertEquals(2, zergBase.getBasePrice().getMinerals());
		assertEquals(0, zergBase.getBasePrice().getGas());
		assertEquals("Queen's Nest: Can build Queen Defiler ", zergBase.getBuilding(1).toString());
		assertEquals(1, zergBase.getBuilding(1).getPriceForLevel(2).getMinerals());
		assertEquals(1, zergBase.getBuilding(1).getPriceForLevel(2).getGas());
		assertEquals(0, UnitCreator.getPrice(zergBase.getBuilding(1).getUnitForLevel(2)).getMinerals());
		assertEquals(2, UnitCreator.getPrice(zergBase.getBuilding(1).getUnitForLevel(2)).getGas());
	}

	@Test
	public void testBuildings() {
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
	public void testUnits() {
		Player wiwi = new Player("Wiwi", Faction.getFaction("Overmind"));
		Player lolo = new Player("Lolo", Faction.getFaction("Overmind"));

		Planet pridewater = Galaxy.getPlanet("Pridewater");
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
	public void testPlanets() {
		Galaxy galaxy = new Galaxy();
		Planet pridewater = Galaxy.getPlanet("Pridewater");
		Planet chauSara = Galaxy.getPlanet("Chau Sara");
		Planet tarsonis = Galaxy.getPlanet("Tarsonis");
		Planet braken = Galaxy.getPlanet("Braken");

		galaxy.add(pridewater);
		tarsonis.rotateClockwise();
		galaxy.add(tarsonis, pridewater, Cardinal.EAST);
		chauSara.rotateClockwise();
		galaxy.add(chauSara, pridewater, Cardinal.SOUTH);
		galaxy.add(braken, tarsonis, Cardinal.SOUTH);

		assertEquals(" | \n" + " P-\n" + " | \n", pridewater.showTextGraph());
		assertEquals("PT\n" + "CB\n", galaxy.toString());
		assertEquals("Chau Sara's #2 area", chauSara.getArea(1).toString());
	}

}
