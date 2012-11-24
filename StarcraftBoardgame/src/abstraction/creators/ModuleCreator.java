package abstraction.creators;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import abstraction.Module;
import abstraction.patterns.ModulePattern;

/**
 * @author William Gautier
 */
public class ModuleCreator {

	private static Map<String, ModulePattern> modulePatterns = new ConcurrentHashMap<String, ModulePattern>();

	public static Module createModule(String name) {
		ModulePattern pattern = modulePatterns.get(name);
		if (pattern != null) {
			return new Module(pattern);
		} else {
			throw new IllegalArgumentException("No module pattern associated with the name " + name + ".");
		}
	}

	public static void addModulePattern(String name, ModulePattern pattern) {
		modulePatterns.put(name, pattern);
	}

}
