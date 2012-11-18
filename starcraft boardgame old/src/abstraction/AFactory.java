package abstraction;

import java.util.List;

import abstraction.menus.AMenu.MenuName;
import abstraction.menus.AMenuChooseFromList;
import abstraction.menus.AMenuStaticChoices;

public abstract class AFactory {

	public abstract AMenuStaticChoices newMenuStaticChoices(MenuName menuName, Player player);

	public abstract <T extends Comparable<? super T>> AMenuChooseFromList<T> newMenuChooseFromList(MenuName menuName,
			List<T> listChoices, Player player);

}
