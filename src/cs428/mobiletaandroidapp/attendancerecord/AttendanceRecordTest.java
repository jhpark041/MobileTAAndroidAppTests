package cs428.mobiletaandroidapp.attendancerecord;

import java.util.Date;
import java.util.Random;

import android.test.AndroidTestCase;

/**
 * 
 * Test Cases for AttendanceRecord.java
 * 
 * @author Junhyun Park
 *
 */
public class AttendanceRecordTest extends AndroidTestCase  {

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
		Date testDate = new Date();
		int testSectionID = generator.nextInt();
		
		AttendanceRecord attendanceRecord = new AttendanceRecord(testName, testDate, testSectionID);
		
		assertNotNull(attendanceRecord);
		
		String name = attendanceRecord.getName();
		Date date = attendanceRecord.getDate();
		int sectionID = attendanceRecord.getSectionID();
		
		assertTrue(name.equals(testName));
		assertTrue(date.equals(testDate));
		assertEquals(testSectionID, sectionID);
	}
	
	public void testConstructorTwo() {
		String testName = "name" + generator.nextInt();
		String testDateString = "1991-11-26";
		int testSectionID = generator.nextInt();
		
		AttendanceRecord attendanceRecord = new AttendanceRecord(testName, testDateString, testSectionID);
		
		assertNotNull(attendanceRecord);
		
		String name = attendanceRecord.getName();
		String dateString = attendanceRecord.getDateString();
		int sectionID = attendanceRecord.getSectionID();
		
		assertTrue(name.equals(testName));
		assertTrue(dateString.equals(testDateString));
		assertEquals(testSectionID, sectionID);
	}
	
	public void testConstructorThree() {
		int testID = generator.nextInt();
		String testDateString = "1991-11-26";
		String testName = "name" + generator.nextInt();
		int testSectionID = generator.nextInt();
		String testNote = "note" + generator.nextInt();
		
		AttendanceRecord attendanceRecord = new AttendanceRecord(testID, testName, testDateString, testSectionID, testNote);
		
		assertNotNull(attendanceRecord);
		
		int id = attendanceRecord.getId();
		String dateString = attendanceRecord.getDateString();
		String name = attendanceRecord.getName();
		int sectionID = attendanceRecord.getSectionID();
		// String note = attendanceRecord.getNote(); 
		
		assertEquals(testID, id);
		assertTrue(dateString.equals(testDateString));
		assertTrue(name.equals(testName));
		assertEquals(testSectionID, sectionID);
		// assertTrue(note.equals(testNote));
	}
}
