package presentation.text.menus;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import presentation.text.TextIHM;
import abstraction.menus.AMenu.MenuName;
import control.text.menus.CMenuStaticChoices;

public class PMenuStaticChoices implements IPMenu<Integer> {

	private final String ROTATE_PLANET_PROMPT = "rotate this planet?";
	private final String PLACE_FIRST_BASE_PROMPT = "choose this planet for your first base?";

	private final List<String> ROTATE_PLANET_CHOICES = Arrays.asList("Rotate clockwise", "Rotate counterclockwise",
			"Place planet");
	private final List<String> PLACE_FIRST_BASE_CHOICES = Arrays.asList("Yes", "No");

	private final CMenuStaticChoices control;
	private final String promptMessage;
	private final List<String> choices;

	public PMenuStaticChoices(CMenuStaticChoices control) {
		this.control = control;
		MenuName menuType = control.getMenuType();

		switch (menuType) {
		case ROTATE_PLANET:
			promptMessage = ROTATE_PLANET_PROMPT;
			choices = ROTATE_PLANET_CHOICES;
			break;
		case PLACE_FIRST_BASE:
			promptMessage = PLACE_FIRST_BASE_PROMPT;
			choices = PLACE_FIRST_BASE_CHOICES;
			break;
		default:
			throw new IllegalArgumentException("Unknown menu type.");
		}
	}

	@Override
	public Integer askChoice() {
		String playerName = control.getPlayer().getName();
		System.out.println(playerName + ", " + promptMessage);
		int choice = -1;
		while (choice < 1 || choice > choices.size()) {

			Iterator<String> it = choices.iterator();
			int i = 1;
			while (it.hasNext()) {
				String t = it.next();
				System.out.println(i + " : " + t);
				i++;
			}

			choice = TextIHM.scanner.nextInt();
			System.out.println();
		}

		return choice;
	}

	@Override
	public Integer askChoiceWithCancel() {
		String playerName = control.getPlayer().getName();
		System.out.println(playerName + ", " + promptMessage);
		int choice = -1;
		int cancel = choices.size() + 1;
		while (choice < 1 || choice > cancel) {

			Iterator<String> it = choices.iterator();
			int i = 1;
			while (it.hasNext()) {
				String t = it.next();
				System.out.println(i + " : " + t);
				i++;
			}

			System.out.println(cancel + " : Cancel");

			choice = TextIHM.scanner.nextInt();
			System.out.println();
		}

		Integer result;
		if (choice == cancel) {
			result = null;
		} else {
			result = choice;
		}

		return result;
	}

}
