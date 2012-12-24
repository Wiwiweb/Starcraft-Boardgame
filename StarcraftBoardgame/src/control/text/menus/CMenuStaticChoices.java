package control.text.menus;

import java.util.Collection;

import presentation.text.menus.IPMenu;
import presentation.text.menus.PMenuStaticChoices;
import abstraction.Player;
import abstraction.menus.MenuStaticChoices;
import abstraction.menus.multimenus.MultiMenu;

/**
 * @author William Gautier
 */
public class CMenuStaticChoices extends MenuStaticChoices {

	private final IPMenu<StaticChoice> presentation;

	public CMenuStaticChoices(StaticChoicesMenuName menuName, Collection<StaticChoice> disabledChoices, Player player,
			MultiMenu multiMenu) {
		super(menuName, disabledChoices, player, multiMenu);
		presentation = new PMenuStaticChoices(this);
	}

	public CMenuStaticChoices(StaticChoicesMenuName menuName, Player player, MultiMenu multiMenu) {
		this(menuName, null, player, multiMenu);
	}

	public CMenuStaticChoices(StaticChoicesMenuName menuName, Collection<StaticChoice> disabledChoices, Player player) {
		this(menuName, disabledChoices, player, null);
	}

	public CMenuStaticChoices(StaticChoicesMenuName menuName, Player player) {
		this(menuName, null, player, null);
	}

	@Override
	public StaticChoice selectChoice(boolean cancel) {
		return presentation.askChoice(cancel);
	}

}
