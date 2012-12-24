package presentation.text.menus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import presentation.text.TextIHM;
import abstraction.Game;
import abstraction.menus.MenuChooseFromList.ChooseFromListMenuName;
import control.text.menus.CMenuChooseFromList;

/**
 * @author William Gautier
 */
public class PMenuChooseFromList<T extends Comparable<? super T>> implements IPMenu<T> {

	@SuppressWarnings("serial")
	private static final Map<ChooseFromListMenuName, String> PROMPT_MESSAGES =
			Collections.unmodifiableMap(new HashMap<ChooseFromListMenuName, String>() {

				{
					put(ChooseFromListMenuName.CHOOSE_FACTION, "select your starting faction:");

					put(ChooseFromListMenuName.CHOOSE_PLANET_TO_PLACE, "select which planet to place:");
					put(ChooseFromListMenuName.CHOOSE_PLANET_SPOT, "select where to place this planet:");
					put(ChooseFromListMenuName.CHOOSE_BASE_AREA, "select where to place this base:");

					put(ChooseFromListMenuName.CHOOSE_ZAXIS_ENTRANCE, "select where to place the first side of the Z-Axis:");
					put(ChooseFromListMenuName.CHOOSE_ZAXIS_EXIT, "select where to place the second side of the Z-Axis:");

					put(ChooseFromListMenuName.CHOOSE_UNIT_TO_PLACE, "select the unit to place:");
					put(ChooseFromListMenuName.CHOOSE_UNIT_PLACEMENT, "select where to place that unit:");
					put(ChooseFromListMenuName.CHOOSE_UNIT_TO_REMOVE, "select the unit to remove:");
					put(ChooseFromListMenuName.CHOOSE_TRANSPORT_PLACEMENT, "select where to place a transport:");
					put(ChooseFromListMenuName.CHOOSE_TRANSPORT_TO_REMOVE, "select which transport to remove:");

					put(ChooseFromListMenuName.CHOOSE_ORDER_TYPE, "select what order to place:");
					put(ChooseFromListMenuName.CHOOSE_PLANET_FOR_ORDER, "select where to place that order:");

					put(ChooseFromListMenuName.CHOOSE_ORDER_TO_EXECUTE, "select which order to execute:");
				}
			}
					);

	private final CMenuChooseFromList<T> control;
	private final String promptMessage;

	public PMenuChooseFromList(CMenuChooseFromList<T> control) {
		this.control = control;
		ChooseFromListMenuName menuName = control.getMenuName();

		String getPrompt = PROMPT_MESSAGES.get(menuName);
		if (getPrompt == null) {
			promptMessage = "[No message for " + menuName + "]";
		} else {
			promptMessage = getPrompt;
		}
	}

	@Override
	public T askChoice(boolean cancel) {
		String playerName = control.getPlayer().getName();
		System.out.println(playerName + ", " + promptMessage);
		int choice = -1;

		int cancelChoiceNb = control.getChoices().size() + 1;
		int maxSize;
		if (cancel) {
			maxSize = cancelChoiceNb;
		} else {
			maxSize = control.getChoices().size();
		}

		while (choice < 1 || choice > maxSize) {

			Iterator<T> it = control.getChoices().iterator();
			int i = 1;
			while (it.hasNext()) {
				T t = it.next();
				System.out.println(i + " : " + t);
				i++;
			}

			if (cancel) {
				System.out.println(cancel + " : Cancel");
			}

			choice = TextIHM.scanner.nextInt();
			if (Game.IS_TEST) {
				System.out.println(choice);
			}
			System.out.println();
		}

		T result;
		if (cancel && choice == cancelChoiceNb) {
			result = null;
		} else {
			result = control.getChoices().get(choice - 1);
		}

		return result;
	}

}
