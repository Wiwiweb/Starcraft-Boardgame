package menus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;


import org.junit.Before;
import org.junit.Test;

import presentation.text.TextIHM;
import tests.Tests;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Order.OrderType;
import abstraction.Planet;
import abstraction.Player;
import abstraction.menus.multimenus.MultiMenuPlaceOrder;
import abstraction.menus.multimenus.states.MultiMenuPlaceOrderChoices;

/**
 * @author William Gautier
 */
public class MultiMenuPlaceOrderTest extends Tests {

	private Game game;
	private Galaxy galaxy;
	private Player player;

	private MultiMenuPlaceOrder menu;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		game = factory.newGame();
		galaxy = game.getGalaxy();
		player = factory.newPlayer("Player");
	}

	/**
	 * Test method for {@link abstraction.menus.multimenus.MultiMenuPlaceOrder#doSelection()}.
	 */
	@Test
	public void testDoSelection() {
		Planet abaddon = factory.newPlanet("Abaddon");
		Planet tarsonis = factory.newPlanet("Tarsonis");
		Planet pridewater = factory.newPlanet("Pridewater");

		generateBasicGalaxy(galaxy, abaddon, tarsonis, pridewater);

		pridewater.getArea(0).buildBaseOwnedBy(player);

		// Mobilize, Pridewater
		String data = "2 1 ";
		TextIHM.scanner = new Scanner(data);
		menu = new MultiMenuPlaceOrder(galaxy.getAvailableOrderPlanets(player),
				new HashSet<OrderType>(Arrays.asList(OrderType.values())), player);
		MultiMenuPlaceOrderChoices choices = menu.doSelection();

		assertEquals(OrderType.MOBILIZE, choices.getOrderType());
		assertSame(pridewater, choices.getPlanet());
	}

}
