package abstraction.menus;

import abstraction.Player;
import abstraction.menus.AMenuStaticChoices.StaticChoicesMenuName.StaticChoice;

public abstract class AMenuStaticChoices extends AMenu<StaticChoice> {

	public static enum StaticChoicesMenuName {
		ROTATE_PLANET(StaticChoice.ROTATE_PLANET_CLOCKWISE, StaticChoice.ROTATE_PLANET_COUNTERCLOCKWISE, StaticChoice.ROTATE_PLANET_PLACE),
		PLACE_FIRST_BASE(StaticChoice.PLACE_FIRST_BASE_YES, StaticChoice.PLACE_FIRST_BASE_NO);

		private final StaticChoice[] choices;

		StaticChoicesMenuName(StaticChoice... choices) {
			this.choices = choices;
		}

		public StaticChoice[] getChoices() {
			return choices;
		}

		public static enum StaticChoice {
			ROTATE_PLANET_CLOCKWISE, ROTATE_PLANET_COUNTERCLOCKWISE, ROTATE_PLANET_PLACE,
			PLACE_FIRST_BASE_YES, PLACE_FIRST_BASE_NO
		}

	}

	// SELECT_FACTION(MenuType.CHOOSE_FROM_LIST);
	//
	// private final MenuType type;
	//
	// public static enum MenuType {
	// CHOOSE_FROM_LIST, STATIC_CHOICE
	// }
	//
	// MenuName(MenuType type) {
	// this.type = type;
	// }
	//
	// public MenuType getType() {
	// return type;
	// }

	private final StaticChoicesMenuName menuName;

	public AMenuStaticChoices(StaticChoicesMenuName menuName, Player player) {
		super(player);
		this.menuName = menuName;
	}

	public StaticChoicesMenuName getMenuName() {
		return menuName;
	}

	@Override
	public abstract StaticChoice selectChoice();

	/**
	 * Returns null if the player chose to cancel
	 * 
	 * @return The number of the choice selected, or null
	 */
	public abstract StaticChoice selectChoiceWithCancel();

}
