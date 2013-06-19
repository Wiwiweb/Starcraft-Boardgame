package abstraction.menus.multimenus;

import abstraction.Factory;
import abstraction.Player;
import abstraction.menus.Menu;
import abstraction.menus.multimenus.states.MultiMenuBuildOrderChoices;
import abstraction.menus.multimenus.states.MultiMenuChooseOrderChoices;

/**
 * State diagram :
 * <img alt="" src="../../../../doc/UML/MultiMenuBuildOrder.jpg" />
 * 
 * @author William Gautier
 */
public class MultiMenuBuildOrder extends MultiMenu {

	private int totalMinerals;
	private int totalGas;

	private final MultiMenuBuildOrderChoices choices = new MultiMenuBuildOrderChoices();

	public MultiMenuBuildOrder(Player player) {
		super(player);
	}

	@Override
	protected Menu<?> getMenu(int i, Factory factory) {
		Menu<?> menu;

		switch (i) {
		case 1:
			break;
		}
		return null;
	}

	@Override
	protected void updateState() {
		// TODO Auto-generated method stub

	}

	@Override
	public MultiMenuChooseOrderChoices doSelection(Factory factory) {
		// TODO Auto-generated method stub
		return null;
	}

}
