package presentation.text.menus;

import java.util.Iterator;
import java.util.Scanner;

import abstraction.AMenu.MenuType;
import control.text.CMenu;


public class PMenuChooseFromList<T> implements IPMenu<T> {
	
	private final String SELECT_FACTION_PROMPT = "Select your starting faction:";
	
	private final CMenu<T> control;
	private final String promptMessage;
	
	public PMenuChooseFromList(CMenu<T> control) {
		this.control = control;
		MenuType menuType = control.getMenuType();
		
		switch (menuType) {
		case SELECT_FACTION:
			promptMessage = SELECT_FACTION_PROMPT;
			break;
		default:
			throw new IllegalArgumentException("Unknown menu type.");
		}
	}
	
	@Override
	public T askChoice() {
		System.out.println(promptMessage);
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

	public String getPromptMessage() {
		return promptMessage;
	}
}
