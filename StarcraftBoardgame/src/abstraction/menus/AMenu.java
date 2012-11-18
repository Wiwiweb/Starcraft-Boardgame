package abstraction.menus;

import abstraction.Player;

public abstract class AMenu<T> {

	private final Player player;

	public AMenu(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public abstract T selectChoice();

	public abstract T selectChoiceWithCancel();


}
