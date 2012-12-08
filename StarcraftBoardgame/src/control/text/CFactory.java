package control.text;

import java.util.ArrayList;
import java.util.Collection;

import abstraction.AFactory;
import abstraction.Player;
import abstraction.menus.AMenuChooseFromList;
import abstraction.menus.AMenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.AMenuStaticChoices;
import abstraction.menus.AMenuStaticChoices.StaticChoice;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName;
import control.text.menus.CMenuChooseFromList;
import control.text.menus.CMenuStaticChoices;

/**
 * @author William Gautier
 */
public class CFactory extends AFactory {

	@Override
	public AMenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName, Player player) {
		return new CMenuStaticChoices(menuName, player);
	}

	@Override
	public AMenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName, Collection<StaticChoice> disabledChoices,
			Player player) {
		return new CMenuStaticChoices(menuName, disabledChoices, player);
	}

	@Override
	public <T extends Comparable<? super T>> AMenuChooseFromList<T> newMenuChooseFromList(ChooseFromListMenuName menuName,
			Collection<T> listChoices, Player player) {
		return new CMenuChooseFromList<T>(menuName, new ArrayList<T>(listChoices), player);
	}
}
