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
		
		Group group = new Group(testSectionID);
		
		assertNotNull(group);
		
		int sectionID = group.getSectionID();
		
		assertEquals(testSectionID, sectionID);
	}
	
	public void testConstructorTwo() {
		String testName = "Name" + generator.nextInt();
		int testSectionID = generator.nextInt();
		int testColor = generator.nextInt();
		
		Group group = new Group(testName, testSectionID, testColor);
		
		assertNotNull(group);
		
		String name = group.getName();
		int sectionID = group.getSectionID();
		int color = group.getColor();
		
		assertTrue(testName.equals(name));
		assertEquals(testSectionID, sectionID);
		assertEquals(testColor, color);
	}
	
	public void testConstructorThree() {
		int testID = generator.nextInt();
		String testName = "Name" + generator.nextInt();
		int testSectionID = generator.nextInt();
		int testColor = generator.nextInt();
		
		Group group = new Group(testID, testName, testSectionID, testColor);
		
		assertNotNull(group);
		
		int id = group.getId();
		String name = group.getName();
		int sectionID = group.getSectionID();
		int color = group.getColor();
		
		assertEquals(testID, id);
		assertTrue(testName.equals(name));
		assertEquals(testSectionID, sectionID);
		assertEquals(testColor, color);
	}
}
