package abstraction;

import java.util.List;

public abstract class AMenu<T> {
	
	public static enum MenuType {
		SELECT_FACTION
	}

	private final List<T> choices;
	private final MenuType menuType;

	public AMenu(List<T> choices, MenuType menuType) {
		this.choices = choices;
		this.menuType = menuType;
	}

	public List<T> getChoices() {
		return choices;
	}
	
	public MenuType getMenuType() {
		return menuType;
	}

	public abstract T selectChoice();


}
