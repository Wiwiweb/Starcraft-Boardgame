package abstraction.patterns;

import abstraction.Price;

public class ModulePattern {
	
	private final int maxNum;
	private final Price price;
		
	private final String name;
	
	public ModulePattern(String name, int maxNum, Price price) {
		this.name = name;
		this.maxNum = maxNum;
		this.price = price;
	}

	public Price getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public int getMaxNum() {
		return maxNum;
	}
}
