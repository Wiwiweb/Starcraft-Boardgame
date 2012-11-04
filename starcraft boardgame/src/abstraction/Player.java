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

	/**
	 * Used during the galaxy setup. Shows what planet the player can place.
	 */
	private List<Planet> planetTokens = new ArrayList<Planet>();

	private Faction faction;
	private Base base;

	private List<Unit> units = new ArrayList<Unit>();
	private List<Resource> controlledResources = new ArrayList<Resource>();
	private int baseNumber = 0;

	public Player(String name) {
		this.name = name;
	}

	public void buyUnit(String unitName, Planet orderPlanet) {
		if (!getBase().isAvailableUnit(unitName)) {
			throw new IllegalArgumentException("This unit is not available.");
		}
		Price price = UnitCreator.getPrice(unitName);

		int mineralWorkersNeeded = price.getMinerals();
		int gasWorkersNeeded = price.getGas();
		if (mineralWorkersNeeded + gasWorkersNeeded > base.getWorkerPool()) {
			throw new IllegalStateException("Not enough available workers.");
		}

		if (mineralWorkersNeeded > 0) {
			List<Resource> mineralResources = getAvailableMiningArea(ResourceType.MINERALS);
			if (!enoughAvailableResources(mineralResources, mineralWorkersNeeded)) {
				throw new IllegalStateException("Not enough available minerals.");
			}

			for (int i = 1; i <= mineralWorkersNeeded; i++) {
				Resource chosenResource = Game.ihm.selectResourceToSendWorker(this, mineralResources, i, ResourceType.MINERALS);
				sendWorkerToMine(chosenResource);
			}
		}

		if (gasWorkersNeeded > 0) {
			List<Resource> gasResources = getAvailableMiningArea(ResourceType.GAS);
			if (!enoughAvailableResources(gasResources, mineralWorkersNeeded)) {
				throw new IllegalStateException("Not enough available gas.");
			}

			for (int i = 1; i <= mineralWorkersNeeded; i++) {
				Resource chosenResource = Game.ihm.selectResourceToSendWorker(this, gasResources, i, ResourceType.GAS);
				sendWorkerToMine(chosenResource);
			}
		}

		Unit unit = createUnit(unitName);
		List<Area> buildableAreas = getBuildableAreas(orderPlanet);
		if (buildableAreas.isEmpty()) {
			throw new IllegalStateException("No space left to build a unit.");
		}
		Area selectedArea = Game.ihm.selectAreaToPlaceUnit(this, buildableAreas);
		selectedArea.addUnit(unit);
	}

	private List<Area> getBuildableAreas(Planet orderPlanet) {
		List<Area> result = new ArrayList<Area>();
		for (Area area : orderPlanet.getAreas()) {
			if (area.isEmpty() || (area.isControlledBy(this) && !area.isFull())) {
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
		while (it.hasNext() && resourcesAvailable < workersNeeded) {
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

	public void setFaction(Faction faction) {
		if (this.faction == null) {
			this.faction = faction;
			this.base = BaseCreator.createBase(faction.getBaseName(), this);
			controlledResources.addAll(Arrays.asList(base.getPermanentResources()));
		} else {
			throw new IllegalStateException(
					"You cannot change the faction of a player after it has been set. (Original faction: " + this.faction
							+ " ; Tried to change to: " + faction + ")");
		}
	}

	public Faction selectFactionFromList(List<Faction> list) {
		return Game.ihm.selectStartingFaction(this, list);
	}

	public Base getBase() {
		return base;
	}

	public void addUnit(Unit unit) {
		units.add(unit);
	}

	public void placeBase(Area area) {
		if (getBaseNumber() == getBase().getBaseMaxNum()) {
			throw new IllegalStateException("You cannot build more bases.");
		}
		area.buildBaseOwnedBy(this);
		addToBaseNumber();
	}

	public int getBaseNumber() {
		return baseNumber;
	}

	public void addToBaseNumber() {
		this.baseNumber++;
	}

	public void removeToBaseNumber() {
		this.baseNumber--;
	}

	public List<Planet> getPlanetTokens() {
		return planetTokens;
	}

	public void addPlanetToken(Planet planetToken) {
		this.planetTokens.add(planetToken);
	}

	public void removePlanetToken(Planet planetToken) {
		this.planetTokens.remove(planetToken);
	}

	public String listUnits() {
		String result = getName() + "'s army:\n";
		Iterator<Unit> i = units.iterator();
		while (i.hasNext()) {
			Unit unit = i.next();
			result += unit.getName();
			result += "\n";
		}

		return result;
	}

	@Override
	public String toString() {
		return "Player " + name;
	}

}
