package abstraction.menus.multimenus.states;

import java.util.ArrayList;
import java.util.List;

import abstraction.Area;
import abstraction.Route;
import abstraction.Unit;

/**
 * @author William Gautier
 */
public class MultiMenuPlaceStartingForcesChoices implements MultiMenuChoices {

	// placedUnit and placedUnitArea go together
	private List<Unit> placedUnits = new ArrayList<Unit>();
	private List<Area> placedUnitsAreas = new ArrayList<Area>();
	private List<Route> placedTransports = new ArrayList<Route>();

	public List<Unit> getPlacedUnits() {
		return placedUnits;
	}

	public void setPlacedUnits(List<Unit> placedUnits) {
		this.placedUnits = placedUnits;
	}

	public List<Area> getPlacedUnitsAreas() {
		return placedUnitsAreas;
	}

	public void setPlacedUnitsAreas(List<Area> placedUnitsAreas) {
		this.placedUnitsAreas = placedUnitsAreas;
	}

	public List<Route> getPlacedTransports() {
		return placedTransports;
	}

	public void setPlacedTransports(List<Route> placedTransports) {
		this.placedTransports = placedTransports;
	}

}
