package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import abstraction.Race;
import abstraction.Race.Ability;

/**
 * @author William Gautier
 */
public class RaceTest extends Tests {

	private Race zerg;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		zerg = factory.getRace("Zerg");
	}

	/**
	 * Test method for {@link abstraction.Race#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals("Zerg", zerg.getName());
	}

	/**
	 * Test method for {@link abstraction.Race#getAbilities()}.
	 */
	@Test
	public void testGetAbilities() {
		List<Ability> abilities = zerg.getAbilities();
		assertTrue(abilities.contains(Ability.ZERG_CREEP));
		assertTrue(abilities.contains(Ability.ZERG_HEALING));
		assertFalse(abilities.contains(Ability.TERRAN_LIFTOFF));
	}

}
