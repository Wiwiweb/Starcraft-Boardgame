package abstraction.creators;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import abstraction.Base;
import abstraction.Player;
import abstraction.patterns.BasePattern;

/**
 * @author William Gautier
 */
public class BaseCreator {

	private static Map<String, BasePattern> basePatterns = new ConcurrentHashMap<String, BasePattern>();

	public static Base createBase(String name, Player owner) {
		BasePattern pattern = basePatterns.get(name);
		if (pattern != null) {
			return new Base(pattern, owner, owner.getFaction().getStartingWorkers());
		} else {
			throw new IllegalArgumentException("No base pattern associated with the name " + name + ".");
		}

	}

	public static void addBasePattern(String name, BasePattern pattern) {
		basePatterns.put(name, pattern);
	}

}
