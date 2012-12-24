package menus;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import presentation.text.TextIHM;
import tests.Tests;
import abstraction.Galaxy;
import abstraction.Game;
import abstraction.Order;
import abstraction.Order.OrderType;
import abstraction.Planet;
import abstraction.Player;
import abstraction.menus.multimenus.MultiMenuChooseOrder;
import abstraction.menus.multimenus.states.MultiMenuChooseOrderChoices;

/**
 * @author William Gautier
 */
public class MultiMenuChooseOrderTest extends Tests {

	private Game game;
	private Galaxy galaxy;
	private Player player;

	private MultiMenuChooseOrder menu;

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
	 * Test method for {@link abstraction.menus.multimenus.MultiMenuChooseOrder#doSelection(abstraction.Factory)}.
	 */
	@Test
	public void testDoSelectionFactory() {
		Planet abaddon = factory.newPlanet("Abaddon");
		Planet tarsonis = factory.newPlanet("Tarsonis");
		Planet pridewater = factory.newPlanet("Pridewater");

		generateBasicGalaxy(galaxy, abaddon, tarsonis, pridewater);

		Order order1 = new Order(OrderType.BUILD, player, abaddon);
		Order order2 = new Order(OrderType.BUILD, player, tarsonis);
		abaddon.addOrderToTop(order1);
		tarsonis.addOrderToTop(order2);

		Set<Order> orders = new HashSet<Order>(Arrays.asList(order1, order2));

		// Abaddon, Cancel, Tarsonis, Execute
		String data = "1 3 2 1 ";
		TextIHM.scanner = new Scanner(data);

		menu = new MultiMenuChooseOrder(orders, player);
		MultiMenuChooseOrderChoices choices = menu.doSelection(factory);

		assertSame(order2, choices.getOrderChosen());
		assertFalse(choices.isEventCard());
	}
}
