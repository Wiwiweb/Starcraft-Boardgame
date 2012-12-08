package abstraction;

import java.util.Collection;

import abstraction.menus.AMenuChooseFromList;
import abstraction.menus.AMenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.AMenuStaticChoices;
import abstraction.menus.AMenuStaticChoices.StaticChoice;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName;

/**
 * @author William Gautier
 */
public abstract class AFactory {

	public abstract AMenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName, Player player);

	public abstract AMenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName,
			Collection<StaticChoice> disabledChoices, Player player);

	public abstract <T extends Comparable<? super T>> AMenuChooseFromList<T> newMenuChooseFromList(
			ChooseFromListMenuName menuName, Collection<T> listChoices, Player player);

}
