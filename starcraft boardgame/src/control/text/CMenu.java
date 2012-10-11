package control.text;

import java.util.List;

import presentation.text.menus.IPMenu;
import presentation.text.menus.PMenuChooseFromList;
import abstraction.AMenu;

public class CMenu<T> extends AMenu<T> {

	private IPMenu<T> presentation;

	public CMenu(List<T> choices, MenuType menuType) {
		super(choices, menuType);
		
		switch (menuType) {
		case SELECT_FACTION:
			presentation = new PMenuChooseFromList<T>(this);
			break;
		default:
			throw new IllegalArgumentException("Unknown menu type.");
		}
	}

	@Override
	public T selectChoice() {
		return presentation.askChoice();
	}

}
