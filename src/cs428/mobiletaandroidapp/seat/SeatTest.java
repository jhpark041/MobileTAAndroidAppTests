package cs428.mobiletaandroidapp.seat;

import java.util.Random;

import android.test.AndroidTestCase;

/**
 * 
 * Test Cases for Seat.java
 * 
 * @author Junhyun Park
 *
 */
public class SeatTest extends AndroidTestCase  {

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
		int testX = generator.nextInt();
		int testY = generator.nextInt();
		
		Seat seat = new Seat(testX, testY);
		
		assertNotNull(seat);
		
		int x = seat.getX();
		int y = seat.getY();
		
		assertEquals(testX, x);
		assertEquals(testY, y);
	}
	
	public void testConstructorTwo() {
		int testID = generator.nextInt();
		int testX = generator.nextInt();
		int testY = generator.nextInt();
		int testSectionID = generator.nextInt();
		
		Seat seat = new Seat(testID, testX, testY, testSectionID);
		
		assertNotNull(seat);
		
		int id = seat.getId();
		int x = seat.getX();
		int y = seat.getY();
		int sectionID = seat.getSectionID();
		
		assertEquals(testID, id);
		assertEquals(testX, x);
		assertEquals(testY, y);
		assertEquals(testSectionID, sectionID);
	}
}
