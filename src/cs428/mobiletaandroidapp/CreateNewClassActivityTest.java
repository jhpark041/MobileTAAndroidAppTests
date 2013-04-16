package cs428.mobiletaandroidapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

/**
 * 
 * Test Cases for StudentDatabaseHandler
 * 
 * @author Junhyun
 *
 */
public class CreateNewClassActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private ClassDbHelper dbHandler;
	private MainActivity mainActivity;

	public CreateNewClassActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);

		mainActivity = getActivity();
		dbHandler = new ClassDbHelper(mainActivity);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPreConditions() {
		assertTrue(dbHandler != null);
	}

	/**
	 * Try adding two classes from UI
	 */
	public void testCreateNewClassFromUI() {
		SQLiteDatabase db = dbHandler.getReadableDatabase();
		// Access create new class activity
		sendKeys(KeyEvent.KEYCODE_ENTER);
		
		// Check the status of DB before adding Dummy
		Cursor cursor = db.query("table_class", null,
				"name =? AND room =? AND section =?", new String[] {
				"cname", "rname", "sname" }, null, null, null, null);

		int beforeCount = cursor.getCount();

		String CNAME_FIELD = "C N A M E TAB ";
		String RNAME_FIELD = "R N A M E TAB ";
		String SNAME_FIELD = "S N A M E TAB ";
				
		sendKeys(CNAME_FIELD + RNAME_FIELD + SNAME_FIELD);
		sendKeys(KeyEvent.KEYCODE_ENTER);
		
		// Check if it is appropriately added to DB
		cursor = db.query("table_class", null,
				"name =? AND room =? AND section =?", new String[] {
				"cname", "rname", "sname" }, null, null, null, null);

		int afterCount = cursor.getCount();

		assertTrue("A class with given information should be found on the Database",
				afterCount == ++beforeCount);
		
		// Try adding one more class
		sendKeys(KeyEvent.KEYCODE_BACK);
		sendKeys(KeyEvent.KEYCODE_BACK);
		sendKeys(KeyEvent.KEYCODE_ENTER);
		
		cursor = db.query("table_class", null,
				"name =? AND room =? AND section =?", new String[] {
				"", "rname2", "sname2" }, null, null, null, null);

		beforeCount = cursor.getCount();
		
		CNAME_FIELD = "TAB ";
		RNAME_FIELD = "R N A M E 2 TAB ";
		SNAME_FIELD = "S N A M E 2 TAB ";
		
		// Provide input data with empty name field
		sendKeys(CNAME_FIELD + RNAME_FIELD + SNAME_FIELD);
		sendKeys(KeyEvent.KEYCODE_ENTER);
		
		// There should be no change
		cursor = db.query("table_class", null,
				"name =? AND room =? AND section =?", new String[] {
				"", "rname2", "sname2" }, null, null, null, null);

		afterCount = cursor.getCount();
		
		assertTrue("A class with empty name should not be added on the Database",
				afterCount == beforeCount);
		
		CNAME_FIELD = "TAB C N A M E 2 TAB ";
		RNAME_FIELD = "TAB ";
		SNAME_FIELD = "TAB ";
		
		// Provide class name
		sendKeys(CNAME_FIELD + RNAME_FIELD + SNAME_FIELD);
		sendKeys(KeyEvent.KEYCODE_ENTER);
		
		// Class should be added to DB
		cursor = db.query("table_class", null,
				"name =? AND room =? AND section =?", new String[] {
				"cname2", "rname2", "sname2" }, null, null, null, null);
		
		afterCount = cursor.getCount();
		
		assertTrue("A class should now be added on the Database",
				afterCount == ++beforeCount);
		
		for(int i = 0; i < 2; i++) {
			// Start New Day, Attendance Chart not implemented yet.
			sendKeys(KeyEvent.KEYCODE_ENTER);
			sendKeys(KeyEvent.KEYCODE_TAB);
		}
		
		// Student List.
		sendKeys(KeyEvent.KEYCODE_ENTER);
		sendKeys(KeyEvent.KEYCODE_BACK);
		sendKeys(KeyEvent.KEYCODE_TAB);
		
		// Seating chart cannot be tested on virtual device
		sendKeys(KeyEvent.KEYCODE_TAB);
		
		// Clean the test class
		sendKeys(KeyEvent.KEYCODE_ENTER);
		
		// Clean the Dummy data from DB
		db.delete("table_class", "name =? AND room =? AND section =?", new String[] {
				"cname", "rname", "sname" });
	}
}
