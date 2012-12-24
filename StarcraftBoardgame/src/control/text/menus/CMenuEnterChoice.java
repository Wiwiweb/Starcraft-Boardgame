package control.text.menus;

import presentation.text.menus.IPMenu;
import presentation.text.menus.PMenuEnterChoice;
import abstraction.Player;
import abstraction.menus.MenuEnterChoice;
import abstraction.menus.multimenus.MultiMenu;

/**
 * @author William Gautier
 */
public class CMenuEnterChoice<T> extends MenuEnterChoice<T> {

	private final IPMenu<T> presentation;

	public CMenuEnterChoice(EnterChoiceMenuName menuName, Player player, Class<T> inputClass, MultiMenu multimenu) {
		super(menuName, player, inputClass, multimenu);
		presentation = new PMenuEnterChoice<T>(this, inputClass);
	}

	public CMenuEnterChoice(EnterChoiceMenuName menuName, Class<T> inputClass, Player player) {
		this(menuName, player, inputClass, null);
	}

	@Override
	public T selectChoice(boolean cancel) {
		return presentation.askChoice(cancel);
	}

}
