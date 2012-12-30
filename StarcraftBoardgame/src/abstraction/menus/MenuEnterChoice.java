package abstraction.menus;

import abstraction.Player;
import abstraction.menus.multimenus.MultiMenu;

/**
 * @author William Gautier
 */
public abstract class MenuEnterChoice<T> extends Menu<T> {

	public static enum EnterChoiceMenuName {
		HOW_MANY_WORKERS
	}

	private final EnterChoiceMenuName menuName;
	private final Class<T> inputClass;

	public MenuEnterChoice(EnterChoiceMenuName menuName, Class<T> inputClass, Player player, MultiMenu multimenu) {
		super(player, multimenu);
		this.menuName = menuName;
		this.inputClass = inputClass;
	}

	public MenuEnterChoice(EnterChoiceMenuName menuName, Class<T> inputClass, Player player) {
		this(menuName, inputClass, player, null);
	}

	public EnterChoiceMenuName getMenuName() {
		return menuName;
	}

	public Class<T> getInputClass() {
		return inputClass;
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
