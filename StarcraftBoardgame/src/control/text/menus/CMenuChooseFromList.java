package control.text.menus;

import java.util.Collections;
import java.util.List;

import presentation.text.menus.IPMenu;
import presentation.text.menus.PMenuChooseFromList;
import abstraction.Player;
import abstraction.menus.MenuChooseFromList;
import abstraction.menus.multimenus.MultiMenu;

/**
 * @author William Gautier
 */
public class CMenuChooseFromList<T extends Comparable<? super T>> extends MenuChooseFromList<T> {

	private final IPMenu<T> presentation;

	public CMenuChooseFromList(ChooseFromListMenuName menuName, List<T> choices, Player player, MultiMenu multimenu) {
		super(menuName, choices, player, multimenu);
		Collections.sort(choices);
		presentation = new PMenuChooseFromList<T>(this);
	}

	public CMenuChooseFromList(ChooseFromListMenuName menuName, List<T> choices, Player player) {
		this(menuName, choices, player, null);
	}

	@Override
	public T selectChoice(boolean cancel) {
		return presentation.askChoice(cancel);
	}

}
