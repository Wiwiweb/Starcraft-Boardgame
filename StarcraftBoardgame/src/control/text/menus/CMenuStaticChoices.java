package control.text.menus;

import presentation.text.menus.IPMenu;
import presentation.text.menus.PMenuStaticChoices;
import abstraction.Player;
import abstraction.menus.AMenuStaticChoices;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName.StaticChoice;

public class CMenuStaticChoices extends AMenuStaticChoices {

	private final IPMenu<StaticChoice> presentation;

	public CMenuStaticChoices(StaticChoicesMenuName menuName, Player player) {
		super(menuName, player);
		presentation = new PMenuStaticChoices(this);
	}
	
	public CMenuStaticChoices(StaticChoicesMenuName menuName, StaticChoice[] disabledChoices, Player player) {
		super(menuName, disabledChoices, player);
		presentation = new PMenuStaticChoices(this);
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
