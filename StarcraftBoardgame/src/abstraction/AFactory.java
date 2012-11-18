package abstraction;

import java.util.List;

import abstraction.menus.AMenuChooseFromList;
import abstraction.menus.AMenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.AMenuStaticChoices;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName;

public abstract class AFactory {

	public abstract AMenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName, Player player);

	public abstract <T extends Comparable<? super T>> AMenuChooseFromList<T> newMenuChooseFromList(ChooseFromListMenuName menuName,
			List<T> listChoices, Player player);

}
