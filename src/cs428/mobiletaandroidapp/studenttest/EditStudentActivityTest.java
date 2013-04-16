package cs428.mobiletaandroidapp.studenttest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import cs428.mobiletaandroidapp.student.Student;
import cs428.mobiletaandroidapp.student.StudentDatabaseHandler;
import cs428.mobiletaandroidapp.student.ViewStudentListActivity;

/**
 * 
 * Test Cases for EditStudentActivity
 * 
 * @author Junhyun Park
 *
 */
public class EditStudentActivityTest extends
		ActivityInstrumentationTestCase2<ViewStudentListActivity> {
	
	private ViewStudentListActivity editStudentActivity;
	private StudentDatabaseHandler dbHandler;
	
	private String FNAME_FIELD;
	private String LNAME_FIELD;
	private String NICKNAME_FIELD;
	private String STUDENTID_FIELD;
	
	public EditStudentActivityTest() {
		super(ViewStudentListActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		
		editStudentActivity = getActivity();
		dbHandler = new StudentDatabaseHandler(editStudentActivity);
	}
	
	/**
	 * UI Test for EditStudentActivity: add a student and edit the data
	 */
	public void testEditStudent() {
		sendKeys("TAB TAB TAB TAB ENTER");
		SQLiteDatabase db = dbHandler.getReadableDatabase();

		// Dummy data
		FNAME_FIELD = "0 TAB ";
		LNAME_FIELD = "1 TAB ";
		NICKNAME_FIELD = "2 TAB ";
		STUDENTID_FIELD = "3 TAB ";

		// Add a student with dummy data, it should be added on the top of the student list
		sendKeys(FNAME_FIELD + LNAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER");

		Cursor cursor = db.query("students", null, "first_name =? AND last_name =? AND nickname =? AND student_id =?",
				new String[] { "0", "1", "2", "3" }, null, null, null, null);
		
		cursor.moveToFirst();
		int stdtId = cursor.getInt(0);
		
		// Dummy data will be replaced by this
		FNAME_FIELD = "A L I C E TAB ";
		LNAME_FIELD = "T H E G R E A T TAB ";
		NICKNAME_FIELD = "B O B TAB ";
		STUDENTID_FIELD = "C A R O L TAB ";
		
		// Edit data
		sendKeys("TAB TAB TAB ENTER ENTER");
		sendKeys(FNAME_FIELD + LNAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER ");
		
		// Get the specific Student from DB and compare with the expected value
		Student student = dbHandler.getStudent(stdtId);
		assertTrue("First Name 0 should be editted to Alice", student.getFirstName().equals("alice"));
		assertTrue("Last Name 1 should be editted to Thegreat", student.getLastName().equals("thegreat"));
		assertTrue("Nickname 2 should be editted to Bob", student.getNickname().equals("bob"));
		assertTrue("STUDENTId 3 should be editted to Carol", student.getStudentId().equals("carol"));

		// Clean the DB
		db.delete("students", "first_name =? AND last_name =? AND nickname =? AND student_id =?",
				new String[] { "alice", "thegreat", "bob", "carol" } );
	}

}