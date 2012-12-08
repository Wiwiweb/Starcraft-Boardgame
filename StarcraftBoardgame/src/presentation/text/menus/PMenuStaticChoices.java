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
import abstraction.menus.AMenuStaticChoices.StaticChoice;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName;
import control.text.menus.CMenuStaticChoices;

/**
 * @author William Gautier
 */
public class PMenuStaticChoices implements IPMenu<StaticChoice> {

	@SuppressWarnings("serial")
	private static final Map<StaticChoicesMenuName, String> PROMPT_MESSAGES =
			Collections.unmodifiableMap(new HashMap<StaticChoicesMenuName, String>() {

				{
					put(StaticChoicesMenuName.ROTATE_PLANET, "rotate this planet?");
					put(StaticChoicesMenuName.PLACE_FIRST_BASE, "choose this planet for your first base?");
					put(StaticChoicesMenuName.PLACE_REMOVE_UNIT, "what to do next?");
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
					put(StaticChoice.PLACE_REMOVE_UNIT_PLACE_UNIT, "Place unit");
					put(StaticChoice.PLACE_REMOVE_UNIT_REMOVE_UNIT, "Remove unit");
					put(StaticChoice.PLACE_REMOVE_UNIT_PLACE_TRANSPORT, "Place transport");
					put(StaticChoice.PLACE_REMOVE_UNIT_REMOVE_TRANSPORT, "Remove transport");
				}
			}
					);

	private final CMenuStaticChoices control;
	private final String promptMessage;

	public PMenuStaticChoices(CMenuStaticChoices control) {
		this.control = control;
		StaticChoicesMenuName menuName = control.getMenuName();
		String getPrompt = PROMPT_MESSAGES.get(menuName);
		if (getPrompt == null) {
			promptMessage = "[No message for " + menuName + "]";
		} else {
			promptMessage = getPrompt;
		}
	}

	@Override
	public StaticChoice askChoice() {
		String playerName = control.getPlayer().getName();
		System.out.println(playerName + ", " + promptMessage);
		int choice = -1;

		List<StaticChoice> choices = new ArrayList<>();
		choices.addAll(Arrays.asList(control.getMenuName().getChoices()));
		choices.removeAll(Arrays.asList(control.getDisabledChoices()));

		while (choice < 1 || choice > choices.size()) {

			Iterator<StaticChoice> it = choices.iterator();
			int i = 1;
			while (it.hasNext()) {
				StaticChoice c = it.next();
				String msg = CHOICE_MESSAGES.get(c);
				if(msg == null) {
					msg = "[No message for " + c + "]";
				}
				System.out.println(i + " : " + msg);
				i++;
			}

			choice = TextIHM.scanner.nextInt();
			if (Game.IS_TEST) {
				System.out.println(choice);
			}
			System.out.println();
		}

		return choices.get(choice - 1);
	}

	@Override
	public StaticChoice askChoiceWithCancel() {
		String playerName = control.getPlayer().getName();
		System.out.println(playerName + ", " + promptMessage);
		int choice = -1;

		List<StaticChoice> choices = new ArrayList<>();
		choices.addAll(Arrays.asList(control.getMenuName().getChoices()));
		choices.removeAll(Arrays.asList(control.getDisabledChoices()));

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
			if (Game.IS_TEST) {
				System.out.println(choice);
			}
			System.out.println();
		}

		if (choice == cancel) {
			return null;
		} else {
			return choices.get(choice - 1);
		}
	}
}
