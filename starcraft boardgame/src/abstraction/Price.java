package abstraction;

public class Price {
	
	private final int minerals;
	private final int gas;
	
	public Price(int minerals, int gas) {
		this.minerals = minerals;
		this.gas = gas;
	}

	public int getMinerals() {
		return minerals;
	}

	public int getGas() {
		return gas;
	}
	
	@Override
	public String toString() {
		return "Price: " + minerals + " minerals, " + gas + " gas.";
	}
	

}
