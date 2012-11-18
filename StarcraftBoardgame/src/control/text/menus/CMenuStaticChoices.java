package control.text.menus;

import presentation.text.menus.IPMenu;
import presentation.text.menus.PMenuStaticChoices;
import abstraction.Player;
import abstraction.menus.AMenuStaticChoices;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName.StaticChoice;

public class CMenuStaticChoices extends AMenuStaticChoices {

	private IPMenu<StaticChoice> presentation;

	public CMenuStaticChoices(StaticChoicesMenuName menuName, Player player) {
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
	public StaticChoice selectChoice() {
		return presentation.askChoice();
	}

	@Override
	public StaticChoice selectChoiceWithCancel() {
		return presentation.askChoiceWithCancel();
	}

}
