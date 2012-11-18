package abstraction.patterns;

import abstraction.Price;

public class UnitPattern {
	
	public static enum WalkType {
		GROUND, AIR
	}
	
	private final Price price;
	private final int maxNum;
	
	private final String name;
	private final WalkType walkType;
	private final boolean groundAttack;
	private final boolean airAttack;
	private final int support;
	private final boolean assist;	
	
	public UnitPattern(String name, Price price, int maxNum, WalkType walkType, boolean groundAttack, boolean airAttack, int support, boolean assist) {
		this.name = name;
		this.maxNum = maxNum;
		this.price = price;
		this.walkType = walkType;
		this.groundAttack = groundAttack;
		this.airAttack = airAttack;
		this.support = support;
		this.assist = assist;
	}
	
	public String getName() {
		return name;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public Price getPrice() {
		return price;
	}

	public WalkType getWalkType() {
		return walkType;
	}

	public boolean isGroundAttack() {
		return groundAttack;
	}

	public boolean isAirAttack() {
		return airAttack;
	}

	public int getSupport() {
		return support;
	}

	public boolean isAssist() {
		return assist;
	}
	
	@Override
	public String toString() {
		return name + "(P)";		
	}

}
