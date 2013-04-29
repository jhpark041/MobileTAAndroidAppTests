package cs428.mobiletaandroidapp.room;

import java.util.ArrayList;
import java.util.Random;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import cs428.mobiletaandroidapp.room.Room;
import cs428.mobiletaandroidapp.room.RoomDbHandler;

/**
 * 
 * Test Cases for RoomDatabaseHandler
 * 
 * @author Junhyun Park
 *
 */
public class RoomDbHandlerTest extends AndroidTestCase {

	private static final String TEST_FILE_PREFIX = "test_";
	
	private static final int RANDOM_SEED = 19580427;
	
	private static final int NUM_OF_DUMMIES = 3;
	
	private RoomDbHandler dbHandler;
	
	private Random generator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
		
		dbHandler = new RoomDbHandler(context);
		generator = new Random(RANDOM_SEED);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		dbHandler.closeRoomDB();
		dbHandler = null;
	}
	
	public void testPreConditions() {
	    assertNotNull(dbHandler);
	}

	public void testInsertRoomAndGetRoomWithRowID() {
		int[] roomId = new int[NUM_OF_DUMMIES];

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			String testName = "name" + generator.nextInt();
			
			Room room = new Room(testName);

			roomId[i] = dbHandler.insertRoom(room);
			assertFalse(roomId[i] == -1);

			Room testRoom = dbHandler.getRoomWithRowID(roomId[i]);
			assertTrue(room.getName().equals(testRoom.getName()));
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++)
			dbHandler.deleteRoom(roomId[i]);	// not sure where to put it
	}
	
	public void testDeleteRoom() {
		String testName = "name" + generator.nextInt();
		
		Room room = new Room(testName);
		
		int rowID = dbHandler.insertRoom(room);
		Room testRoom = dbHandler.getRoomWithRowID(rowID);
		assertNotNull(testRoom);

		dbHandler.deleteRoom(rowID);

		testRoom = dbHandler.getRoomWithRowID(rowID);
		assertNull(testRoom);
	}
	

	public void testGetAllRooms() {
		ArrayList<Room> roomList;
		int[] roomId = new int[NUM_OF_DUMMIES];
		int beforeCount = dbHandler.getAllRooms().size();

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			String testName = "name" + generator.nextInt();
			
			Room room = new Room(testName);

			roomId[i] = dbHandler.insertRoom(room);
		}

		roomList = dbHandler.getAllRooms();
		int afterCount = roomList.size();
		
		assertEquals(beforeCount + NUM_OF_DUMMIES, afterCount);

		for (int i = 0; i < afterCount; i++) {
			Room room = roomList.get(i);
			Room testRoom = dbHandler.getRoomWithRowID(room.getId());
			assertNotNull(testRoom);
			assertTrue(room.getName().equals(testRoom.getName()));
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			dbHandler.deleteRoom(roomId[i]);
		}
	}
}
