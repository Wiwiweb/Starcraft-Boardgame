package abstraction.patterns;

import abstraction.Resource.ResourceType;

public class AreaPattern {

	// These are special :
	// Only used inside PlanetPattern, created when the PlanetPattern is created.
	// No AreaCreator : PlanetCreator is the one creating the Areas when a Planet is created.
	// No list of AreaPatterns. PlanetPatterns are the ones owning AreaPatterns.
	// Not referenced in Area : Only used for creating the areas.

	private final int unitLimit;
	private final ResourceType resourceType;
	private final int resourceNum;
	
	public AreaPattern(int unitLimit, ResourceType resourceType, int resourceNum) {
		this.unitLimit = unitLimit;
		this.resourceType = resourceType;
		this.resourceNum = resourceNum;
	}
	
	public int getUnitLimit() {
		return unitLimit;
	}
	
	public ResourceType getResourceType() {
		return resourceType;
	}
	
	public int getResourceNum() {
		return resourceNum;
	}
	
	@Override
	public String toString() {
		return getResourceNum() + " " + getResourceType().toString() + " area (P)";
	}

}
