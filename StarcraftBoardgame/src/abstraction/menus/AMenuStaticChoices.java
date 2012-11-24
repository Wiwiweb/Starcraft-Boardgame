package abstraction.menus;

import java.util.Collection;

import abstraction.Player;
import abstraction.menus.AMenuStaticChoices.StaticChoice;

/**
 * @author William Gautier
 */
public abstract class AMenuStaticChoices extends AMenu<StaticChoice> {

	public static enum StaticChoicesMenuName {
		ROTATE_PLANET(StaticChoice.ROTATE_PLANET_CLOCKWISE, StaticChoice.ROTATE_PLANET_COUNTERCLOCKWISE,
				StaticChoice.ROTATE_PLANET_PLACE),
		PLACE_FIRST_BASE(StaticChoice.PLACE_FIRST_BASE_YES, StaticChoice.PLACE_FIRST_BASE_NO),
		PLACE_REMOVE_UNIT(StaticChoice.PLACE_REMOVE_UNIT_PLACE_UNIT, StaticChoice.PLACE_REMOVE_UNIT_REMOVE_UNIT,
				StaticChoice.PLACE_REMOVE_UNIT_PLACE_TRANSPORT, StaticChoice.PLACE_REMOVE_UNIT_REMOVE_TRANSPORT);

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
		PLACE_REMOVE_UNIT_PLACE_UNIT, PLACE_REMOVE_UNIT_REMOVE_UNIT, PLACE_REMOVE_UNIT_PLACE_TRANSPORT, PLACE_REMOVE_UNIT_REMOVE_TRANSPORT
	}

	private final StaticChoicesMenuName menuName;
	private final StaticChoice[] disabledChoices;

	public AMenuStaticChoices(StaticChoicesMenuName menuName, Player player) {
		super(player);
		this.menuName = menuName;
		this.disabledChoices = new StaticChoice[0];
	}

	public AMenuStaticChoices(StaticChoicesMenuName menuName, Collection<StaticChoice> disabledChoices, Player player) {
		super(player);
		this.menuName = menuName;
		this.disabledChoices = disabledChoices.toArray(new StaticChoice[disabledChoices.size()]);
	}

	public StaticChoicesMenuName getMenuName() {
		return menuName;
	}

	public StaticChoice[] getDisabledChoices() {
		return disabledChoices;
	}

	@Override
	public abstract StaticChoice selectChoice();

	/**
	 * Returns null if the player chose to cancel
	 * 
	 * @return The number of the choice selected, or null
	 */
	@Override
	public abstract StaticChoice selectChoiceWithCancel();

}
