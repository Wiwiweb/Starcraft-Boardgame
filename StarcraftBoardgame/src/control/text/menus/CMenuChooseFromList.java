package control.text.menus;

import java.util.Collections;
import java.util.List;

import presentation.text.menus.IPMenu;
import presentation.text.menus.PMenuChooseFromList;
import abstraction.Player;
import abstraction.menus.MenuChooseFromList;

/**
 * @author William Gautier
 */
public class CMenuChooseFromList<T extends Comparable<? super T>> extends MenuChooseFromList<T> {

	private final IPMenu<T> presentation;

	public CMenuChooseFromList(ChooseFromListMenuName menuName, List<T> choices, Player player) {
		super(menuName, choices, player);
		Collections.sort(choices);
		presentation = new PMenuChooseFromList<T>(this);
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
