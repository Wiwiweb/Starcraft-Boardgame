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

	public CMenuEnterChoice(EnterChoiceMenuName menuName, Class<T> inputClass, Player player, MultiMenu multimenu) {
		super(menuName, inputClass, player, multimenu);
		presentation = new PMenuEnterChoice<T>(this, inputClass);
	}

	public CMenuEnterChoice(EnterChoiceMenuName menuName, Class<T> inputClass, Player player) {
		this(menuName, inputClass, player, null);
	}

	@Override
	public T selectChoice(boolean cancel) {
		return presentation.askChoice(cancel);
	}

}
