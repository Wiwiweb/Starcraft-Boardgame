package abstraction.patterns;

import abstraction.Price;
import abstraction.Resource;

public class BasePattern {
	
	private final String name;
	
	private final String[] buildingNames;
	
	private final int modulesMaxNum;
	private final String[] availableModules;
	
	private final int workersMaxNum;
	private final Price workerPrice;
	
	private final int transportsMaxNum;
	private final Price transportPrice;

	private final int basesMaxNum;
	private final Price basePrice;
	
	private final Resource[] permanentResources;
	
	public BasePattern(String name, String[] buildingNames, int modulesMaxNum, String[] availableModules, Resource[] permanentResources,
						int workersMaxNum, Price workerPrice, int transportsMaxNum, Price transportPrice, int basesMaxNum, Price basePrice) {
		this.name = name;
		
		if (buildingNames.length == 3) {
			this.buildingNames = buildingNames;
		} else {
			throw new IllegalArgumentException("A base must have exactly 3 buildings.");
		}
		
		this.modulesMaxNum = modulesMaxNum;
		this.availableModules = availableModules;
		this.permanentResources = permanentResources;
		this.workersMaxNum = workersMaxNum;
		this.workerPrice = workerPrice;
		this.transportsMaxNum = transportsMaxNum;
		this.transportPrice = transportPrice;
		this.basesMaxNum = basesMaxNum;
		this.basePrice = basePrice;
	}

	public String getName() {
		return name;
	}

	public String getBuildingName(int i) {
		return buildingNames[i];
	}

	public int getModulesMaxNum() {
		return modulesMaxNum;
	}

	public String[] getAvailableModules() {
		return availableModules;
	}
	
	public Resource[] getPermanentResources() {
		return permanentResources;
	}

	public int getWorkerMaxNum() {
		return workersMaxNum;
	}


	public Price getWorkerPrice() {
		return workerPrice;
	}

	public int getTransportMaxNum() {
		return transportsMaxNum;
	}

	public Price getTransportPrice() {
		return transportPrice;
	}

	public int getBaseMaxNum() {
		return basesMaxNum;
	}

	public Price getBasePrice() {
		return basePrice;
	}
}
