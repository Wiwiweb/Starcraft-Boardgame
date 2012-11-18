package abstraction.menus;

import abstraction.Player;

public abstract class AMenu<T> {

	public static enum MenuName {

		// Every "choose from list" type menu starts with "SELECT", the rest is "static choice" menu.
		SELECT_FACTION, SELECT_PLANET_TO_PLACE, ROTATE_PLANET, SELECT_PLANET_SPOT, PLACE_FIRST_BASE, SELECT_BASE_AREA, SELECT_ZAXIS_ENTRANCE, SELECT_ZAXIS_EXIT
	}

	private final MenuName menuName;
	private final Player player;

	public AMenu(MenuName menuName, Player player) {
		this.menuName = menuName;
		this.player = player;
	}

	public MenuName getMenuType() {
		return menuName;
	}

	public Player getPlayer() {
		return player;
	}

	public abstract T selectChoice();

	public abstract T selectChoiceWithCancel();

}
