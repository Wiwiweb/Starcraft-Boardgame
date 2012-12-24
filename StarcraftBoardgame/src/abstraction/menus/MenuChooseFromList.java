package abstraction.menus;

import java.util.List;

import abstraction.Player;
import abstraction.menus.multimenus.MultiMenu;

/**
 * @author William Gautier
 */
public abstract class MenuChooseFromList<T> extends Menu<T> {

	public static enum ChooseFromListMenuName {
		CHOOSE_FACTION, CHOOSE_PLANET_TO_PLACE, CHOOSE_PLANET_SPOT, CHOOSE_BASE_AREA,
		CHOOSE_ZAXIS_ENTRANCE, CHOOSE_ZAXIS_EXIT,
		CHOOSE_UNIT_TO_PLACE, CHOOSE_UNIT_PLACEMENT, CHOOSE_UNIT_TO_REMOVE, CHOOSE_TRANSPORT_PLACEMENT, CHOOSE_TRANSPORT_TO_REMOVE,
		CHOOSE_ORDER_TYPE, CHOOSE_PLANET_FOR_ORDER,
		CHOOSE_ORDER_TO_EXECUTE,

		CHOOSE_BUILD_WHAT_UNIT, CHOOSE_BUILD_UNIT_AREA, CHOOSE_BUILD_ROUTE, CHOOSE_BUILD_BUILDING, CHOOSE_BUILD_MODULE,
		CHOOSE_BUILD_BASE_AREA, CHOOSE_MINERAL_RESOURCE, CHOOSE_GAS_RESOURCE
	}

	private final ChooseFromListMenuName menuName;
	private final List<T> choices;

	public MenuChooseFromList(ChooseFromListMenuName menuName, List<T> choices, Player player, MultiMenu multimenu) {
		super(player, multimenu);
		this.menuName = menuName;
		this.choices = choices;
	}

	public MenuChooseFromList(ChooseFromListMenuName menuName, List<T> choices, Player player) {
		this(menuName, choices, player, null);
	}

	public List<T> getChoices() {
		return choices;
	}

	public ChooseFromListMenuName getMenuName() {
		return menuName;
	}

	/**
	 * Returns null if the player chose to cancel
	 * 
	 * @param cancel - If this menu has a "cancel" choice
	 * @return The object selected from the list, or null
	 */
	@Override
	public abstract T selectChoice(boolean cancel);

}
