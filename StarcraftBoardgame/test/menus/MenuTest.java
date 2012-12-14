package menus;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import presentation.text.TextIHM;
import tests.Tests;
import abstraction.Game;
import abstraction.Player;
import abstraction.menus.MenuStaticChoices;
import abstraction.menus.MenuStaticChoices.StaticChoice;
import abstraction.menus.MenuStaticChoices.StaticChoicesMenuName;

/**
 * @author William Gautier
 */
public class MenuTest extends Tests {

	private Player player;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		player = factory.newPlayer("Player");
	}
	
	@Test
	public void testDisabledStaticMenu() {
		StaticChoice[] disabledChoices = { StaticChoice.PLACE_REMOVE_UNIT_REMOVE_UNIT };
		MenuStaticChoices menu = Game.factory.newMenuStaticChoices(StaticChoicesMenuName.PLACE_REMOVE_UNIT,
				Arrays.asList(disabledChoices), player);
		String data = "2 ";

		TextIHM.scanner = new Scanner(data);
		StaticChoice choice = menu.selectChoice();
		assertEquals(StaticChoice.PLACE_REMOVE_UNIT_PLACE_TRANSPORT, choice);
	}

	@Test
	public void testStaticMenu() {
		MenuStaticChoices menu = Game.factory.newMenuStaticChoices(StaticChoicesMenuName.PLACE_REMOVE_UNIT, player);
		String data = "2 ";

		TextIHM.scanner = new Scanner(data);
		StaticChoice choice = menu.selectChoice();
		assertEquals(StaticChoice.PLACE_REMOVE_UNIT_REMOVE_UNIT, choice);
	}

}
