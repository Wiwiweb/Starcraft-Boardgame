package control.text.menus;

import java.util.Collections;
import java.util.List;

import presentation.text.menus.IPMenu;
import presentation.text.menus.PMenuChooseFromList;
import abstraction.Player;
import abstraction.menus.AMenuChooseFromList;

/**
 * @author William Gautier
 */
public class CMenuChooseFromList<T extends Comparable<? super T>> extends AMenuChooseFromList<T> {

	private final IPMenu<T> presentation;

	public CMenuChooseFromList(ChooseFromListMenuName menuName, List<T> choices, Player player) {

		super(menuName, choices, player);
		Collections.sort(choices);

		switch (menuName) {
		case CHOOSE_FACTION:
		case CHOOSE_PLANET_TO_PLACE:
		case CHOOSE_PLANET_SPOT:
		case CHOOSE_BASE_AREA:
		case CHOOSE_ZAXIS_ENTRANCE:
		case CHOOSE_ZAXIS_EXIT:
			presentation = new PMenuChooseFromList<T>(this);
			break;
		default:
			throw new IllegalArgumentException("Unknown menu type.");
		}
	}

	@Override
	public T selectChoice() {
		return presentation.askChoice();
	}

	@Override
	public T selectChoiceWithCancel() {
		return presentation.askChoiceWithCancel();
	}

}
