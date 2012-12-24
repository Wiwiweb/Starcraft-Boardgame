package abstraction.menus;

import abstraction.Player;
import abstraction.menus.multimenus.MultiMenu;

/**
 * @author William Gautier
 */
public abstract class Menu<T> {

	private final Player player;
	private final MultiMenu multimenu;

	public Menu(Player player, MultiMenu multimenu) {
		this.player = player;
		this.multimenu = multimenu;
	}

	public Player getPlayer() {
		return player;
	}

	public MultiMenu getMultimenu() {
		return multimenu;
	}

	public abstract T selectChoice(boolean cancel);

}
