package control.text;

import java.util.List;

import abstraction.AFactory;
import abstraction.Player;
import abstraction.menus.AMenuChooseFromList;
import abstraction.menus.AMenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.AMenuStaticChoices;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName.StaticChoice;
import control.text.menus.CMenuChooseFromList;
import control.text.menus.CMenuStaticChoices;

public class CFactory extends AFactory {

	@Override
	public AMenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName, Player player) {
		return new CMenuStaticChoices(menuName, player);
	}
	
	@Override
	public AMenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName, StaticChoice[] disabledChoices, Player player) {
		return new CMenuStaticChoices(menuName, disabledChoices, player);
	}

	@Override
	public <T extends Comparable<? super T>> AMenuChooseFromList<T> newMenuChooseFromList(ChooseFromListMenuName menuName, List<T> listChoices, Player player) {
		return new CMenuChooseFromList<T>(menuName, listChoices, player);
	}


	// @Override
	// public AMenuStaticChoices newMenuStaticChoices(MenuName menuName, Player player) {
	// if (menuName.getType() == MenuType.STATIC_CHOICE) {
	// return new CMenuStaticChoices(menuName, player);
	// } else {
	// throw new IllegalArgumentException("Wrong type of menu.");
	// }
	// }
	//
	// @Override
	// public <T> AMenuChooseFromList<T> newMenuChooseFromList(MenuName menuName, Player player, List<T> listChoices) {
	// if (menuName.getType() == MenuType.CHOOSE_FROM_LIST) {
	// return new CMenuChooseFromList<T>(menuName, player, listChoices);
	// } else {
	// throw new IllegalArgumentException("Wrong type of menu.");
	// }
	// }

}
