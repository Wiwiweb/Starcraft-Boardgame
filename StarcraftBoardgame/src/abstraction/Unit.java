package abstraction;

import abstraction.patterns.UnitPattern;
import abstraction.patterns.UnitPattern.WalkType;

/**
 * @author William Gautier
 */
public class Unit implements Comparable<Unit> {

	private final UnitPattern pattern;
	private final Player owner;
	
	private Area area = null;

	public Unit(UnitPattern pattern, Player owner) {
		this.pattern = pattern;
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public String getName() {
		return pattern.getName();
	}

	public WalkType getWalkType() {
		return pattern.getWalkType();
	}

	public boolean canAttackGround() {
		return pattern.isGroundAttack();
	}

	public boolean canAttackAir() {
		return pattern.isAirAttack();
	}

	public int getSupport() {
		return pattern.getSupport();
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public boolean isAssist() {
		return pattern.isAssist();
	}

	@Override
	public int compareTo(Unit o) {
		int result = getName().compareTo(o.getName());
		if(result == 0) {
			if(getArea() == null) {
				if(o.getArea() == null) {
					result = -1;
				} else {
					result = 0;
				}
			} else if (o.getArea() == null) {
				result = 1;
			} else {
				result = getArea().compareTo(o.getArea());
			}
		}
		return result;
	}

	@Override
	public String toString() {
		if(area == null) {
			return owner.getName() + "'s " + getName();
		} else {
			return owner.getName() + "'s " + getName() + " placed on " + getArea();
		}
	}


}
