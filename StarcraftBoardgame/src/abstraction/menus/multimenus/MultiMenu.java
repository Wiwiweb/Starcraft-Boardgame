package abstraction.menus.multimenus;

import abstraction.Player;
import abstraction.menus.Menu;
import abstraction.menus.multimenus.states.MultiMenuChoices;

/**
 * @author William Gautier
 */
public abstract class MultiMenu {

	protected final Player player;

	/**
	 * 0 is the beginning state</br>
	 * 1+ is "after menu 1+" -1 is the end state
	 */
	protected int state;

	public MultiMenu(Player player) {
		this.player = player;
		this.state = 0;
	}

	/**
	 * Get the menu number i. Menu numbers start at 1, not 0. Does necessary operations to provide the menu with the
	 * list of elements from which to choose.
	 * 
	 * @return
	 */
	abstract protected Menu<?> getMenu(int i);

	/**
	 * Updates the state based on current state and selection of menus. Does internal operations after a selection of a
	 * menu (such as modifying a previous selection or providing a default selection to a skipped menu).
	 */
	abstract protected void updateState();

	/**
	 * Main loop.
	 */
	abstract public MultiMenuChoices doSelection();

}
