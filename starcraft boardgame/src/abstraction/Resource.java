package abstraction;

public class Resource {
	
	public enum ResourceType {
		MINERALS, GAS, CONTROL;
	}
	
	private final ResourceType resourceType;
	private final int resourceNum;
	private final boolean isPermanent;
	
	private int workersMining = 0;
	private int depletedLevel = 2;
	
	// Just used for displaying the name of the Resource
	private Area area;

	public Resource(ResourceType resourceType, int resourceNum, boolean isPermanent) {
		this.resourceType = resourceType;
		this.resourceNum = resourceNum;
		this.isPermanent = isPermanent;
	}
	
	public ResourceType getResourceType() {
		return resourceType;
	}

	public int getResourceNum() {
		return resourceNum;
	}
	
	public int getWorkersMining() {
		return workersMining;
	}
	
	public boolean hasEmptySpots() {
		return workersMining != resourceNum;
	}
	
	public void putWorkerToMine() {
		workersMining++;
	}
	
	public void removeAllWorkers() {
		workersMining = 0;
	}
	
	public int getDepletedLevel() {
		return depletedLevel;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	public void deplete() {
		if (depletedLevel == 0) {
			throw new IllegalStateException("The area is already depleted.");
		} else if (isPermanent) {
			throw new IllegalStateException("Cannot force mine permanent resources.");	
		} else {
			depletedLevel--;
		}
	}
	
	public void replenish() {
		if (depletedLevel < 2) {
			depletedLevel++;
		} else {
			throw new IllegalStateException("The area is already full.");
		}
	}
	

	@Override
	public String toString() {
		String result;
		if (isPermanent) {
			result = "Permanent : ";
		} else {
			result = area + " : ";
		}
		result += resourceNum + " " + resourceType;
		return result;
	}


}
