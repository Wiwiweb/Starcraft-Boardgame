package abstraction.creators;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import abstraction.Race;

/**
 * @author William Gautier
 */
public class RaceCreator {

	// A race is its own pattern.
	// It has no individual values, and thus can be shared by multiple players.
	private static Map<String, Race> raceList = new LinkedHashMap<String, Race>();

	public static Race getRace(String name) {
		Race race = raceList.get(name);
		if (race != null) {
			return race;
		} else {
			throw new IllegalArgumentException("No race associated with the name " + name + ".");
		}
	}

	public static void addRace(String name, Race race) {
		raceList.put(name, race);
	}

	public static List<Race> getRaceList() {
		final List<Race> result = new ArrayList<Race>(raceList.values());
		return result;
	}
}
