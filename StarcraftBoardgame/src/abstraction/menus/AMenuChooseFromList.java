package abstraction.menus;

import java.util.List;

import abstraction.Player;

/**
 * @author William Gautier
 */
public abstract class AMenuChooseFromList<T> extends AMenu<T> {

	public static enum ChooseFromListMenuName {
		CHOOSE_FACTION, CHOOSE_PLANET_TO_PLACE, CHOOSE_PLANET_SPOT, CHOOSE_BASE_AREA,
		CHOOSE_ZAXIS_ENTRANCE, CHOOSE_ZAXIS_EXIT,
		CHOOSE_UNIT_TO_PLACE, CHOOSE_UNIT_PLACEMENT, CHOOSE_UNIT_TO_REMOVE, CHOOSE_TRANSPORT_PLACEMENT, CHOOSE_TRANSPORT_TO_REMOVE,
		CHOOSE_ORDER_TYPE, CHOOSE_PLANET_FOR_ORDER
	}

	private final ChooseFromListMenuName menuName;
	private final List<T> choices;

	public AMenuChooseFromList(ChooseFromListMenuName menuName, List<T> choices, Player player) {
		super(player);
		this.menuName = menuName;
		this.choices = choices;
	}

	public List<T> getChoices() {
		return choices;
	}

	public ChooseFromListMenuName getMenuName() {
		return menuName;
	}

	@Override
	public abstract T selectChoice();

	/**
	 * Returns null if the player chose to cancel
	 * 
	 * @return The object selected from the list, or null
	 */
	@Override
	public abstract T selectChoiceWithCancel();

}
