package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import abstraction.Base;
import abstraction.Game;
import abstraction.Player;
import abstraction.creators.UnitCreator;

/**
 * @author William Gautier
 */
public class VariousTest extends Tests {

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
	public void testIncreaseLevel() {
		Player wiwi = new Player("Wiwi");
		wiwi.setFaction("Overmind");

		assertEquals("Zergling", wiwi.getBase().getBuilding(0).getUnitForLevel(1));

		assertTrue(wiwi.getBase().isAvailableUnit("Zergling"));
		assertFalse(wiwi.getBase().isAvailableUnit("Hydralisk"));

		assertEquals(1, wiwi.getBase().getBuilding(0).getLevel());
		wiwi.getBase().getBuilding(0).increaseLevel();
		assertEquals(2, wiwi.getBase().getBuilding(0).getLevel());

		assertTrue(wiwi.getBase().isAvailableUnit("Hydralisk"));
	}

	@Test
	public void testFaction() {
		Player wiwi = new Player("Wiwi");
		wiwi.setFaction("Overmind");
		Player lolo = new Player("Lolo");
		lolo.setFaction("Queen of Blades");

		assertEquals("Wiwi", wiwi.getName());
		assertEquals("Overmind", wiwi.getFaction().getName());
		assertEquals("Hydralisk", lolo.getFaction().getStartingUnitTypes(1));
		assertEquals("Zerg Base", lolo.getFaction().getBaseName());
	}

	@Test
	public void testBase() {
		Player wiwi = new Player("Wiwi");
		wiwi.setFaction("Overmind");
		Base zergBase = wiwi.getBase();

		assertEquals(2, zergBase.getBasePrice().getMinerals());
		assertEquals(0, zergBase.getBasePrice().getGas());
		assertEquals("Queen's Nest: Can build Queen Defiler ", zergBase.getBuilding(1).toString());
		assertEquals(1, zergBase.getBuilding(1).getPriceForLevel(2).getMinerals());
		assertEquals(1, zergBase.getBuilding(1).getPriceForLevel(2).getGas());
		assertEquals(0, UnitCreator.getPrice(zergBase.getBuilding(1).getUnitForLevel(2)).getMinerals());
		assertEquals(2, UnitCreator.getPrice(zergBase.getBuilding(1).getUnitForLevel(2)).getGas());
	}
}
