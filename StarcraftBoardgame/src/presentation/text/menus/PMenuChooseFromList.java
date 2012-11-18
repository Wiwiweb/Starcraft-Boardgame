package presentation.text.menus;

import java.util.Iterator;

import presentation.text.TextIHM;
import abstraction.Game;
import abstraction.menus.AMenuChooseFromList.ChooseFromListMenuName;
import control.text.menus.CMenuChooseFromList;

public class PMenuChooseFromList<T extends Comparable<? super T>> implements IPMenu<T> {

	private final String SELECT_FACTION_PROMPT = "select your starting faction:";
	private final String SELECT_PLANET_PROMPT = "select which planet to place:";
	private final String SELECT_PLANET_SPOT_PROMPT = "select where to place this planet:";
	private final String SELECT_BASE_AREA_PROMPT = "select where to place this base:";
	private final String SELECT_ZAXIS_ENTRANCE_PROMPT = "select where to place the first side of the Z-Axis:";
	private final String SELECT_ZAXIS_EXIT = "select where to place the second side of the Z-Axis:";

	private final CMenuChooseFromList<T> control;
	private final String promptMessage;

	public PMenuChooseFromList(CMenuChooseFromList<T> control) {
		this.control = control;
		ChooseFromListMenuName menuName = control.getMenuName();

		switch (menuName) {
		case CHOOSE_FACTION:
			promptMessage = SELECT_FACTION_PROMPT;
			break;
		case CHOOSE_PLANET_TO_PLACE:
			promptMessage = SELECT_PLANET_PROMPT;
			break;
		case CHOOSE_PLANET_SPOT:
			promptMessage = SELECT_PLANET_SPOT_PROMPT;
			break;
		case CHOOSE_BASE_AREA:
			promptMessage = SELECT_BASE_AREA_PROMPT;
			break;
		case CHOOSE_ZAXIS_ENTRANCE:
			promptMessage = SELECT_ZAXIS_ENTRANCE_PROMPT;
			break;
		case CHOOSE_ZAXIS_EXIT:
			promptMessage = SELECT_ZAXIS_EXIT;
			break;
		default:
			throw new IllegalArgumentException("Unknown menu name.");
		}
	}

	@Override
	public T askChoice() {
		String playerName = control.getPlayer().getName();
		System.out.println(playerName + ", " + promptMessage);
		int choice = -1;
		while (choice < 1 || choice > control.getChoices().size()) {

			Iterator<T> it = control.getChoices().iterator();
			int i = 1;
			while (it.hasNext()) {
				T t = it.next();
				System.out.println(i + " : " + t);
				i++;
			}

			choice = TextIHM.scanner.nextInt();
			if(Game.IS_TEST) {
				System.out.println(choice);
			}
			System.out.println();
		}

		return control.getChoices().get(choice - 1);
	}

	@Override
	public T askChoiceWithCancel() {
		String playerName = control.getPlayer().getName();
		System.out.println(playerName + ", " + promptMessage);
		int choice = -1;
		int cancel = control.getChoices().size() + 1;
		while (choice < 1 || choice > cancel) {

			Iterator<T> it = control.getChoices().iterator();
			int i = 1;
			while (it.hasNext()) {
				T t = it.next();
				System.out.println(i + " : " + t);
				i++;
			}

			System.out.println(cancel + " : Cancel");

			choice = TextIHM.scanner.nextInt();
			if(Game.IS_TEST) {
				System.out.println(choice);
			}
			System.out.println();
		}

		T result;
		if (choice == cancel) {
			result = null;
		} else {
			result = control.getChoices().get(choice - 1);
		}

		return result;
	}
}
