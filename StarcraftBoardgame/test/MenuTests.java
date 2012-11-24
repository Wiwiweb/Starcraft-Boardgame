import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import presentation.text.TextIHM;
import tools.XmlParser;
import abstraction.Game;
import abstraction.Player;
import abstraction.menus.AMenuStaticChoices;
import abstraction.menus.AMenuStaticChoices.StaticChoice;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName;

/**
 * @author William Gautier
 */
public class MenuTests {

	@BeforeClass
	public static void initializeXml() {
		XmlParser.getAll();
		Game.IS_TEST = true;
	}

	@Test
	public void testDisabledStaticMenu() {
		Player player = new Player("Player");
		StaticChoice[] disabledChoices = { StaticChoice.PLACE_REMOVE_UNIT_REMOVE_UNIT };
		AMenuStaticChoices menu = Game.factory.newMenuStaticChoices(StaticChoicesMenuName.PLACE_REMOVE_UNIT,
				Arrays.asList(disabledChoices), player);
		String data = "2 ";

		TextIHM.scanner = new Scanner(data);
		StaticChoice choice = menu.selectChoice();
		assertEquals(StaticChoice.PLACE_REMOVE_UNIT_PLACE_TRANSPORT, choice);
	}

	@Test
	public void testStaticMenu() {
		Player player = new Player("Player");
		AMenuStaticChoices menu = Game.factory.newMenuStaticChoices(StaticChoicesMenuName.PLACE_REMOVE_UNIT, player);
		String data = "2 ";

		TextIHM.scanner = new Scanner(data);
		StaticChoice choice = menu.selectChoice();
		assertEquals(StaticChoice.PLACE_REMOVE_UNIT_REMOVE_UNIT, choice);
	}

}
