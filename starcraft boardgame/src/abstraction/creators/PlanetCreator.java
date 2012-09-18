package abstraction.creators;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import abstraction.Area;
import abstraction.Planet;
import abstraction.Resource;
import abstraction.patterns.AreaPattern;
import abstraction.patterns.PlanetPattern;


public class PlanetCreator {
    private static Map<String, PlanetPattern> planetPatterns = new ConcurrentHashMap<String, PlanetPattern>();
    
    public static Planet createPlanet(String name) {
    	PlanetPattern pattern = planetPatterns.get(name);    	
		if (pattern != null) {
			Area[] areas = new Area[pattern.getAreas().length];
			int i = 0;
			for(AreaPattern areaP : pattern.getAreas()) {
				Resource resource = new Resource(areaP.getResourceType(), areaP.getResourceNum(), false);
				areas[i] = new Area(resource, areaP.getUnitLimit());
				i++;
			}
			Planet planet =  new Planet(pattern, areas);
			return planet;
	    } else {
			throw new IllegalArgumentException("No planet pattern associated with the name " + name + ".");
		}
    }
    
    public static void addPlanetPattern(String name, PlanetPattern pattern) {
    	planetPatterns.put(name, pattern);
    }
}
