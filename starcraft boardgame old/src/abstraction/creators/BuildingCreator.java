package abstraction.creators;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import abstraction.Building;
import abstraction.patterns.BuildingPattern;

public class BuildingCreator {
	
    private static Map<String, BuildingPattern> buildingPatterns = new ConcurrentHashMap<String, BuildingPattern>();
        
    public static Building createBuilding(String name) {
    	BuildingPattern pattern = buildingPatterns.get(name);    
    	if (pattern != null) {
    		return new Building(pattern);
	    } else {
			throw new IllegalArgumentException("No building pattern associated with the name " + name + ".");
		}
    }
    
    public static void addBuildingPattern(String name, BuildingPattern pattern) {
    	buildingPatterns.put(name, pattern);
    }

}
