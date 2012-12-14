package control.text;

import java.util.ArrayList;
import java.util.Collection;

import abstraction.Factory;
import abstraction.Player;
import abstraction.menus.MenuChooseFromList;
import abstraction.menus.MenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.MenuStaticChoices;
import abstraction.menus.MenuStaticChoices.StaticChoice;
import abstraction.menus.MenuStaticChoices.StaticChoicesMenuName;
import control.text.menus.CMenuChooseFromList;
import control.text.menus.CMenuStaticChoices;

/**
 * @author William Gautier
 */
public class CFactory extends Factory {

	@Override
	public MenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName, Player player) {
		return new CMenuStaticChoices(menuName, player);
	}

	@Override
	public MenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName, Collection<StaticChoice> disabledChoices,
			Player player) {
		return new CMenuStaticChoices(menuName, disabledChoices, player);
	}

	@Override
	public <T extends Comparable<? super T>> MenuChooseFromList<T> newMenuChooseFromList(ChooseFromListMenuName menuName,
			Collection<T> listChoices, Player player) {
		return new CMenuChooseFromList<T>(menuName, new ArrayList<T>(listChoices), player);
	}
}
