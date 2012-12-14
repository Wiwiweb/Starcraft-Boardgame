package control.text.menus;

import java.util.Collection;

import presentation.text.menus.IPMenu;
import presentation.text.menus.PMenuStaticChoices;
import abstraction.Player;
import abstraction.menus.MenuStaticChoices;

/**
 * @author William Gautier
 */
public class CMenuStaticChoices extends MenuStaticChoices {

	private final IPMenu<StaticChoice> presentation;

	public CMenuStaticChoices(StaticChoicesMenuName menuName, Player player) {
		super(menuName, player);
		presentation = new PMenuStaticChoices(this);
	}

	public CMenuStaticChoices(StaticChoicesMenuName menuName, Collection<StaticChoice> disabledChoices, Player player) {
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
