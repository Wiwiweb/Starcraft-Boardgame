package abstraction;

/**
 * @author William Gautier
 */
public class Faction implements Comparable<Faction> {

	private final String name;

	private final String raceName;
	private final String baseName;

	private final int startingWorkers;
	private final int startingTransports;

	private final String[] startingUnitTypes;
	private final int[] startingUnitNumbers;

	public Faction(String name, String raceName, String baseName, int startingWorkers, int startingTransports,
			String[] startingUnitTypes, int[] startingUnitNumbers) {
		this.name = name;
		this.raceName = raceName;
		this.baseName = baseName;
		this.startingWorkers = startingWorkers;
		this.startingTransports = startingTransports;
		this.startingUnitTypes = startingUnitTypes;
		this.startingUnitNumbers = startingUnitNumbers;
	}

	public String getName() {
		return name;
	}

	public String getRaceName() {
		return raceName;
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

	public String[] getStartingUnitTypes() {
		return startingUnitTypes;
	}

	public int getStartingUnitNumbers(int i) {
		return startingUnitNumbers[i];
	}

	@Override
	public String toString() {
		return "Faction " + getName();
	}

	@Override
	public int compareTo(Faction o) {
		return this.name.compareTo(o.name);
	}

}
