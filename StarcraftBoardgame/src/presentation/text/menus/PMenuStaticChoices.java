package presentation.text.menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import presentation.text.TextIHM;
import abstraction.Game;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName.StaticChoice;
import control.text.menus.CMenuStaticChoices;

public class PMenuStaticChoices implements IPMenu<StaticChoice> {

	@SuppressWarnings("serial")
	private static final Map<StaticChoicesMenuName, String> PROMPT_MESSAGES =
			Collections.unmodifiableMap(new HashMap<StaticChoicesMenuName, String>() {

				{
					put(StaticChoicesMenuName.ROTATE_PLANET, "rotate this planet?");
					put(StaticChoicesMenuName.PLACE_FIRST_BASE, "choose this planet for your first base?");
				}
			}
					);

	@SuppressWarnings("serial")
	private static final Map<StaticChoice, String> CHOICE_MESSAGES =
			Collections.unmodifiableMap(new HashMap<StaticChoice, String>() {

				{
					put(StaticChoice.ROTATE_PLANET_CLOCKWISE, "Rotate clockwise");
					put(StaticChoice.ROTATE_PLANET_COUNTERCLOCKWISE, "Rotate counterclockwise");
					put(StaticChoice.ROTATE_PLANET_PLACE, "Place planet");
					put(StaticChoice.PLACE_FIRST_BASE_YES, "Yes");
					put(StaticChoice.PLACE_FIRST_BASE_NO, "No");
				}
			}
					);

	private final CMenuStaticChoices control;
	private final String promptMessage;

	public PMenuStaticChoices(CMenuStaticChoices control) {
		this.control = control;
		StaticChoicesMenuName menuName = control.getMenuName();
		promptMessage = PROMPT_MESSAGES.get(menuName);
	}

	@Override
	public StaticChoice askChoice() {
		String playerName = control.getPlayer().getName();
		System.out.println(playerName + ", " + promptMessage);
		int choice = -1;
		
		List<StaticChoice> choices = new ArrayList<>();
		choices.addAll(Arrays.asList(control.getMenuName().getChoices()));
		//TODO remove disabled choices
		
		while (choice < 1 || choice > choices.size()) {

			Iterator<StaticChoice> it = choices.iterator();
			int i = 1;
			while (it.hasNext()) {
				StaticChoice c = it.next();
				String msg = CHOICE_MESSAGES.get(c);
				System.out.println(i + " : " + msg);
				i++;
			}

			choice = TextIHM.scanner.nextInt();
			if(Game.IS_TEST) {
				System.out.println(choice);
			}
			System.out.println();
		}

		return getStaticChoiceFromInt(choice);
	}

	@Override
	public StaticChoice askChoiceWithCancel() {
		String playerName = control.getPlayer().getName();
		System.out.println(playerName + ", " + promptMessage);
		int choice = -1;
		
		List<StaticChoice> choices = new ArrayList<>();
		choices.addAll(Arrays.asList(control.getMenuName().getChoices()));
		//TODO remove disabled choices
		
		int cancel = choices.size() + 1;

		while (choice < 1 || choice > cancel) {

			Iterator<StaticChoice> it = choices.iterator();
			int i = 1;
			while (it.hasNext()) {
				StaticChoice c = it.next();
				String msg = CHOICE_MESSAGES.get(c);
				System.out.println(i + " : " + msg);
				i++;
			}

			System.out.println(cancel + " : Cancel");

			choice = TextIHM.scanner.nextInt();
			if(Game.IS_TEST) {
				System.out.println(choice);
			}
			System.out.println();
		}

		if (choice == cancel) {
			return null;
		} else {
			return getStaticChoiceFromInt(choice);
		}
	}

	private StaticChoice getStaticChoiceFromInt(int choice) {
		StaticChoice[] choices = control.getMenuName().getChoices();
		return choices[choice - 1];
		// TODO allow disabling choices
	}

}
