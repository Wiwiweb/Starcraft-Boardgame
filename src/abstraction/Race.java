package abstraction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author William Gautier
 */
public class Race {

	public static enum Ability {
		TERRAN_LIFTOFF, TERRAN_VERSATILITY,
		ZERG_CREEP, ZERG_HEALING,
		PROTOSS_SHIELDS
	}

	private final String name;

	private final List<Ability> abilities = new ArrayList<Ability>();

	public Race(String name, String[] abilities) {
		this.name = name;
		for (String a : abilities) {
			try {
				this.abilities.add(Ability.valueOf(a));
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("No ability associated with the name " + a);
			}
		}
	}

	public String getName() {
		return name;
	}

	public List<Ability> getAbilities() {
		return abilities;
	}

}
