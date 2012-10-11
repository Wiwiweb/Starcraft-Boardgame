package abstraction;

import java.util.List;

import abstraction.AMenu.MenuType;



public abstract class AFactory {
	
	public abstract <T> AMenu<T> newMenu(List<T> choices, MenuType menuType);
}
