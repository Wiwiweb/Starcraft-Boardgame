package control.text.menus;

import presentation.text.menus.IPMenu;
import presentation.text.menus.PMenuStaticChoices;
import abstraction.Player;
import abstraction.menus.AMenuStaticChoices;

public class CMenuStaticChoices extends AMenuStaticChoices {

	private IPMenu<Integer> presentation;

	public CMenuStaticChoices(MenuName menuName, Player player) {
		super(menuName, player);

		switch (menuName) {
		case ROTATE_PLANET:
		case PLACE_FIRST_BASE:
			presentation = new PMenuStaticChoices(this);
			break;
		default:
			throw new IllegalArgumentException("Unknown menu type.");
		}
	}

	@Override
	public Integer selectChoice() {
		return presentation.askChoice();
	}

	@Override
	public Integer selectChoiceWithCancel() {
		return presentation.askChoiceWithCancel();
	}

}
