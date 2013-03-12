package cs428.mobiletaandroidapp.studenttest;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import cs428.mobiletaandroidapp.student.AddStudentActivity;
import cs428.mobiletaandroidapp.student.StudentDatabaseHandler;

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

	public void testAddStudentUI() {
		SQLiteDatabase db = dbHandler.getReadableDatabase();

		String NAME_FIELD = "N A M E TAB ";
		String NICKNAME_FIELD = "N I C K N A M E TAB ";
		String STUDENTID_FIELD = "I D 1 1 2 6 TAB ";

		Cursor cursor = db.query("students", null,
				"name =? AND nickname =? AND student_id =?", new String[] {
						"name", "nickname", "id1126" }, null, null, null, null);

		int beforeStdtCount = cursor.getCount();

		sendKeys(NAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER");

		cursor = db.query("students", null,
				"name =? AND nickname =? AND student_id =?", new String[] {
						"name", "nickname", "id1126" }, null, null, null, null);

		int afterStdtCount = cursor.getCount();

		assertTrue(
				"A student with given information should be found on the Database",
				afterStdtCount == beforeStdtCount + 1);

		db.delete("students", "name =? AND nickname =? AND student_id =?",
				new String[] { "name", "nickname", "id1126" });
	}

	public void testAddWithBlankFieldsUI() {
		SQLiteDatabase db = dbHandler.getReadableDatabase();

		String NAME_FIELD = "TAB ";
		String NICKNAME_FIELD = "TAB ";
		String STUDENTID_FIELD = "TAB ";

		int beforeStdtCount = dbHandler.getStudentsCount();
		sendKeys(NAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER");
		int afterStdtCount = dbHandler.getStudentsCount();

		assertTrue("Attempting to add blank student should be ignored",
				afterStdtCount == beforeStdtCount);

		NAME_FIELD = "TAB TAB N A M E TAB ";

		beforeStdtCount = dbHandler.getStudentsCount();
		sendKeys(NAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER");
		afterStdtCount = dbHandler.getStudentsCount();

		assertTrue("Attempting to add bland student should be ignored",
				afterStdtCount == beforeStdtCount);

		NAME_FIELD = "TAB TAB TAB ";
		STUDENTID_FIELD = "I D 1 1 2 6 TAB ";

		beforeStdtCount = dbHandler.getStudentsCount();
		sendKeys(NAME_FIELD + NICKNAME_FIELD + STUDENTID_FIELD + "ENTER");
		afterStdtCount = dbHandler.getStudentsCount();

		assertTrue(
				"Since Name and ID are provided it should be addd on Database",
				afterStdtCount == beforeStdtCount + 1);

		db.delete("students", "name =? AND student_id =?", new String[] {
				"name", "id1126" });
	}
}
