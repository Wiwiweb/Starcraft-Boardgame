package abstraction;

import java.util.Collection;

import abstraction.Order.OrderType;
import abstraction.Resource.ResourceType;
import abstraction.creators.BaseCreator;
import abstraction.creators.BuildingCreator;
import abstraction.creators.FactionCreator;
import abstraction.creators.ModuleCreator;
import abstraction.creators.PlanetCreator;
import abstraction.creators.RaceCreator;
import abstraction.creators.UnitCreator;
import abstraction.menus.MenuChooseFromList;
import abstraction.menus.MenuChooseFromList.ChooseFromListMenuName;
import abstraction.menus.MenuEnterChoice;
import abstraction.menus.MenuEnterChoice.EnterChoiceMenuName;
import abstraction.menus.MenuStaticChoices;
import abstraction.menus.MenuStaticChoices.StaticChoice;
import abstraction.menus.MenuStaticChoices.StaticChoicesMenuName;
import abstraction.menus.multimenus.MultiMenu;

/**
 * @author William Gautier
 */
public abstract class Factory {

	public Game newGame() {
		return new Game();
	}

	public Player newPlayer(String name) {
		return new Player(name);
	}

	public void newFaction(String name, String raceName, String baseName, int startingWorkers, int startingTransports,
			String[] startingUnitTypes, int[] startingUnitNumbers) {
		Faction faction = new Faction(name, raceName, baseName, startingWorkers, startingTransports, startingUnitTypes,
				startingUnitNumbers);
		FactionCreator.addFaction(name, faction);
	}

	public Faction getFaction(String name) {
		return FactionCreator.getFaction(name);
	}

	public void newRace(String name, String[] abilities) {
		Race race = new Race(name, abilities);
		RaceCreator.addRace(name, race);
	}

	public Race getRace(String name) {
		return RaceCreator.getRace(name);
	}

	public Galaxy newGalaxy() {
		return new Galaxy();
	}

	public Planet newPlanet(String name) {
		return PlanetCreator.createPlanet(name, this);
	}

	public Area newArea(Resource resource, int unitLimit) {
		return new Area(resource, unitLimit);
	}

	public Resource newResource(ResourceType resourceType, int resourceNum, boolean isPermanent) {
		return new Resource(resourceType, resourceNum, isPermanent);
	}

	public Route newRoute(Planet planet1, Planet planet2, boolean isZAxis) {
		return new Route(planet1, planet2, isZAxis);
	}

	public Base newBase(String name, Player owner) {
		return BaseCreator.createBase(name, owner, this);
	}

	public Building newBuilding(String name) {
		return BuildingCreator.createBuilding(name);
	}

	public Module newModule(String name) {
		return ModuleCreator.createModule(name);
	}

	public Unit newUnit(String name, Player owner) {
		return UnitCreator.createUnit(name, owner);
	}

	public Order newOrder(OrderType type, Player player, Planet planet) {
		return new Order(type, player, planet);
	}

	public Price newPrice(int minerals, int gas) {
		return new Price(minerals, gas);
	}

	public abstract <T extends Comparable<? super T>> MenuChooseFromList<T> newMenuChooseFromList(
			ChooseFromListMenuName menuName, Collection<T> listChoices, Player player, MultiMenu multiMenu);

	public abstract <T extends Comparable<? super T>> MenuChooseFromList<T> newMenuChooseFromList(
			ChooseFromListMenuName menuName, Collection<T> listChoices, Player player);

	public abstract MenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName, Player player, MultiMenu multiMenu);

	public abstract MenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName, Player player);

	public abstract MenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName,
			Collection<StaticChoice> disabledChoices, Player player, MultiMenu multiMenu);

	public abstract MenuStaticChoices newMenuStaticChoices(StaticChoicesMenuName menuName,
			Collection<StaticChoice> disabledChoices, Player player);

	public abstract <T> MenuEnterChoice<T> newMenuEnterChoice(EnterChoiceMenuName menuName, Class<T> inputClass, Player player,
			MultiMenu multiMenu);

	public abstract <T> MenuEnterChoice<T> newMenuEnterChoice(EnterChoiceMenuName menuName, Class<T> inputClass, Player player);
}
