package cs428.mobiletaandroidapp.studenttest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import cs428.mobiletaandroidapp.student.AddStudentActivity;
import cs428.mobiletaandroidapp.student.StudentDatabaseHandler;

/**
 * 
 * Test Cases for AddStudentActivity
 * 
 * @author Junhyun Park
 *
 */
public class AddStudentActivityTest extends
		ActivityInstrumentationTestCase2<AddStudentActivity> {

	private StudentDatabaseHandler dbHandler;
	private AddStudentActivity addStudentActivity;

	public AddStudentActivityTest() {
		super(AddStudentActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);

		addStudentActivity = getActivity();
		dbHandler = new StudentDatabaseHandler(addStudentActivity);
	}

	/**
	 * UI Test for AddStudentActivity: add a student
	 */
	public void testAddStudentUI() {
		SQLiteDatabase db = dbHandler.getReadableDatabase();

		// Dummy Data
		String FNAME_FIELD = "F N A M E TAB ";
		String LNAME_FIELD = "L N A M E TAB ";
		String NICKNAME_FIELD = "N I C K N A M E TAB ";
		String STUDENTID_FIELD = "I D 1 1 2 6 TAB ";

		// Check the status of DB before adding Dummy
		Cursor cursor = db.query("students", null,
				"first_name =? AND last_name =? AND nickname =? AND student_id =?", new String[] {
					"fname", "lname", "nickname", "id1126" }, null, null, null, null);

		int beforeStdtCount = cursor.getCount();

		sendKeys(FNAME_FIELD + LNAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER");

		// Check the status of DB after adding Dummy
		cursor = db.query("students", null,
				"first_name =? AND last_name =? AND nickname =? AND student_id =?", new String[] {
				"fname", "lname", "nickname", "id1126" }, null, null, null, null);

		int afterStdtCount = cursor.getCount();

		assertTrue(
				"A student with given information should be found on the Database",
				afterStdtCount == beforeStdtCount + 1);

		// Clean the Dummy data from DB
		db.delete("students", "first_name =? AND last_name =? AND nickname =? AND student_id =?",
				new String[] { "fname", "name", "nickname", "id1126" });
	}

	/**
	 * UI Test for AddStudentActivity: try to add a student provided with blank data fields
	 */
	public void testAddWithBlankFieldsUI() {
		SQLiteDatabase db = dbHandler.getReadableDatabase();

		// Dummy data is empty
		String FNAME_FIELD = "TAB ";
		String LNAME_FIELD = "TAB ";
		String NICKNAME_FIELD = "TAB ";
		String STUDENTID_FIELD = "TAB ";

		// Check the status of DB before trying to add a student with empty data
		int beforeStdtCount = dbHandler.getStudentsCount();
		sendKeys(FNAME_FIELD + LNAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER");
		int afterStdtCount = dbHandler.getStudentsCount();

		assertTrue("Attempting to add blank student should be ignored",
				afterStdtCount == beforeStdtCount);

		// Provide 'First Name'
		FNAME_FIELD = "TAB TAB F N A M E TAB ";

		// Check the status of DB after trying to add a student with only first name provided
		beforeStdtCount = dbHandler.getStudentsCount();
		sendKeys(FNAME_FIELD + LNAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER");
		afterStdtCount = dbHandler.getStudentsCount();

		// First name is not enough data
		assertTrue("Attempting to add blank student should be ignored",
				afterStdtCount == beforeStdtCount);

		// Provide 'Last Name' and 'Student ID'
		FNAME_FIELD = "TAB TAB TAB ";
		LNAME_FIELD = "L N A M E TAB ";
		STUDENTID_FIELD = "I D 1 1 2 6 TAB ";

		// Check the status of DB after trying to add a student with enough data provided.
		beforeStdtCount = dbHandler.getStudentsCount();
		sendKeys(FNAME_FIELD + LNAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER");
		afterStdtCount = dbHandler.getStudentsCount();

		assertTrue(
				"Since Name and ID are provided it should be addd on Database",
				afterStdtCount == beforeStdtCount + 1);

		// Clean dummy data from DB
		db.delete("students", "first_name =? AND last_name =? AND student_id =?", new String[] {
				"fname", "lname", "id1126" });
	}

}
