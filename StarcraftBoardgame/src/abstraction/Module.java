package abstraction;

import abstraction.patterns.ModulePattern;

/**
 * @author William Gautier
 */
public class Module {

	private final ModulePattern pattern;

	public Module(ModulePattern pattern) {
		this.pattern = pattern;
	}

	public String getName() {
		return pattern.getName();
	}
}
