package abstraction.creators;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import abstraction.Faction;

/**
 * @author William Gautier
 */
public class FactionCreator {

	// A faction is its own pattern.
	// It has no individual values, and thus can be shared by multiple players.
	private static Map<String, Faction> factionList = new LinkedHashMap<String, Faction>();

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

	public static List<Faction> getFactionList() {
		final List<Faction> result = new ArrayList<Faction>(factionList.values());
		return result;
	}
}
