package abstraction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Area {

	private final int unitLimit;
	private final Resource resource;

	private List<Unit> listUnits = new ArrayList<Unit>();
	private Player hasBaseOwnedBy = null;

	// Just used for displaying the name of the Area
	private Planet planet;
	private int areaNumber;

	public Area(Resource resource, int unitLimit) {
		this.resource = resource;
		this.unitLimit = unitLimit;

		this.resource.setArea(this);
	}

	public int getUnitLimit() {
		return unitLimit;
	}

	public void addUnit(Unit unit) {
		listUnits.add(unit);
	}

	public void removeUnit(Unit unit) {
		listUnits.remove(unit);
	}

	public Player getBaseOwner() {
		return hasBaseOwnedBy;
	}

	public void buildBaseOwnedBy(Player player) {
		if(hasBaseOwnedBy != null) {
			throw new IllegalStateException("You cannot build a base on an existing one.");
		}
		hasBaseOwnedBy = player;
	}

	public Planet getPlanet() {
		return planet;
	}

	public void setPlanet(Planet planet) {
		if (this.planet == null) {
			this.planet = planet;
		} else {
			throw new IllegalStateException("You cannot change the planet of an area after it has been set. (Original planet: " + this.planet
					+ " ; Tried to change to: " + planet + ")");
		}
	}

	public int getAreaNumber() {
		return areaNumber;
	}

	public void setAreaNumber(int areaNumber) {
		this.areaNumber = areaNumber;
	}

	public boolean isControlledBy(Player player) {
		return !isEmpty() && !(hasBaseOwnedBy != player) && listUnits.get(0).getOwner() == player;
	}
	


	public boolean isEmpty() {
		return listUnits.isEmpty();
	}

	public boolean isFull() {
		return listUnits.size() >= unitLimit;
	}

	public String listUnits() {
		String result = "";
		Iterator<Unit> i = listUnits.iterator();
		while (i.hasNext()) {
			Unit unit = i.next();
			result += unit;
			result += "\n";
		}

		return result;
	}

	@Override
	public String toString() {
		return planet.getName() + "'s #" + areaNumber + " area";
	}

}