package presentation.text.menus;

import java.util.Iterator;
import java.util.Scanner;

import abstraction.menus.AMenu.MenuName;
import control.text.menus.CMenuChooseFromList;

public class PMenuChooseFromList<T> implements IPMenu<T> {

	private final String SELECT_FACTION_PROMPT = "select your starting faction:";
	private final String SELECT_PLANET_PROMPT = "select which planet to place:";
	private final String SELECT_PLANET_SPOT_PROMPT = "select where to place this planet:";
	private final String SELECT_BASE_AREA_PROMPT = "select where to place this base:";

	private final CMenuChooseFromList<T> control;
	private final String promptMessage;

	public PMenuChooseFromList(CMenuChooseFromList<T> control) {
		this.control = control;
		MenuName menuType = control.getMenuType();

		switch (menuType) {
		case SELECT_FACTION:
			promptMessage = SELECT_FACTION_PROMPT;
			break;
		case SELECT_PLANET_TO_PLACE:
			promptMessage = SELECT_PLANET_PROMPT;
			break;
		case SELECT_PLANET_SPOT:
			promptMessage = SELECT_PLANET_SPOT_PROMPT;
			break;
		case SELECT_BASE_AREA:
			promptMessage = SELECT_BASE_AREA_PROMPT;
			break;
		default:
			throw new IllegalArgumentException("Unknown menu type.");
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

			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
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

			Scanner sc = new Scanner(System.in);
			choice = sc.nextInt();
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
