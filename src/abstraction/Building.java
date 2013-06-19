package abstraction;

import abstraction.patterns.BuildingPattern;

/**
 * @author William Gautier
 */
public class Building {

	private final BuildingPattern pattern;

	private int level = 0;

	public Building(BuildingPattern pattern) {
		this.pattern = pattern;
	}

	public String getName() {
		return pattern.getName();
	}

	public int getMaxLevel() {
		return pattern.getMaxLevel();
	}

	public int getLevel() {
		return level;
	}

	public String getUnitForLevel(int level) {
		return pattern.getUnitForLevel(level);
	}

	public Price getPriceForLevel(int level) {
		return pattern.getPriceForLevel(level);
	}

	public void increaseLevel() {
		if (level < getMaxLevel()) {
			level++;
		} else {
			throw new IllegalStateException("This building is already max level.");
		}
	}

	public String toString() {
		String result = getName() + ": Can build ";
		for (int i = 1; i <= getMaxLevel(); i++) {
			result += getUnitForLevel(i) + " ";
		}
		return result;
	}
}
