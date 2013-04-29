package cs428.mobiletaandroidapp.section;

import java.util.Random;

import android.test.AndroidTestCase;

/**
 * 
 * Test Cases for Section.java
 * 
 * @author Junhyun Park
 *
 */
public class SectionTest extends AndroidTestCase {

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
		String testName = "name" + generator.nextInt();
		String testCourse = "course" + generator.nextInt();
		
		Section section = new Section(testName, testCourse);
		
		assertNotNull(section);
		
		String name = section.getName();
		String course = section.getCourse();
		
		assertTrue(name.equals(testName));
		assertTrue(course.equals(testCourse));
	}
	
	public void testConstructorTwo() {
		int testID = generator.nextInt();
		String testName = "name" + generator.nextInt();
		String testCourse = "course" + generator.nextInt();
		int testRoomID = generator.nextInt();
		
		Section section = new Section(testID, testName, testCourse, testRoomID);
		
		assertNotNull(section);
		
		int id = section.getId();
		String name = section.getName();
		String course = section.getCourse();
		int roomID = section.getRoomID();
		
		assertTrue(id == testID);
		assertTrue(name.equals(testName));
		assertTrue(course.equals(testCourse));
		assertTrue(roomID == testRoomID);
	}
}
