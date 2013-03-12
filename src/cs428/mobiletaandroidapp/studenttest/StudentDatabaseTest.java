package cs428.mobiletaandroidapp.studenttest;

import android.test.ActivityInstrumentationTestCase2;
import cs428.mobiletaandroidapp.student.AddStudentActivity;
import cs428.mobiletaandroidapp.student.Student;
import cs428.mobiletaandroidapp.student.StudentDatabaseHandler;

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
		Student testStudent;
		int studentCount = dbHandler.getStudentsCount();
		int[] studentId = new int[3];

		for (int i = 0; i < 3; i++) {
			Student student = new Student("TestStudent" + i, "TS" + i, "ID" + i);

			studentId[i] = dbHandler.addStudent(student);
			assertFalse("Returned Row ID should not be -1", studentId[i] == -1);

			testStudent = dbHandler.getStudent(studentId[i]);
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
		Student student = new Student("TestStudent", "TS", "ID");
		Student testStudent;
		dbHandler.addStudent(student);

		testStudent = dbHandler.getStudent(student.getId());
		assertTrue(
				"Confirm that this speific student is added to the Database",
				student.equals(testStudent));

		student.setName("ModifiedName");
		student.setNickname("MS");
		student.setStudentId("MID");

		dbHandler.updateStudent(student);
		testStudent = dbHandler.getStudent(student.getId());
		assertTrue("Confirm that all the information is correctly updated",
				student.equals(testStudent));

		dbHandler.deleteStudent(student.getId());
	}

	public void testDeleteStudent() {
		Student student = new Student("TestStudent", "TS", "ID");
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

}
