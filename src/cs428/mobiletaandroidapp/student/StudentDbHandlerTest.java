package cs428.mobiletaandroidapp.student;

import java.util.ArrayList;
import java.util.Random;

import cs428.mobiletaandroidapp.student.Student;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * 
 * Test Cases for StudentDatabaseHandler
 * 
 * @author Junhyun Park
 *
 */
public class StudentDbHandlerTest extends AndroidTestCase {

	private static final String TEST_FILE_PREFIX = "test_";
	
	private static final int RANDOM_SEED = 19580427;
	
	private static final int NUM_OF_DUMMIES = 3;
	
	private StudentDbHandler dbHandler;
	
	private Random generator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
		
		dbHandler = new StudentDbHandler(context);
		generator = new Random(RANDOM_SEED);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		dbHandler.closeStudentDB();
		dbHandler = null;
	}
	
	public void testPreConditions() {
	    assertNotNull(dbHandler);
	}

	public void testInsertStudentAndGetStudentWithRowID() {
		int[] studentId = new int[NUM_OF_DUMMIES];

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			String testFirstName = "first" + generator.nextInt();
			String testLastName = "last" + generator.nextInt();
			String testNickname = "nick" + generator.nextInt();
			
			Student student = new Student(testFirstName, testLastName, testNickname);

			studentId[i] = dbHandler.insertStudent(student);
			assertFalse(studentId[i] == -1);

			Student testStudent = dbHandler.getStudentWithRowID(studentId[i]);
			assertTrue(testStudent.getFirstName().equals(testFirstName));
			assertTrue(testStudent.getLastName().equals(testLastName));
			assertTrue(testStudent.getNickname().equals(testNickname));
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++)
			dbHandler.deleteStudent(studentId[i]);	// not sure where to put it
	}
	
	public void testUpdateStudent() {
		String testFirstName = "first" + generator.nextInt();
		String testLastName = "last" + generator.nextInt();
		String testNickname = "nick" + generator.nextInt();
		
		Student student = new Student(testFirstName, testLastName, testNickname);
		
		int rowID = dbHandler.insertStudent(student);
		Student testStudent = dbHandler.getStudentWithRowID(rowID);
		assertTrue(testStudent.getFirstName().equals(testFirstName));
		assertTrue(testStudent.getLastName().equals(testLastName));
		assertTrue(testStudent.getNickname().equals(testNickname));

		student.setId(rowID);
		student.setFirstName("updateFristName");
		student.setLastName("updaetLastName");
		student.setNickname("updateNickname");
		
		dbHandler.updateStudent(student);

		testStudent = dbHandler.getStudentWithRowID(rowID);
		assertTrue(testStudent.getFirstName().equals("updateFristName"));
		assertTrue(testStudent.getLastName().equals("updaetLastName"));
		assertTrue(testStudent.getNickname().equals("updateNickname"));
	}
	
	public void testDeleteStudent() {
		String testFirstName = "first" + generator.nextInt();
		String testLastName = "last" + generator.nextInt();
		String testNickname = "nick" + generator.nextInt();
		
		Student student = new Student(testFirstName, testLastName, testNickname);
		
		int rowID = dbHandler.insertStudent(student);
		Student testStudent = dbHandler.getStudentWithRowID(rowID);
		assertNotNull(testStudent);

		dbHandler.deleteStudent(rowID);

		testStudent = dbHandler.getStudentWithRowID(rowID);
		assertNull(testStudent);
	}
	
	private int getUniqueSectionID() {
		int count;
		int SectionID = generator.nextInt();
		do {
			count = dbHandler.getStudentsWithSectionID(SectionID).size();
			if(count != 0)
				SectionID = generator.nextInt();
		} while (count != 0);
		
		return SectionID;
	}
	
	public void testDeleteStudentWithSectionID() {
		int[] studentId = new int[NUM_OF_DUMMIES];
		int testSectionID = getUniqueSectionID();
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			String testFirstName = "first" + generator.nextInt();
			String testLastName = "last" + generator.nextInt();
			String testNickname = "nick" + generator.nextInt();
			
			Student student = new Student(testFirstName, testLastName, testNickname, testSectionID);
			
			int rowID = dbHandler.insertStudent(student);
			Student testStudent = dbHandler.getStudentWithRowID(rowID);
			assertNotNull(testStudent);
		}
		
		int studentsCount = dbHandler.getStudentsWithSectionID(testSectionID).size();
		assertEquals(NUM_OF_DUMMIES, studentsCount);

		dbHandler.deleteStudentWithSectionID(testSectionID);
		
		studentsCount = dbHandler.getStudentsWithSectionID(testSectionID).size();
		assertEquals(0, studentsCount);

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			Student testStudent = dbHandler.getStudentWithRowID(studentId[i]);
			assertNull(testStudent);
		}
	}

	public void testGetAllStudents() {
		ArrayList<Student> studentList;
		int[] studentId = new int[NUM_OF_DUMMIES];
		int beforeCount = dbHandler.getAllStudents().size();

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			String testFirstName = "first" + generator.nextInt();
			String testLastName = "last" + generator.nextInt();
			String testNickname = "nick" + generator.nextInt();
			
			Student student = new Student(testFirstName, testLastName, testNickname);

			studentId[i] = dbHandler.insertStudent(student);
		}

		studentList = dbHandler.getAllStudents();
		int afterCount = studentList.size();
		
		assertEquals(beforeCount + NUM_OF_DUMMIES, afterCount);

		for (int i = 0; i < afterCount; i++) {
			Student student = studentList.get(i);
			Student testStudent = dbHandler.getStudentWithRowID(student.getId());
			assertNotNull(testStudent);
			assertTrue(testStudent.getFirstName().equals(student.getFirstName()));
			assertTrue(testStudent.getLastName().equals(student.getLastName()));
			assertTrue(testStudent.getNickname().equals(student.getNickname()));
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			dbHandler.deleteStudent(studentId[i]);
		}
	}
}
