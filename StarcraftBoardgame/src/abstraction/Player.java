package abstraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import abstraction.Order.OrderType;
import abstraction.Resource.ResourceType;
import abstraction.creators.BaseCreator;
import abstraction.creators.FactionCreator;
import abstraction.creators.UnitCreator;

/**
 * @author William Gautier
 */
public class Player {

	private String name;

	/**
	 * Used during the galaxy setup. Shows what planet the player can place.
	 */
	private List<Planet> planetTokens = new ArrayList<Planet>();

	private Faction faction;
	private Base base;
	private Planet startingPlanet;

	private List<Unit> units = new ArrayList<Unit>();
	private List<Resource> controlledResources = new ArrayList<Resource>();
	private int baseNumber = 0;

	// Attributes for a single round
	private int ordersLeftToPlace;
	private int specialOrdersAvailable;
	private HashMap<OrderType, Integer> orderTypePlaced = new HashMap<OrderType, Integer>();

	public Player(String name) {
		this.name = name;
	}

	public void buyUnit(String unitName, Planet orderPlanet) {
		// TODO remake
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
		List<Area> buildableAreas = orderPlanet.getBuildableAreas(this);
		if (buildableAreas.isEmpty()) {
			throw new IllegalStateException("No space left to build a unit.");
		}
		Area selectedArea = Game.ihm.selectAreaToPlaceUnit(this, buildableAreas);
		selectedArea.addUnit(unit);
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
		if (faction == null) {
			throw new IllegalStateException("This player has no faction");
		}
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

	public void setFaction(String faction) {
		setFaction(FactionCreator.getFaction(faction));
	}

	public Faction selectFactionFromList(List<Faction> list) {
		return Game.ihm.selectStartingFaction(this, list);
	}

	public Base getBase() {
		if (base == null) {
			throw new IllegalStateException("This player has no base");
		}
		return base;
	}

	public Planet getStartingPlanet() {
		return startingPlanet;
	}

	public void setStartingPlanet(Planet startingPlanet) {
		this.startingPlanet = startingPlanet;
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

	public void addControlledResource(Resource resource) {
		if (resource.getResourceType() == ResourceType.CONTROL) {
			throw new IllegalArgumentException("Control point areas can't be controlled by a player (not a spendable resource).");
		}
		controlledResources.add(resource);
	}

	public boolean hasTransport(Route route) {
		return route.getTransports().contains(this);
	}

	public int getOrdersLeftToPlace() {
		return ordersLeftToPlace;
	}

	public void setOrdersLeftToPlace(int ordersLeftToPlace) {
		this.ordersLeftToPlace = ordersLeftToPlace;
	}

	public void decrementOrdersLeftToPlace() {
		this.ordersLeftToPlace--;
	}

	public int getSpecialOrdersAvailable() {
		return specialOrdersAvailable;
	}

	public void decrementSpecialOrdersAvailable() {
		this.specialOrdersAvailable--;
	}

	int getSpecialOrdersMaxAmount() {
		int result = 0;
		for (Module m : getBase().getModules()) {
			if (m.getName() == "Research & Development") {
				result++;
			}
		}
		return result;
	}
	
	public int getOrderTypePlaced(OrderType orderType) {
		return orderTypePlaced.get(orderType);
	}

	public void setOrderTypePlaced(HashMap<OrderType, Integer> orderTypePlaced) {
		this.orderTypePlaced = orderTypePlaced;
	}
	
	public void resetOrdersAttributes() {
		ordersLeftToPlace = Game.NB_ORDERS_PER_PLAYER;
		specialOrdersAvailable = getSpecialOrdersMaxAmount();
		for(OrderType o : OrderType.values()) {
			orderTypePlaced.put(o, 0);
		}
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

	public List<Unit> getUnits() {
		return units;
	}

}
