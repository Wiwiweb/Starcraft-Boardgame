package abstraction;

import abstraction.creators.BuildingCreator;
import abstraction.patterns.BasePattern;

public class Base {

	private final BasePattern pattern;

	private final Player owner;
	private final Building[] buildings = new Building[3];
	private final Resource[] permanentResources;

	private int workerPool;
	private int workerUnavailable = 0;

	public Base(BasePattern pattern, Player owner, int startingWorkers) {
		this.pattern = pattern;
		this.owner = owner;
		this.setWorkerPool(startingWorkers);
		this.permanentResources = new Resource[pattern.getPermanentResourcesType().length];

		for (int i = 0; i < pattern.getPermanentResourcesType().length; i++) {
			this.permanentResources[i] = new Resource(pattern.getPermanentResourcesType()[i], pattern.getPermanentResourcesNum()[i], true);
		}

		for (int i = 0; i < 3; i++) {
			buildings[i] = BuildingCreator.createBuilding(pattern.getBuildingName(i));
		}

		// First building starts built (level 1)
		buildings[0].increaseLevel();
	}

	public boolean isAvailableUnit(String unitName) {
		for (Building b : buildings) {
			for (int i = 1; i <= b.getLevel(); i++) {
				if (unitName.equals(b.getUnitForLevel(i))) {
					return true;
				}
			}
		}
		return false;
	}

	public String getName() {
		return pattern.getName();
	}

	public Building getBuilding(int i) {
		return buildings[i];
	}

	public Resource[] getPermanentResources() {
		return permanentResources;
	}

	public int getModulesMaxNum() {
		return pattern.getModulesMaxNum();
	}

	public int getWorkerMaxNum() {
		return pattern.getWorkerMaxNum();
	}

	public Price getWorkerPrice() {
		return pattern.getWorkerPrice();
	}

	public int getTransportMaxNum() {
		return pattern.getTransportMaxNum();
	}

	public Price getTransportPrice() {
		return pattern.getTransportPrice();
	}

	public int getBaseMaxNum() {
		return pattern.getBaseMaxNum();
	}

	public Price getBasePrice() {
		return pattern.getBasePrice();
	}

	public Player getOwner() {
		return owner;
	}

	public int getWorkerPool() {
		return workerPool;
	}

	public void setWorkerPool(int workerPool) {
		this.workerPool = workerPool;
	}

	public void decrementWorkerPool() {
		workerPool--;
	}

	public int getWorkerUnavailable() {
		return workerUnavailable;
	}

	public void setWorkerUnavailable(int workerUnavailable) {
		this.workerUnavailable = workerUnavailable;
	}

}
