package abstraction;

import java.util.ArrayList;
import java.util.List;

import abstraction.creators.BuildingCreator;
import abstraction.patterns.BasePattern;

/**
 * @author William Gautier
 */
public class Base {

	private final BasePattern pattern;

	private final Player owner;
	private final List<Building> buildings = new ArrayList<Building>();
	private final List<Module> modules = new ArrayList<Module>();
	private final Resource[] permanentResources;

	private int workerPool;
	private int workerUnavailable = 0;

	public Base(BasePattern pattern, Player owner, int startingWorkers) {
		this.pattern = pattern;
		this.owner = owner;
		this.setWorkerPool(startingWorkers);
		this.permanentResources = new Resource[pattern.getPermanentResourcesType().length];

		for (int i = 0; i < pattern.getPermanentResourcesType().length; i++) {
			this.permanentResources[i] = new Resource(pattern.getPermanentResourcesType()[i],
					pattern.getPermanentResourcesNum()[i], true);
		}

		for (int i = 0; i < pattern.getBuildingNames().length; i++) {
			Building building = BuildingCreator.createBuilding(pattern.getBuildingName(i));
			buildings.add(building);
		}

		// First building starts built (level 1)
		buildings.get(0).increaseLevel();
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
		return buildings.get(i);
	}

	public List<Module> getModules() {
		return modules;
	}
	
	public void addModule(Module module) {
		if(modules.size() == getModulesMaxNum()) {
			throw new IllegalStateException("Cannot add any more modules.");
		}
		modules.add(module);
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
