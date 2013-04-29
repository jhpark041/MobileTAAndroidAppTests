package cs428.mobiletaandroidapp.attendancerecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * 
 * Test Cases for AttendanceRecordDatabaseHandler
 * 
 * @author Junhyun Park
 *
 */
public class AttendanceRecordDbHandlerTest extends AndroidTestCase {

	private static final String TEST_FILE_PREFIX = "test_";
	
	private static final int RANDOM_SEED = 19580427;
	
	private static final int NUM_OF_DUMMIES = 3;
	
	private AttendanceRecordDbHandler dbHandler;
	
	private Random generator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
		
		dbHandler = new AttendanceRecordDbHandler(context);
		generator = new Random(RANDOM_SEED);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		dbHandler.closeAttendanceRecordDB();
		dbHandler = null;
	}
	
	public void testPreConditions() {
	    assertNotNull(dbHandler);
	}

	public void testInsertAttendanceRecordAndGetAttendanceRecordWithRowID() {
		int[] attendanceRecordId = new int[NUM_OF_DUMMIES];

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			Date testDate = new Date();
			String testName = "name" + generator.nextInt();
			int testSectionID = generator.nextInt();
			
			AttendanceRecord attendanceRecord = new AttendanceRecord(testName, testDate, testSectionID);

			attendanceRecordId[i] = dbHandler.insertAttendanceRecord(attendanceRecord);
			assertFalse(attendanceRecordId[i] == -1);

			AttendanceRecord testAttendanceRecord = dbHandler.getAttendanceRecordWithRowID(attendanceRecordId[i]);
			// assertTrue(testDate.equals(testAttendanceRecord.getDate()));
			assertTrue(testName.equals(testAttendanceRecord.getName()));
			assertEquals(testSectionID, testAttendanceRecord.getSectionID());
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++)
			dbHandler.deleteAttendanceRecord(attendanceRecordId[i]);	// not sure where to put it
	}
	
	public void testDeleteAttendanceRecordAndExists() {
		Date testDate = new Date();
		String testName = "name" + generator.nextInt();
		int testSectionID = generator.nextInt();
		
		AttendanceRecord attendanceRecord = new AttendanceRecord(testName, testDate, testSectionID);
		
		int rowID = dbHandler.insertAttendanceRecord(attendanceRecord);
		AttendanceRecord testAttendanceRecord = dbHandler.getAttendanceRecordWithRowID(rowID);
		assertNotNull(testAttendanceRecord);
		

		dbHandler.deleteAttendanceRecord(testSectionID);

		testAttendanceRecord = dbHandler.getAttendanceRecordWithRowID(rowID);
		assertNull(testAttendanceRecord);
	}
	
	private int getUniqueSectionID() {
		int count;
		int sectionID = generator.nextInt();
		do {
			count = dbHandler.getAllAttendanceRecordsInSection(sectionID).size();
			if(count != 0)
				sectionID = generator.nextInt();
		} while (count != 0);
		
		return sectionID;
	}
	
	public void testGetAllAttendanceRecords() {
		ArrayList<AttendanceRecord> attendanceRecordList;
		int[] attendanceRecordId = new int[NUM_OF_DUMMIES];
		int testSectionID = getUniqueSectionID();

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			Date testDate = new Date();
			String testName = "name" + generator.nextInt();
			
			AttendanceRecord attendanceRecord = new AttendanceRecord(testName, testDate, testSectionID);

			attendanceRecordId[i] = dbHandler.insertAttendanceRecord(attendanceRecord);
		}

		attendanceRecordList = dbHandler.getAllAttendanceRecordsInSection(testSectionID);
		int count = attendanceRecordList.size();
		
		assertEquals(NUM_OF_DUMMIES, count);

		for (int i = 0; i < count; i++) {
			AttendanceRecord attendanceRecord = attendanceRecordList.get(i);
			AttendanceRecord testAttendanceRecord = dbHandler.getAttendanceRecordWithRowID(attendanceRecord.getId());
			assertNotNull(testAttendanceRecord);
			assertTrue(attendanceRecord.getDate().equals(testAttendanceRecord.getDate()));
			assertTrue(attendanceRecord.getName().equals(testAttendanceRecord.getName()));
			assertEquals(testSectionID, testAttendanceRecord.getSectionID());
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			dbHandler.deleteAttendanceRecord(attendanceRecordId[i]);
		}
	}
}
