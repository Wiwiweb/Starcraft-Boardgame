package abstraction.patterns;

import abstraction.Price;

public class BuildingPattern {

	private final String name;

	private final int maxLevel;
	private final Price[] levelPrices;
	
	private final String[] levelUnits;
	
	public BuildingPattern(String name, int maxLevel, Price[] levelPrices, String[] levelUnits) {
		this.name = name;
		this.maxLevel = maxLevel;
		
		if (levelPrices.length == maxLevel) {
			this.levelPrices = levelPrices;
		} else {
			throw new IllegalArgumentException("There must be exactly a single price for each level of building.");
		}
		
		if (levelUnits.length == maxLevel) {
			this.levelUnits = levelUnits;
		} else {
			throw new IllegalArgumentException("There must be exactly a single unit for each level of building.");
		}
	}	

	public String getName() {
		return name;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}

	public Price getPriceForLevel(int level) {
		if (level <= maxLevel) {
			return levelPrices[level-1];
		} else {
			throw new IllegalArgumentException("This level is above the maximum.");
		}
	}
	
	public String getUnitForLevel(int level) {
		if (level <= maxLevel) {
			return levelUnits[level-1];
		} else {
			throw new IllegalArgumentException("This level is above the maximum.");
		}
	}	 
}
