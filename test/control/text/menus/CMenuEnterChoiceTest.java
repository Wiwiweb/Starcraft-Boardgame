package control.text.menus;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import presentation.text.TextIHM;
import testsSetup.GameTests;
import abstraction.Player;
import abstraction.menus.MenuEnterChoice;
import abstraction.menus.MenuEnterChoice.EnterChoiceMenuName;

/**
 * @author William Gautier
 */
public class CMenuEnterChoiceTest extends GameTests {

	private Player player;
	private MenuEnterChoice<?> menu;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		player = factory.newPlayer("Player");
	}

	/**
	 * Test method for {@link control.text.menus.CMenuEnterChoice#selectChoice(boolean)}.
	 */
	@Test
	public void testSelectChoice() {
		menu = factory.newMenuEnterChoice(EnterChoiceMenuName.HOW_MANY_WORKERS, Integer.class, player);

		String data = "2";
		TextIHM.scanner = new Scanner(data);
		Integer choice = (Integer) menu.selectChoice(false);

		assertEquals(new Integer(2), choice);
	}

	/**
	 * Test method for {@link control.text.menus.CMenuEnterChoice#selectChoice(boolean)}.
	 */
	@Test
	public void testSelectChoiceCancel() {
		menu = factory.newMenuEnterChoice(EnterChoiceMenuName.HOW_MANY_WORKERS, Integer.class, player);

		String data = "Cancel";
		TextIHM.scanner = new Scanner(data);
		Integer choice = (Integer) menu.selectChoice(true);

		assertEquals(null, choice);
	}

}
