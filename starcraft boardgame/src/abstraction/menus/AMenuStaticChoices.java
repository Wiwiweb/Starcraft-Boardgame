package abstraction.menus;

import abstraction.Player;


public abstract class AMenuStaticChoices extends AMenu<Integer> {

	public enum StaticChoice {
		
	}
	
	public AMenuStaticChoices(MenuName menuName, Player player) {
		super(menuName, player);
	}

	@Override
	public abstract Integer selectChoice();
	
	/**
	 * Returns null if the player chose to cancel
	 * @return The number of the choice selected, or null
	 */
	public abstract Integer selectChoiceWithCancel();


}
