package cs428.mobiletaandroidapp.studenttest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import cs428.mobiletaandroidapp.student.Student;
import cs428.mobiletaandroidapp.student.StudentDatabaseHandler;
import cs428.mobiletaandroidapp.student.ViewStudentListActivity;

public class EditStudentActivityTest extends
		ActivityInstrumentationTestCase2<ViewStudentListActivity> {
	
	private ViewStudentListActivity editStudentActivity;
	private StudentDatabaseHandler dbHandler;
	
	private String NAME_FIELD;
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
	
	public void testEditStudent() {
		sendKeys("TAB TAB TAB TAB ENTER");
		SQLiteDatabase db = dbHandler.getReadableDatabase();

		NAME_FIELD = "0 TAB ";
		NICKNAME_FIELD = "1 TAB ";
		STUDENTID_FIELD = "2 TAB ";

		sendKeys(NAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER");

		Cursor cursor = db.query("students", null, "name =? AND nickname =? AND student_id =?",
				new String[] { "0", "1", "2" }, null, null, null, null);
		
		cursor.moveToFirst();
		int stdtId = cursor.getInt(7);
		
		NAME_FIELD = "A L I C E TAB ";
		NICKNAME_FIELD = "B O B TAB ";
		STUDENTID_FIELD = "C A R O L TAB ";
		
		sendKeys("TAB TAB TAB ENTER ENTER");
		sendKeys(NAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER ");
		
		Student student = dbHandler.getStudent(stdtId);
		assertTrue("Name A should be editted to Alice", student.getName().equals("alice"));
		assertTrue("Nickname B should be editted to Bob", student.getNickname().equals("bob"));
		assertTrue("STUDENTId C should be editted to Carol", student.getStudentId().equals("carol"));

		db.delete("students", "name =? AND nickname =? AND student_id =?",
				new String[] { "alice", "bob", "carol" } );
	}


}