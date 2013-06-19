package abstraction.menus;

import static abstraction.menus.MenuStaticChoices.StaticChoice.*;

import java.util.Collection;

import abstraction.Player;
import abstraction.menus.MenuStaticChoices.StaticChoice;
import abstraction.menus.multimenus.MultiMenu;

/**
 * @author William Gautier
 */
public abstract class MenuStaticChoices extends Menu<StaticChoice> {

	public static enum StaticChoicesMenuName {

		ROTATE_PLANET(ROTATE_PLANET_CLOCKWISE, ROTATE_PLANET_COUNTERCLOCKWISE, ROTATE_PLANET_PLACE),
		PLACE_FIRST_BASE(PLACE_FIRST_BASE_YES, PLACE_FIRST_BASE_NO),

		PLACE_REMOVE_UNIT(PLACE_REMOVE_UNIT_PLACE_UNIT, PLACE_REMOVE_UNIT_REMOVE_UNIT, PLACE_REMOVE_UNIT_PLACE_TRANSPORT,
				PLACE_REMOVE_UNIT_REMOVE_TRANSPORT),

		EXECUTE_OR_CARD(EXECUTE_OR_CARD_EXECUTE, EXECUTE_OR_CARD_CARD),

		BUILD_UNITS(BUILD_UNITS_YES, BUILD_UNITS_NO),
		BUILD_WORKER_TRANSPORT_OR_UNIT(BUILD_WORKER_TRANSPORT_OR_UNIT_WORKER, BUILD_WORKER_TRANSPORT_OR_UNIT_TRANSPORT,
				BUILD_WORKER_TRANSPORT_OR_UNIT_UNIT),
		BUILD_BASE_UPGRADE(BUILD_BASE_UPGRADE_YES, BUILD_BASE_UPGRADE_NO),
		BUILD_BUILDING_OR_MODULE(BUILD_BUILDING_OR_MODULE_BUILDING, BUILD_BUILDING_OR_MODULE_MODULE),
		BUILD_BUILDING_AFTER_MODULE(BUILD_BUILDING_AFTER_MODULE_YES, BUILD_BUILDING_AFTER_MODULE_NO),
		BUILD_MODULE_AFTER_BUILDING(BUILD_MODULE_AFTER_BUILDING_YES, BUILD_MODULE_AFTER_BUILDING_NO),
		BUILD_BASE(BUILD_BASE_YES, BUILD_BASE_NO),
		SPECIAL_DISCOUNT(SPECIAL_DISCOUNT_MINERALS, SPECIAL_DISCOUNT_GAS);

		private final StaticChoice[] choices;

		StaticChoicesMenuName(StaticChoice... choices) {
			this.choices = choices;
		}

		public StaticChoice[] getChoices() {
			return choices;
		}

	}

	public static enum StaticChoice {
		ROTATE_PLANET_CLOCKWISE, ROTATE_PLANET_COUNTERCLOCKWISE, ROTATE_PLANET_PLACE,
		PLACE_FIRST_BASE_YES, PLACE_FIRST_BASE_NO,
		PLACE_REMOVE_UNIT_PLACE_UNIT, PLACE_REMOVE_UNIT_REMOVE_UNIT, PLACE_REMOVE_UNIT_PLACE_TRANSPORT, PLACE_REMOVE_UNIT_REMOVE_TRANSPORT,
		EXECUTE_OR_CARD_EXECUTE, EXECUTE_OR_CARD_CARD,
		BUILD_UNITS_YES, BUILD_UNITS_NO,
		BUILD_WORKER_TRANSPORT_OR_UNIT_WORKER, BUILD_WORKER_TRANSPORT_OR_UNIT_TRANSPORT, BUILD_WORKER_TRANSPORT_OR_UNIT_UNIT,
		BUILD_BASE_UPGRADE_YES, BUILD_BASE_UPGRADE_NO,
		BUILD_BUILDING_OR_MODULE_BUILDING, BUILD_BUILDING_OR_MODULE_MODULE,
		BUILD_BUILDING_AFTER_MODULE_YES, BUILD_BUILDING_AFTER_MODULE_NO,
		BUILD_MODULE_AFTER_BUILDING_YES, BUILD_MODULE_AFTER_BUILDING_NO,
		BUILD_BASE_YES, BUILD_BASE_NO,
		SPECIAL_DISCOUNT_MINERALS, SPECIAL_DISCOUNT_GAS
	}

	private final StaticChoicesMenuName menuName;
	private final StaticChoice[] disabledChoices;

	public MenuStaticChoices(StaticChoicesMenuName menuName, Collection<StaticChoice> disabledChoices, Player player,
			MultiMenu multimenu) {
		super(player, multimenu);
		this.menuName = menuName;
		if (disabledChoices == null) {
			this.disabledChoices = new StaticChoice[0];
		} else {
			this.disabledChoices = disabledChoices.toArray(new StaticChoice[disabledChoices.size()]);
		}
	}

	public MenuStaticChoices(StaticChoicesMenuName menuName, Player player, MultiMenu multimenu) {
		this(menuName, null, player, multimenu);
	}

	public MenuStaticChoices(StaticChoicesMenuName menuName, Collection<StaticChoice> disabledChoices, Player player) {
		this(menuName, disabledChoices, player, null);
	}

	public MenuStaticChoices(StaticChoicesMenuName menuName, Player player) {
		this(menuName, null, player, null);
	}

	public StaticChoicesMenuName getMenuName() {
		return menuName;
	}

	public StaticChoice[] getDisabledChoices() {
		return disabledChoices;
	}

	/**
	 * Returns null if the player chose to cancel
	 * 
	 * @param cancel - If this menu has a "cancel" choice
	 * @return The object selected from the list, or null
	 */
	@Override
	public abstract StaticChoice selectChoice(boolean cancel);

}
