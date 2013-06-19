package abstraction.menus.multimenus.states;

import tools.PlanetEntrance;
import abstraction.Area;
import abstraction.Planet;

/**
 * @author William Gautier
 */
public class MultiMenuPlacePlanetChoices implements MultiMenuChoices {

	private Planet chosenPlanet;
	private PlanetEntrance chosenSpot;
	private Boolean placeFirstBase;
	private Area chosenBaseArea;

	public Planet getChosenPlanet() {
		return chosenPlanet;
	}

	public void setChosenPlanet(Planet chosenPlanet) {
		this.chosenPlanet = chosenPlanet;
	}

	public PlanetEntrance getChosenSpot() {
		return chosenSpot;
	}

	public void setChosenSpot(PlanetEntrance chosenSpot) {
		this.chosenSpot = chosenSpot;
	}

	public Boolean isPlaceFirstBase() {
		return placeFirstBase;
	}

	public void setPlaceFirstBase(Boolean placeFirstBase) {
		this.placeFirstBase = placeFirstBase;
	}

	public Area getChosenBaseArea() {
		return chosenBaseArea;
	}

	public void setChosenBaseArea(Area chosenBaseArea) {
		this.chosenBaseArea = chosenBaseArea;
	}

}
