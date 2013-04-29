package cs428.mobiletaandroidapp.room;

import java.util.Random;

import android.test.AndroidTestCase;

/**
 * 
 * Test Cases for Room.java
 * 
 * @author Junhyun Park
 *
 */
public class RoomTest extends AndroidTestCase  {

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
		
		Room room = new Room(testName);
		
		assertNotNull(room);
		
		String name = room.getName();
		
		assertTrue(name.equals(testName));
	}
	
	public void testConstructorTwo() {
		int testID = generator.nextInt();
		String testName = "name" + generator.nextInt();
		
		Room room = new Room(testID, testName);
		
		assertNotNull(room);
		
		int id = room.getId();
		String name = room.getName();
		
		assertTrue(id == testID);
		assertTrue(name.equals(testName));
	}
}
