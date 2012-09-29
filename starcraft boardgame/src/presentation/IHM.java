package presentation;

import java.util.List;

import tools.PlanetEntrance;
import abstraction.Area;
import abstraction.Faction;
import abstraction.Planet;
import abstraction.Player;
import abstraction.Resource;
import abstraction.Resource.ResourceType;

public interface IHM {

	public Faction selectStartingFaction(Player player, List<Faction> list);

	public Planet selectPlanetToPlace(Player player, List<Planet> list);

	public int choosePlanetRotation(Player player, Planet planet);

	public PlanetEntrance selectSpotToPlacePlanet(Player player, List<PlanetEntrance> list, Planet planet);
	
	public Area selectAreaToPlaceBase(Player player, List<Area> list);

	public Resource selectResourceToSendWorker(Player player, List<Resource> list, int workerNumber, ResourceType resourceType);

	public Area selectAreaToPlaceUnit(Player player, List<Area> list);
	
	public boolean askToPlaceBase(Player player, Planet chosenPlanet);


}
