package control.text.menus;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import presentation.text.TextIHM;
import testsSetup.GameTests;
import abstraction.Faction;
import abstraction.Player;
import abstraction.menus.MenuChooseFromList;
import abstraction.menus.MenuChooseFromList.ChooseFromListMenuName;

/**
 * @author William Gautier
 */
public class CMenuChooseFromListTest extends GameTests {

	private Player player;
	private MenuChooseFromList<?> menu;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		player = factory.newPlayer("Player");
	}

	/**
	 * Test method for {@link control.text.menus.CMenuChooseFromList#selectChoice(boolean)}.
	 */
	@Test
	public void testSelectChoice() {
		List<Faction> listChoices = new ArrayList<Faction>();
		Faction overmind = factory.getFaction("Overmind");
		Faction queenOfBlades = factory.getFaction("Queen of Blades");
		listChoices.add(overmind);
		listChoices.add(queenOfBlades);

		menu = factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_FACTION, listChoices, player);

		String data = "2 ";
		TextIHM.scanner = new Scanner(data);
		Faction choice = (Faction) menu.selectChoice(false);

		assertEquals(queenOfBlades, choice);
	}

	/**
	 * Test method for {@link control.text.menus.CMenuChooseFromList#selectChoice(boolean)}.
	 */
	@Test
	public void testSelectChoiceCancel() {
		List<Faction> listChoices = new ArrayList<Faction>();
		Faction overmind = factory.getFaction("Overmind");
		Faction queenOfBlades = factory.getFaction("Queen of Blades");
		listChoices.add(overmind);
		listChoices.add(queenOfBlades);

		menu = factory.newMenuChooseFromList(ChooseFromListMenuName.CHOOSE_FACTION, listChoices, player);

		String data = "3 ";
		TextIHM.scanner = new Scanner(data);
		Faction choice = (Faction) menu.selectChoice(true);

		assertEquals(null, choice);
	}

}
