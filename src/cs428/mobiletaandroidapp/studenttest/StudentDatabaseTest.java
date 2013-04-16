package cs428.mobiletaandroidapp.studenttest;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import cs428.mobiletaandroidapp.student.AddStudentActivity;
import cs428.mobiletaandroidapp.student.Student;
import cs428.mobiletaandroidapp.student.StudentDatabaseHandler;

/**
 * 
 * Test Cases for StudentDatabaseHandler
 * 
 * @author Junhyun
 *
 */
public class StudentDatabaseTest extends
		ActivityInstrumentationTestCase2<AddStudentActivity> {

	private StudentDatabaseHandler dbHandler;
	private AddStudentActivity addStudentActivity;

	public StudentDatabaseTest() {
		super(AddStudentActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);

		addStudentActivity = getActivity();
		dbHandler = new StudentDatabaseHandler(addStudentActivity);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPreConditions() {
		assertTrue(dbHandler != null);
	}

	public void testAddStudent() {
		// remember current students count.
		int studentCount = dbHandler.getStudentsCount();
		// it will add 3 dummy records.
		int[] studentId = new int[3];

		for (int i = 0; i < 3; i++) {
			Student student = new Student("FName" + i, "LName" + i, "TS" + i, "ID"
					+ i);

			studentId[i] = dbHandler.addStudent(student);
			assertFalse("Returned Row ID should not be -1", studentId[i] == -1);

			Student testStudent = dbHandler.getStudent(studentId[i]);
			assertTrue(
					"Confirm that this speific student is added to the Database",
					student.equals(testStudent));

			assertTrue("Student Count should be incremented by 1",
					dbHandler.getStudentsCount() == studentCount + 1);
			studentCount++;
		}

		for (int i = 0; i < 3; i++)
			dbHandler.deleteStudent(studentId[i]);
	}

	public void testUpdateStudent() {
		// add a dummy student to be tested
		Student student = new Student("FName", "LName", "TS", "ID");
		Student testStudent;
		dbHandler.addStudent(student);

		testStudent = dbHandler.getStudent(student.getId());
		assertTrue(
				"Confirm that this speific student is added to the Database",
				student.equals(testStudent));

		student.setFirstName("mFName");
		student.setLastName("mLName");
		student.setNickname("mTS");
		student.setStudentId("mID");

		dbHandler.updateStudent(student);
		testStudent = dbHandler.getStudent(student.getId());
		assertTrue("Confirm that all the information is correctly updated",
				student.equals(testStudent));

		dbHandler.deleteStudent(student.getId());
	}

	/**
	 * Check if it deletes the specified student.
	 * It does not affect anything if it tries to delete invalid student.
	 */
	public void testDeleteStudent() {
		Student student = new Student("FName", "LName", "TS", "ID");
		Student testStudent;

		assertTrue("Confirm that Test Object is added",
				dbHandler.addStudent(student) != -1);

		testStudent = dbHandler.getStudent(student.getId());
		assertTrue("Before deletion, one can get student object from Database",
				testStudent != null);

		dbHandler.deleteStudent(student.getId());

		testStudent = dbHandler.getStudent(student.getId());
		assertTrue(
				"After deletion, one cannot get student object from Database",
				testStudent == null);

		assertTrue(
				"Trying to delete student with unvalid id should affect no row",
				dbHandler.deleteStudent(-1) == 0);
	}

	public void testGetAllStudents() {
		int studentCount = dbHandler.getStudentsCount();
		int[] studentId = new int[3];

		// add few more test dummies
		for (int i = 0; i < 3; i++) {
			Student student = new Student("FName" + i, "LName", "TS" + i, "ID"
					+ i);

			studentId[i] = dbHandler.addStudent(student);
			studentCount++;
		}

		assertTrue("See if student count is updated well",
				dbHandler.getStudentsCount() == studentCount);

		ArrayList<Student> stdtList = (ArrayList<Student>) dbHandler
				.getAllStudents();

		SQLiteDatabase db = dbHandler.getWritableDatabase();
		Cursor cursor = db.query("students", null, null, null, null, null,
				"last_name ASC");

		assertTrue("List should not be empty and cursor should be returned",
				cursor.moveToFirst());

		// compare and verify that every single students are loaded correctly
		for (int i = 0; i < studentCount; i++) {
			Student student = stdtList.get(i);

			Student testStudent = dbHandler.getStudent(student.getId());
			assertTrue(
					"Confirm that the student with the given ID is correctly returned",
					testStudent != null);

			assertTrue(
					"Confirm that this speific student is added to the Database",
					student.equals(testStudent));
		}
	}
}
