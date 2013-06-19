package abstraction.menus.multimenus.states;

import tools.PlanetEntrance;

/**
 * @author William Gautier
 */
public class MultiMenuPlaceZAxisChoices implements MultiMenuChoices {

	private PlanetEntrance entrance;
	private PlanetEntrance exit;
	
	public PlanetEntrance getEntrance() {
		return entrance;
	}
	
	public void setEntrance(PlanetEntrance entrance) {
		this.entrance = entrance;
	}
	
	public PlanetEntrance getExit() {
		return exit;
	}
	
	public void setExit(PlanetEntrance exit) {
		this.exit = exit;
	}

}
