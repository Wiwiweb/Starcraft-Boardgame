package abstraction.menus;

import abstraction.Player;

/**
 * @author William Gautier
 */
public abstract class Menu<T> {

	private final Player player;

	public Menu(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public abstract T selectChoice();

	public abstract T selectChoiceWithCancel();

}
