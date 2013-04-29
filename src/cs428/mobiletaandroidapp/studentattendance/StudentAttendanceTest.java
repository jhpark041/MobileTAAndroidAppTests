package cs428.mobiletaandroidapp.studentattendance;

import java.util.Random;

import android.test.AndroidTestCase;

/**
 * 
 * Test Cases for StudentAttendance.java
 * 
 * @author Junhyun Park
 *
 */
public class StudentAttendanceTest extends AndroidTestCase  {

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
		int testParticipation = generator.nextInt();
		int testStatus= generator.nextInt();
		
		StudentAttendance studentAttendance = new StudentAttendance(testParticipation, testStatus);
		
		assertNotNull(studentAttendance);
		
		int participation = studentAttendance.getParticipation();
		int status = studentAttendance.getStatus();
		
		assertEquals(testParticipation, participation);
		assertEquals(testStatus, status);
	}
	
	public void testConstructorTwo() {
		int testParticipation = generator.nextInt();
		int testStatus= generator.nextInt();
		int testStudentID = generator.nextInt();
		int testRecordID= generator.nextInt();
		
		StudentAttendance studentAttendance = new StudentAttendance(testParticipation, testStatus, testStudentID, testRecordID);
		
		assertNotNull(studentAttendance);
		
		int participation = studentAttendance.getParticipation();
		int status = studentAttendance.getStatus();
		int studentID = studentAttendance.getStudentID();
		int recordID = studentAttendance.getRecordID();
		
		assertEquals(testParticipation, participation);
		assertEquals(testStatus, status);
		assertEquals(testStudentID, studentID);
		assertEquals(testRecordID, recordID);
	}
	
	public void testConstructorThree() {
		int testID = generator.nextInt();
		int testParticipation = generator.nextInt();
		int testStatus= generator.nextInt();
		int testStudentID = generator.nextInt();
		int testRecordID= generator.nextInt();
		
		StudentAttendance studentAttendance = new StudentAttendance(testID, testParticipation, testStatus, testStudentID, testRecordID);
		
		assertNotNull(studentAttendance);
		
		int id = studentAttendance.getId();
		int participation = studentAttendance.getParticipation();
		int status = studentAttendance.getStatus();
		int studentID = studentAttendance.getStudentID();
		int recordID = studentAttendance.getRecordID();
		
		assertEquals(testID, id);
		assertEquals(testParticipation, participation);
		assertEquals(testStatus, status);
		assertEquals(testStudentID, studentID);
		assertEquals(testRecordID, recordID);
	}
}
