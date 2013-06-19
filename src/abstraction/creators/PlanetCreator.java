package abstraction.creators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import abstraction.Area;
import abstraction.Factory;
import abstraction.Planet;
import abstraction.Resource;
import abstraction.patterns.AreaPattern;
import abstraction.patterns.PlanetPattern;

/**
 * @author William Gautier
 */
public class PlanetCreator {

	private static Map<String, PlanetPattern> planetPatterns = new ConcurrentHashMap<String, PlanetPattern>();

	public static Planet createPlanet(String name, Factory factory) {
		PlanetPattern pattern = planetPatterns.get(name);
		if (pattern != null) {
			List<Area> areas = new ArrayList<Area>();
			for (AreaPattern areaP : pattern.getAreas()) {
				Resource resource = factory.newResource(areaP.getResourceType(), areaP.getResourceNum(), false);
				areas.add(factory.newArea(resource, areaP.getUnitLimit()));
			}
			Planet planet = new Planet(pattern, areas);
			return planet;
		} else {
			throw new IllegalArgumentException("No planet pattern associated with the name " + name + ".");
		}
	}

	public static void addPlanetPattern(String name, PlanetPattern pattern) {
		planetPatterns.put(name, pattern);
	}

	public static List<String> getPlanetNamesList() {
		final List<String> result = new ArrayList<String>(planetPatterns.keySet());
		return result;
	}
}
