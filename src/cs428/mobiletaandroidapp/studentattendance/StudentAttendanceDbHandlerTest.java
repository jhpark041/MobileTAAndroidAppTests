package cs428.mobiletaandroidapp.studentattendance;

import java.util.ArrayList;
import java.util.Random;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * 
 * Test Cases for StudentAttendanceDatabaseHandler
 * 
 * @author Junhyun Park
 *
 */
public class StudentAttendanceDbHandlerTest extends AndroidTestCase {

	private static final String TEST_FILE_PREFIX = "test_";
	
	private static final int RANDOM_SEED = 19580427;
	
	private static final int NUM_OF_DUMMIES = 3;
	
	private StudentAttendanceDbHandler dbHandler;
	
	private Random generator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
		
		dbHandler = new StudentAttendanceDbHandler(context);
		generator = new Random(RANDOM_SEED);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		dbHandler.closeStudentAttendanceDB();
		dbHandler = null;
	}
	
	public void testPreConditions() {
	    assertNotNull(dbHandler);
	}

	public void testInsertStudentAttendanceAndGetStudentAttendanceWithRowID() {
		int[] studentAttendanceId = new int[NUM_OF_DUMMIES];

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			int testParticipation = generator.nextInt();
			int testStatus= generator.nextInt();
			
			StudentAttendance studentAttendance = new StudentAttendance(testParticipation, testStatus);

			studentAttendanceId[i] = dbHandler.insertStudentAttendance(studentAttendance);
			assertFalse(studentAttendanceId[i] == -1);

			StudentAttendance testStudentAttendance = dbHandler.getStudentAttendanceWithRowID(studentAttendanceId[i]);
			assertTrue(studentAttendance.getParticipation() == testStudentAttendance.getParticipation());
			assertTrue(studentAttendance.getStatus() == testStudentAttendance.getStatus());
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++)
			dbHandler.deleteStudentAttendance(studentAttendanceId[i]);	// not sure where to put it
	}
	
	public void testInsertStudentAttendanceAndGetStudentAttendanceWithStudentIDAndRecordID() {
		int[] studentAttendanceId = new int[NUM_OF_DUMMIES];

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			int testParticipation = generator.nextInt();
			int testStatus= generator.nextInt();
			int testStudentID = generator.nextInt();
			int testRecordID= generator.nextInt();
			
			StudentAttendance studentAttendance = new StudentAttendance(testParticipation, testStatus, testStudentID, testRecordID);

			studentAttendanceId[i] = dbHandler.insertStudentAttendance(studentAttendance);
			assertFalse(studentAttendanceId[i] == -1);

			StudentAttendance testStudentAttendance = dbHandler.getStudentAttendance(testStudentID, testRecordID);
			assertTrue(studentAttendance.getParticipation() == testStudentAttendance.getParticipation());
			assertTrue(studentAttendance.getStatus() == testStudentAttendance.getStatus());
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++)
			dbHandler.deleteStudentAttendance(studentAttendanceId[i]);	// not sure where to put it
	}
	
	public void testUpdateStudentAttendance() {
		int testParticipation = generator.nextInt();
		int testStatus = generator.nextInt();
		
		StudentAttendance studentAttendance = new StudentAttendance(testParticipation, testStatus);
		
		int rowID = dbHandler.insertStudentAttendance(studentAttendance);
		StudentAttendance testStudentAttendance = dbHandler.getStudentAttendanceWithRowID(rowID);
		assertTrue(studentAttendance.getParticipation() == testStudentAttendance.getParticipation());
		assertTrue(studentAttendance.getStatus() == testStudentAttendance.getStatus());

		studentAttendance.setId(rowID);
		studentAttendance.setParticipation(1);
		studentAttendance.setStatus(1);
		
		dbHandler.updateStudentAttendance(studentAttendance);

		testStudentAttendance = dbHandler.getStudentAttendanceWithRowID(rowID);
		assertEquals(1, testStudentAttendance.getParticipation());
		assertEquals(1, testStudentAttendance.getStatus());
	}
	
	public void testDeleteStudentAttendance() {
		int testParticipation = generator.nextInt();
		int testStatus = generator.nextInt();
		int testStudentID = generator.nextInt();
		int testRecordID = generator.nextInt();
		
		StudentAttendance studentAttendance = new StudentAttendance(testParticipation, testStatus, testStudentID, testRecordID);

		int rowID = dbHandler.insertStudentAttendance(studentAttendance);
		StudentAttendance testStudentAttendance = dbHandler.getStudentAttendanceWithRowID(rowID);
		assertNotNull(testStudentAttendance);
		
		testStudentAttendance = dbHandler.getStudentAttendance(testStudentID, testRecordID);
		assertNotNull(testStudentAttendance);

		dbHandler.deleteStudentAttendance(testStudentID);

		testStudentAttendance = dbHandler.getStudentAttendanceWithRowID(rowID);
		assertNull(testStudentAttendance);
		
		testStudentAttendance = dbHandler.getStudentAttendance(testStudentID, testRecordID);
		assertNull(testStudentAttendance);
	}
	
	private int getUniqueStudentID() {
		int count;
		int StudentID = generator.nextInt();
		do {
			count = dbHandler.getAllStudentAttendances(StudentID).size();
			if(count != 0)
				StudentID = generator.nextInt();
		} while (count != 0);
		
		return StudentID;
	}
	
	public void testGetAllStudentAttendances() {
		ArrayList<StudentAttendance> studentAttendanceList;
		int[] studentAttendanceId = new int[NUM_OF_DUMMIES];
		int testStudentID = getUniqueStudentID();

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			int testParticipation = generator.nextInt();
			int testStatus= generator.nextInt();
			
			StudentAttendance studentAttendance = new StudentAttendance(testParticipation, testStatus);
			studentAttendance.setStudentID(testStudentID);

			studentAttendanceId[i] = dbHandler.insertStudentAttendance(studentAttendance);
		}

		studentAttendanceList = dbHandler.getAllStudentAttendances(testStudentID);
		int count = studentAttendanceList.size();
		
		assertEquals(NUM_OF_DUMMIES, count);

		for (int i = 0; i < count; i++) {
			StudentAttendance studentAttendance = studentAttendanceList.get(i);
			StudentAttendance testStudentAttendance = dbHandler.getStudentAttendanceWithRowID(studentAttendance.getId());
			assertNotNull(testStudentAttendance);
			assertTrue(studentAttendance.getParticipation() == testStudentAttendance.getParticipation());
			assertTrue(studentAttendance.getStatus() == testStudentAttendance.getStatus());
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			dbHandler.deleteStudentAttendance(studentAttendanceId[i]);
		}
	}
}
