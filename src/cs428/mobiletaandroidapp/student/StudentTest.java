package cs428.mobiletaandroidapp.student;

import java.util.Random;

import android.test.AndroidTestCase;

/**
 * 
 * Test Cases for Student.java
 * 
 * @author Junhyun Park
 *
 */
public class StudentTest extends AndroidTestCase  {

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
		String testFirstName = "first" + generator.nextInt();
		String testLastName = "last" + generator.nextInt();
		String testNickname = "nick" + generator.nextInt();
		
		Student student = new Student(testFirstName, testLastName, testNickname);
		
		assertNotNull(student);
		
		String firstName = student.getFirstName();
		String lastName = student.getLastName();
		String nickname = student.getNickname();
		
		assertTrue(testFirstName.equals(firstName));
		assertTrue(testLastName.equals(lastName));
		assertTrue(testNickname.equals(nickname));
	}
	
	public void testConstructorTwo() {
		String testFirstName = "first" + generator.nextInt();
		String testLastName = "last" + generator.nextInt();
		String testNickname = "nick" + generator.nextInt();
		int testSectionID = generator.nextInt();
		
		Student student = new Student(testFirstName, testLastName, testNickname, testSectionID);
		
		assertNotNull(student);
		
		String firstName = student.getFirstName();
		String lastName = student.getLastName();
		String nickname = student.getNickname();
		int sectionID = student.getSectionID();
		
		assertTrue(testFirstName.equals(firstName));
		assertTrue(testLastName.equals(lastName));
		assertTrue(testNickname.equals(nickname));
		assertEquals(testSectionID, sectionID);
	}
	
	public void testConstructorThree() {
		int testID = generator.nextInt();
		String testFirstName = "first" + generator.nextInt();
		String testLastName = "last" + generator.nextInt();
		String testNickname = "nick" + generator.nextInt();
		int testSectionID = generator.nextInt();
		int testSeatID = generator.nextInt();
		String testStudentID = "studentID" + generator.nextInt();
		int testGroupID = generator.nextInt();
		String testNote = "note" + generator.nextInt(); 
		
		Student student = new Student(testID, testFirstName, testLastName, testNickname, testSectionID, testSeatID, testStudentID, testGroupID, testNote);
		
		assertNotNull(student);
		
		int id = student.getId();
		String firstName = student.getFirstName();
		String lastName = student.getLastName();
		String nickname = student.getNickname();
		int sectionID = student.getSectionID();
		int seatID = student.getSeatID();
		String studentID = student.getStudentID();
		int groupID = student.getGroupID();
		String note = student.getNote();
		
		assertEquals(testID, id);
		assertTrue(testFirstName.equals(firstName));
		assertTrue(testLastName.equals(lastName));
		assertTrue(testNickname.equals(nickname));
		assertEquals(testSectionID, sectionID);
		assertEquals(testSeatID, seatID);
		assertTrue(testStudentID.equals(studentID));
		assertEquals(testGroupID, groupID);
		assertTrue(testNote.equals(note));
	}
}
