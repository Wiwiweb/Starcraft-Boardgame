package control.text;

import java.util.List;

import abstraction.AFactory;
import abstraction.AMenu.MenuType;


public class CFactory extends AFactory {

	@Override
	public <T> CMenu<T> newMenu(List<T> choices, MenuType menuType) {
		return new CMenu<T>(choices, menuType);
	}

}
