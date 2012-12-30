package control.text.menus;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import presentation.text.TextIHM;
import testsSetup.GameTests;
import abstraction.Player;
import abstraction.menus.MenuStaticChoices;
import abstraction.menus.MenuStaticChoices.StaticChoice;
import abstraction.menus.MenuStaticChoices.StaticChoicesMenuName;

/**
 * @author William Gautier
 */
public class CMenuStaticChoicesTest extends GameTests {

	private Player player;
	private MenuStaticChoices menu;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		player = factory.newPlayer("Player");
	}

	/**
	 * Test method for {@link control.text.menus.CMenuStaticChoices#selectChoice(boolean)}.
	 */
	@Test
	public void testSelectChoiceBoolean() {
		menu = factory.newMenuStaticChoices(StaticChoicesMenuName.PLACE_REMOVE_UNIT, player);

		String data = "2 ";
		TextIHM.scanner = new Scanner(data);
		StaticChoice choice = menu.selectChoice(false);

		assertEquals(StaticChoice.PLACE_REMOVE_UNIT_REMOVE_UNIT, choice);
	}

	/**
	 * Test method for {@link control.text.menus.CMenuStaticChoices#selectChoice(boolean)}.
	 */
	@Test
	public void testSelectChoiceBooleanDisabledChoices() {
		StaticChoice[] disabledChoices = { StaticChoice.PLACE_REMOVE_UNIT_REMOVE_UNIT };
		menu = factory.newMenuStaticChoices(StaticChoicesMenuName.PLACE_REMOVE_UNIT,
				Arrays.asList(disabledChoices), player);

		String data = "2 ";
		TextIHM.scanner = new Scanner(data);
		StaticChoice choice = menu.selectChoice(false);

		assertEquals(StaticChoice.PLACE_REMOVE_UNIT_PLACE_TRANSPORT, choice);
	}

	/**
	 * Test method for {@link control.text.menus.CMenuStaticChoices#selectChoice(boolean)}.
	 */
	@Test
	public void testSelectChoiceBooleanCancel() {
		StaticChoice[] disabledChoices = { StaticChoice.PLACE_REMOVE_UNIT_REMOVE_UNIT };
		menu = factory.newMenuStaticChoices(StaticChoicesMenuName.PLACE_REMOVE_UNIT,
				Arrays.asList(disabledChoices), player);

		String data = "4 ";
		TextIHM.scanner = new Scanner(data);
		StaticChoice choice = menu.selectChoice(true);

		assertEquals(null, choice);
	}

}
