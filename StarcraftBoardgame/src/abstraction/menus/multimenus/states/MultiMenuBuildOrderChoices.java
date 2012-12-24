package abstraction.menus.multimenus.states;

import java.util.List;

import abstraction.Area;
import abstraction.Building;
import abstraction.Module;
import abstraction.Resource;
import abstraction.Route;
import abstraction.Unit;

/**
 * @author William Gautier
 */
public class MultiMenuBuildOrderChoices implements MultiMenuChoices {

	private List<Resource> workersSent;

	private int workersBuilt;
	private List<Route> transportsBuilt;
	private List<Unit> unitsBuilt;
	private List<Area> unitsBuiltAreas;

	private Building buildingBuilt;
	private int buildingLevelBuilt;

	private Module moduleBuilt;

	private Area baseBuilt;

	public List<Resource> getWorkersSent() {
		return workersSent;
	}

	public void setWorkersSent(List<Resource> workersSent) {
		this.workersSent = workersSent;
	}

	public int getWorkersBuilt() {
		return workersBuilt;
	}

	public void setWorkersBuilt(int workersBuilt) {
		this.workersBuilt = workersBuilt;
	}

	public List<Route> getTransportsBuilt() {
		return transportsBuilt;
	}

	public void setTransportsBuilt(List<Route> transportsBuilt) {
		this.transportsBuilt = transportsBuilt;
	}

	public List<Unit> getUnitsBuilt() {
		return unitsBuilt;
	}

	public void setUnitsBuilt(List<Unit> unitsBuilt) {
		this.unitsBuilt = unitsBuilt;
	}

	public List<Area> getUnitsBuiltAreas() {
		return unitsBuiltAreas;
	}

	public void setUnitsBuiltAreas(List<Area> unitsBuiltAreas) {
		this.unitsBuiltAreas = unitsBuiltAreas;
	}

	public Building getBuildingBuilt() {
		return buildingBuilt;
	}

	public void setBuildingBuilt(Building buildingBuilt) {
		this.buildingBuilt = buildingBuilt;
	}

	public int getBuildingLevelBuilt() {
		return buildingLevelBuilt;
	}

	public void setBuildingLevelBuilt(int buildingLevelBuilt) {
		this.buildingLevelBuilt = buildingLevelBuilt;
	}

	public Module getModuleBuilt() {
		return moduleBuilt;
	}

	public void setModuleBuilt(Module moduleBuilt) {
		this.moduleBuilt = moduleBuilt;
	}

	public Area getBaseBuilt() {
		return baseBuilt;
	}

	public void setBaseBuilt(Area baseBuilt) {
		this.baseBuilt = baseBuilt;
	}
}
