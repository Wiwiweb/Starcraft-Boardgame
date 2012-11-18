package abstraction.menus;

import java.util.ArrayList;
import java.util.List;

import abstraction.Area;
import abstraction.Player;
import abstraction.Route;
import abstraction.Unit;
import abstraction.menus.AMenu.MenuName;


public class MultiMenuPlaceStartingForces extends MultiMenu {
	
	private List<Unit> startingUnits;
	private int startingTransports;
	
	// placedUnit and placedUnitArea go together
	private List<Unit> placedUnits = new ArrayList<Unit>();
	private List<Area> placedUnitsAreas = new ArrayList<Area>();
	private List<Route> transportsPlaced = new ArrayList<Route>();
	
	private final MenuName[] menuNames = {  };

	public MultiMenuPlaceStartingForces(Player player, List<Unit> startingUnits, int startingTransports) {
		super(player);
		this.startingUnits = startingUnits;
		this.startingTransports = startingTransports;
	}

	@Override
	protected AMenu<?> getMenu(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void updateState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSelection() {
		// TODO Auto-generated method stub

	}

	
	public List<Unit> getPlacedUnits() {
		return placedUnits;
	}

	
	public List<Area> getPlacedUnitsAreas() {
		return placedUnitsAreas;
	}

	
	public List<Route> getTransportsPlaced() {
		return transportsPlaced;
	}

}
