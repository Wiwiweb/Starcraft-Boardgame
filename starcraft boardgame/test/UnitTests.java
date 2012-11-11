import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import presentation.text.TextIHM;
import tools.XmlParser;
import abstraction.Area;
import abstraction.Base;
import abstraction.Building;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Planet;
import abstraction.Player;
import abstraction.creators.BuildingCreator;
import abstraction.creators.FactionCreator;
import abstraction.creators.PlanetCreator;
import abstraction.creators.UnitCreator;
import abstraction.patterns.PlanetPattern.Cardinal;

public class UnitTests {

	@BeforeClass
	public static void initializeXml() {
		XmlParser.getAll();
	}
	
	@Test
	public void placeZAxisNoMoreSpots() {
		Game game = new Game();
		Player a = new Player("A");
		Player b = new Player("B");
		Player c = new Player("C");
		game.addPlayer(a);
		game.addPlayer(b);
		game.addPlayer(c);
		game.setFirstPlayer(a);

		Galaxy galaxy = game.getGalaxy();

		Planet abaddon = PlanetCreator.createPlanet("Abaddon");
		Planet tarsonis = PlanetCreator.createPlanet("Tarsonis");
		Planet pridewater = PlanetCreator.createPlanet("Pridewater");

		galaxy.add(abaddon);
		galaxy.add(tarsonis, abaddon, Cardinal.WEST);
		galaxy.add(pridewater, tarsonis, Cardinal.NORTH);

		assertEquals("P \nTA\n", galaxy.toString());

		try {
			final Method[] methods = game.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals("placeZAxis")) {
					method.setAccessible(true);
					final Object[] args = { game.getPlayerListByOrder() };
					String data = "";
					// A: Pridewater north, abaddon east
					data += "2 1 ";
					// B: Pridewater east, tarsonic south
					data += "1 1 ";
					// C: No more spots left
							
					TextIHM.scanner = new Scanner(data);
					method.invoke(game, args);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void placeZAxisNoMoreLegalSpots() {
		Game game = new Game();
		Player a = new Player("A");
		Player b = new Player("B");
		Player c = new Player("C");
		game.addPlayer(a);
		game.addPlayer(b);
		game.addPlayer(c);
		game.setFirstPlayer(a);

		Galaxy galaxy = game.getGalaxy();

		Planet abaddon = PlanetCreator.createPlanet("Abaddon");
		Planet tarsonis = PlanetCreator.createPlanet("Tarsonis");
		Planet pridewater = PlanetCreator.createPlanet("Pridewater");

		galaxy.add(abaddon);
		galaxy.add(tarsonis, abaddon, Cardinal.WEST);
		galaxy.add(pridewater, tarsonis, Cardinal.NORTH);

		assertEquals("P \nTA\n", galaxy.toString());

		try {
			final Method[] methods = game.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals("placeZAxis")) {
					method.setAccessible(true);
					final Object[] args = { game.getPlayerListByOrder() };
					String data = "";
					// A: Pridewater north, cancel, abaddon east, tarsonis south
					data += "3 3 1 3 ";
					// B: No more legal spots, stop zaxis placement

					TextIHM.scanner = new Scanner(data);
					method.invoke(game, args);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void placePlanets() {
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
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testOrderedPlayers() {
		Game game = new Game();
		Player a = new Player("A");
		Player b = new Player("B");
		Player c = new Player("C");
		Player d = new Player("D");
		game.addPlayer(a);
		game.addPlayer(b);
		game.addPlayer(c);
		game.addPlayer(d);

		game.setFirstPlayer(c);

		assertEquals(Arrays.asList(a, b, c, d), game.getPlayerList());
		assertEquals(Arrays.asList(c, d, a, b), game.getPlayerListByOrder());
	}

	// @Test
	// public void testBuyUnit() {
	// Player wiwi = new Player("Wiwi", Faction.getFaction("Overmind"));
	// Planet tarsonis = Galaxy.getPlanet("Tarsonis");
	// wiwi.buyUnit("Zergling", tarsonis);
	// }

	@Test
	public void testBuildUnit() {
		Player wiwi = new Player("Wiwi");
		wiwi.setFaction(FactionCreator.getFaction("Overmind"));

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
		Player wiwi = new Player("Wiwi");
		wiwi.setFaction(FactionCreator.getFaction("Overmind"));
		Player lolo = new Player("Lolo");
		lolo.setFaction(FactionCreator.getFaction("Queen of Blades"));

		assertEquals("Wiwi", wiwi.getName());
		assertEquals("Overmind", wiwi.getFaction().getName());
		assertEquals("Hydralisk", lolo.getFaction().getStartingUnitTypes(1));
		assertEquals("Zerg Base", lolo.getFaction().getBaseName());
	}

	@Test
	public void testBases() {
		Player wiwi = new Player("Wiwi");
		wiwi.setFaction(FactionCreator.getFaction("Overmind"));
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
	public void testPlanets() {
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
