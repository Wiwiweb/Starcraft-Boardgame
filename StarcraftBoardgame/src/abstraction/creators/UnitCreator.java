package abstraction.creators;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import abstraction.Player;
import abstraction.Price;
import abstraction.Unit;
import abstraction.patterns.UnitPattern;

public class UnitCreator {
	
    private static Map<String, UnitPattern> unitPatterns = new ConcurrentHashMap<String, UnitPattern>();
        
    public static Unit createUnit(String name, Player owner) {
    	UnitPattern pattern = unitPatterns.get(name);    	
		if (pattern != null) {
			Unit unit =  new Unit(pattern, owner);
			owner.addUnit(unit);
			return unit;
	    } else {
			throw new IllegalArgumentException("No unit pattern associated with the name " + name + ".");
		}
    }
    
    public static Price getPrice(String unitName) {
    	UnitPattern pattern = unitPatterns.get(unitName);    	
    	return pattern.getPrice();
    }
    
    public static void addUnitPattern(String name, UnitPattern pattern) {
    	unitPatterns.put(name, pattern);
    }
}
