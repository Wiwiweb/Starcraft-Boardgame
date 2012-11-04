package control.text.menus;

import java.util.List;

import presentation.text.menus.IPMenu;
import presentation.text.menus.PMenuChooseFromList;
import abstraction.Player;
import abstraction.menus.AMenuChooseFromList;

public class CMenuChooseFromList<T> extends AMenuChooseFromList<T> {

	private IPMenu<T> presentation;

	public CMenuChooseFromList(MenuName menuName, List<T> choices, Player player) {
		super(menuName, choices, player);

		switch (menuName) {
		case SELECT_FACTION:
		case SELECT_PLANET_TO_PLACE:
		case SELECT_PLANET_SPOT:
		case SELECT_BASE_AREA:
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

	@Override
	public T selectChoiceWithCancel() {
		return presentation.askChoiceWithCancel();
	}

}
