package abstraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import abstraction.Resource.ResourceType;
import abstraction.creators.BaseCreator;
import abstraction.creators.UnitCreator;

public class Player {

	private String name;
	
	private final Faction faction;
	private final Base base;
	
	private List<Unit> units = new ArrayList<Unit>();
	private List<Resource> controlledResources = new ArrayList<Resource>();
	
	public Player(String name, Faction faction) {
		this.name = name;
		this.faction = faction;
		this.base = BaseCreator.createBase(faction.getBaseName(), this);
		controlledResources.addAll(Arrays.asList(base.getPermanentResources()));
	}
	
	public void buyUnit(String unitName, Planet orderPlanet) {
		if (!getBase().isAvailableUnit(unitName)) {
			throw new IllegalArgumentException("This unit is not available.");
		}
		Price price = UnitCreator.getPrice(unitName);
		
		int mineralWorkersNeeded = price.getMinerals();
		int gasWorkersNeeded = price.getGas();
		if(mineralWorkersNeeded + gasWorkersNeeded > base.getWorkerPool()) {
			throw new IllegalStateException("Not enough available workers.");
		}
		
		if (mineralWorkersNeeded > 0) {
			List<Resource> mineralResources = getAvailableMiningArea(ResourceType.MINERALS);
			if(!enoughAvailableResources(mineralResources, mineralWorkersNeeded)) {
				throw new IllegalStateException("Not enough available minerals.");
			}
			
			for (int i = 1; i <= mineralWorkersNeeded; i++) {
				Game.ihm.warnSendWorker(i, ResourceType.MINERALS);
				Resource chosenResource = Game.ihm.selectResourceFromList(mineralResources);
				sendWorkerToMine(chosenResource);
			}
		}
		
		if (gasWorkersNeeded > 0) {
			List<Resource> gasResources = getAvailableMiningArea(ResourceType.GAS);
			if(!enoughAvailableResources(gasResources, mineralWorkersNeeded)) {
				throw new IllegalStateException("Not enough available gas.");
			}
			
			for (int i = 1; i <= mineralWorkersNeeded; i++) {
				Game.ihm.warnSendWorker(i, ResourceType.GAS);
				Resource chosenResource = Game.ihm.selectResourceFromList(gasResources);
				sendWorkerToMine(chosenResource);
			}
		}
		
		Unit unit = createUnit(unitName);
		List<Area> buildableAreas = getBuildableAreas(orderPlanet);
		if (buildableAreas.isEmpty()) {
			throw new IllegalStateException("No space left to build a unit.");
		}
		Area selectedArea  = Game.ihm.selectAreaFromList(buildableAreas);
		selectedArea.addUnit(unit);
	}
	
	

	
	private List<Area> getBuildableAreas(Planet orderPlanet) {
		List<Area> result = new ArrayList<Area>();
		for(Area area : orderPlanet.getAreas()) {
			if( area.isEmpty() || ( area.isControlledBy(this) && !area.isFull() )) {
				result.add(area);
			}
		}
		return result;
	}

	private List<Resource> getAvailableMiningArea(ResourceType type) {
		List<Resource> available = new ArrayList<Resource>();
		for (Resource r : controlledResources) {
			if (r.getResourceType() == type && r.hasEmptySpots()) {
				available.add(r);
			}
		}		
		return available;
	}
	
	private void sendWorkerToMine(Resource resource) {
		if (getBase().getWorkerPool() > 0) {
			getBase().decrementWorkerPool();
			resource.putWorkerToMine();
		} else {
			throw new IllegalStateException("No worker available.");
		}
	}
	
	private boolean enoughAvailableResources(List<Resource> resources, int workersNeeded) {
		Iterator<Resource> it = resources.iterator();
		int resourcesAvailable = 0;
		while(it.hasNext() && resourcesAvailable < workersNeeded) {
			Resource resource = it.next();
			resourcesAvailable += resource.getResourceNum();
		}
		return resourcesAvailable >= workersNeeded;
	}
	
	private Unit createUnit(String unitName) {
		if (!getBase().isAvailableUnit(unitName)) {
			throw new IllegalArgumentException("This unit is not available.");
		}
		return UnitCreator.createUnit(unitName, this);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Faction getFaction() {
		return faction;
	}
	
	public Base getBase() {
		return base;
	}

	public void addUnit(Unit unit) {
		units.add(unit);
	}

	public String listUnits() {
		String result = getName() + "'s army:\n";
		Iterator<Unit> i = units.iterator();
		while(i.hasNext()) {
		    Unit unit = i.next(); 
		    result += unit.getName();
		    result += "\n";
		}
		
		return result;
	}



}
