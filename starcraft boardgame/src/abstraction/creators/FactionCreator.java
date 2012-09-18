package abstraction.creators;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import abstraction.Faction;

public class FactionCreator {

	// A faction is its own pattern.
	// It has no individual values, and thus can be shared by multiple players.
	private static Map<String, Faction> factionList = new ConcurrentHashMap<String, Faction>();

	public static Faction getFaction(String name) {
		Faction faction = factionList.get(name);
		if (faction != null) {
			return faction;
		} else {
			throw new IllegalArgumentException("No faction associated with the name " + name + ".");
		}
	}

	public static void addFaction(String name, Faction faction) {
		factionList.put(name, faction);
	}
}
