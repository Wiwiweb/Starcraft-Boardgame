package abstraction;

import abstraction.patterns.UnitPattern;
import abstraction.patterns.UnitPattern.WalkType;

/**
 * @author William Gautier
 */
public class Unit implements Comparable<Unit> {

	private final UnitPattern pattern;
	private final Player owner;

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

	public boolean isAssist() {
		return pattern.isAssist();
	}

	@Override
	public int compareTo(Unit o) {
		return pattern.getName().compareTo(o.getName());
	}

	@Override
	public String toString() {
		return owner.getName() + "'s " + getName();
	}
}
