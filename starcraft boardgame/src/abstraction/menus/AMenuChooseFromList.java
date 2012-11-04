package abstraction.menus;

import java.util.List;

import abstraction.Player;

public abstract class AMenuChooseFromList<T> extends AMenu<T> {

	private final List<T> choices;

	public AMenuChooseFromList(MenuName menuName, List<T> choices, Player player) {
		super(menuName, player);
		this.choices = choices;
	}

	public List<T> getChoices() {
		return choices;
	}

	@Override
	public abstract T selectChoice();

	/**
	 * Returns null if the player chose to cancel
	 * 
	 * @return The object selected from the list, or null
	 */
	public abstract T selectChoiceWithCancel();

}
