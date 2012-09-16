package abstraction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Faction {
	
    private static Map<String, Faction> factionList = new ConcurrentHashMap<String, Faction>();

	
	private final String name;
	
	private final String baseName;
	
	private final int startingWorkers;
	private final int startingTransports;
	
	private final String[] startingUnitTypes;
	private final int[] startingUnitNumbers;
	
	public Faction(String name, String baseName, int startingWorkers, int startingTransports, String[] startingUnitTypes, int[] startingUnitNumbers) {
		this.name = name;
		this.baseName = baseName;
		this.startingWorkers = startingWorkers;
		this.startingTransports = startingTransports;
		this.startingUnitTypes = startingUnitTypes;
		this.startingUnitNumbers = startingUnitNumbers;
	}

	public String getName() {
		return name;
	}

	public String getBaseName() {
		return baseName;
	}

	public int getStartingWorkers() {
		return startingWorkers;
	}

	public int getStartingTransports() {
		return startingTransports;
	}

	public String getStartingUnitTypes(int i) {
		return startingUnitTypes[i];
	}

	public int getStartingUnitNumbers(int i) {
		return startingUnitNumbers[i];
	}
	
	
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
