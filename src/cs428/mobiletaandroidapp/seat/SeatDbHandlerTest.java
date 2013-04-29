package cs428.mobiletaandroidapp.seat;

import java.util.ArrayList;
import java.util.Random;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * 
 * Test Cases for SeatDatabaseHandler
 * 
 * @author Junhyun Park
 *
 */
public class SeatDbHandlerTest extends AndroidTestCase {

	private static final String TEST_FILE_PREFIX = "test_";
	
	private static final int RANDOM_SEED = 19580427;
	
	private static final int NUM_OF_DUMMIES = 3;
	
	private SeatDbHandler dbHandler;
	
	private Random generator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
		
		dbHandler = new SeatDbHandler(context);
		generator = new Random(RANDOM_SEED);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		dbHandler.closeSeatDB();
		dbHandler = null;
	}
	
	public void testPreConditions() {
	    assertNotNull(dbHandler);
	}

	public void testInsertSeatAndGetSeatWithRowID() {
		int[] seatId = new int[NUM_OF_DUMMIES];

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			int testX = generator.nextInt();
			int testY = generator.nextInt();
			
			Seat seat = new Seat(testX, testY);

			seatId[i] = dbHandler.insertSeat(seat);
			assertFalse(seatId[i] == -1);

			Seat testSeat = dbHandler.getSeatWithRowID(seatId[i]);
			assertEquals(seat.getX(), testSeat.getX());
			assertEquals(seat.getY(), testSeat.getY());
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++)
			dbHandler.deleteSeat(seatId[i]);	// not sure where to put it
	}
	
	public void testUpdateSeat() {
		int testX = generator.nextInt();
		int testY = generator.nextInt();
		
		Seat seat = new Seat(testX, testY);
		
		int rowID = dbHandler.insertSeat(seat);
		Seat testSeat = dbHandler.getSeatWithRowID(rowID);
		assertEquals(seat.getX(), testSeat.getX());
		assertEquals(seat.getY(), testSeat.getY());

		seat.setId(rowID);
		seat.setX(1);
		seat.setY(1);
		
		dbHandler.updateSeat(seat);

		testSeat = dbHandler.getSeatWithRowID(rowID);
		assertEquals(1, testSeat.getX());
		assertEquals(1, testSeat.getY());
	}
	
	public void testDeleteSeat() {
		int testX = generator.nextInt();
		int testY = generator.nextInt();
		
		Seat seat = new Seat(testX, testY);
		
		int rowID = dbHandler.insertSeat(seat);
		Seat testSeat = dbHandler.getSeatWithRowID(rowID);
		assertNotNull(testSeat);

		dbHandler.deleteSeat(rowID);

		testSeat = dbHandler.getSeatWithRowID(rowID);
		assertNull(testSeat);
	}

	public void testGetAllSeats() {
		ArrayList<Seat> seatList;
		int[] seatId = new int[NUM_OF_DUMMIES];
		int beforeCount = dbHandler.getAllSeats().size();

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			int testX = generator.nextInt();
			int testY = generator.nextInt();
			
			Seat seat = new Seat(testX, testY);

			seatId[i] = dbHandler.insertSeat(seat);
		}

		seatList = dbHandler.getAllSeats();
		int afterCount = seatList.size();
		
		assertEquals(beforeCount + NUM_OF_DUMMIES, afterCount);

		for (int i = 0; i < afterCount; i++) {
			Seat seat = seatList.get(i);
			Seat testSeat = dbHandler.getSeatWithRowID(seat.getId());
			assertNotNull(testSeat);
			assertEquals(seat.getX(), testSeat.getX());
			assertEquals(seat.getY(), testSeat.getY());
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			dbHandler.deleteSeat(seatId[i]);
		}
	}
}
