package control.text;

import java.util.List;

import abstraction.AFactory;
import abstraction.Player;
import abstraction.menus.AMenu.MenuName;
import abstraction.menus.AMenuChooseFromList;
import abstraction.menus.AMenuStaticChoices;
import control.text.menus.CMenuChooseFromList;
import control.text.menus.CMenuStaticChoices;

public class CFactory extends AFactory {

	@Override
	public AMenuStaticChoices newMenuStaticChoices(MenuName menuName, Player player) {
		return new CMenuStaticChoices(menuName, player);
	}

	@Override
	public <T extends Comparable<? super T>> AMenuChooseFromList<T> newMenuChooseFromList(MenuName menuName, List<T> listChoices,
			Player player) {
		return new CMenuChooseFromList<T>(menuName, listChoices, player);
	}
}
