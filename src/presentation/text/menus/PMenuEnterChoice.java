package presentation.text.menus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import presentation.text.TextIHM;
import abstraction.Game;
import abstraction.menus.MenuEnterChoice.EnterChoiceMenuName;
import control.text.menus.CMenuEnterChoice;

/**
 * @author William Gautier
 */
public class PMenuEnterChoice<T> implements IPMenu<T> {

	@SuppressWarnings("serial")
	private static final Map<EnterChoiceMenuName, String> PROMPT_MESSAGES =
			Collections.unmodifiableMap(new HashMap<EnterChoiceMenuName, String>() {

				{
					put(EnterChoiceMenuName.HOW_MANY_WORKERS, "how many workers do you want?");

				}
			}
					);

	private final CMenuEnterChoice<T> control;
	private final Class<T> inputClass;
	private final String promptMessage;

	public PMenuEnterChoice(CMenuEnterChoice<T> control, Class<T> inputClass) {
		this.control = control;
		this.inputClass = inputClass;
		EnterChoiceMenuName menuName = control.getMenuName();

		String getPrompt = PROMPT_MESSAGES.get(menuName);
		if (getPrompt == null) {
			promptMessage = "[No message for " + menuName + "]";
		} else {
			promptMessage = getPrompt;
		}
	}

	@Override
	public T askChoice(boolean cancel) {
		String cancelString = "Cancel";
		String playerName = control.getPlayer().getName();

		if (cancel) {
			System.out.println(playerName + ", " + promptMessage + " Type \"" + cancelString + "\" to cancel.");
		} else {
			System.out.println(playerName + ", " + promptMessage);
		}

		String choice = "";
		T result = null;
		boolean repeat = true;
		while (repeat) {

			repeat = false;
			choice = TextIHM.scanner.nextLine();
			if (cancel && choice.equals(cancelString)) {
				result = null;
			} else if (inputClass == Integer.class) {
				try {
					result = inputClass.cast(Integer.parseInt(choice));
				} catch (NumberFormatException e) {
					System.out.println("Not a number. Try again.");
					repeat = true;
				}
			} else if (inputClass == String.class) {
				result = inputClass.cast(choice);
			}

			if (Game.IS_TEST) {
				System.out.println(choice);
			}
			System.out.println();
		}

		return result;
	}

}
