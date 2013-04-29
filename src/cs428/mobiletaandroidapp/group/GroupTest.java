package cs428.mobiletaandroidapp.group;

import java.util.Random;

import android.test.AndroidTestCase;

/**
 * 
 * Test Cases for Group.java
 * 
 * @author Junhyun Park
 *
 */
public class GroupTest extends AndroidTestCase  {

	private static final int RANDOM_SEED = 19580427;
	
	private Random generator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		generator = new Random(RANDOM_SEED);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testConstructorOne() {
		int testSectionID = generator.nextInt();
		int testColor = generator.nextInt();
		
		Group group = new Group(testSectionID, testColor);
		
		assertNotNull(group);
		
		int sectionID = group.getSectionID();
		int color = group.getColor();
		
		assertEquals(testSectionID, sectionID);
		assertEquals(testColor, color);
	}
	
	public void testConstructorTwo() {
		int testID = generator.nextInt();
		int testSectionID = generator.nextInt();
		int testColor = generator.nextInt();
		
		Group group = new Group(testID, testSectionID, testColor);
		
		assertNotNull(group);
		
		int id = group.getId();
		int sectionID = group.getSectionID();
		int color = group.getColor();
		
		assertEquals(testID, id);
		assertEquals(testSectionID, sectionID);
		assertEquals(testColor, color);
	}
}
