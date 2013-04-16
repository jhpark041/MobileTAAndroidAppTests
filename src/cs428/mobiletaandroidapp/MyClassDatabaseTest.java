package cs428.mobiletaandroidapp;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;

/**
 * 
 * Test Cases for MyClassDatabaseHandler
 * 
 * @author Junhyun Park
 *
 */
public class MyClassDatabaseTest extends
	ActivityInstrumentationTestCase2<ContinueClassListActivity>  {

	private ClassDbHelper dbHandler;
	private ContinueClassListActivity continueClassListActivity;

	public MyClassDatabaseTest() {
		super(ContinueClassListActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);

		continueClassListActivity = getActivity();
		dbHandler = new ClassDbHelper(continueClassListActivity);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Tests adding function of the MyClass Database.
	 * 
	 * possible cases: add invalid record
	 */
	public void testAddClass() {
		// it will add 3 dummy records.
		int[] classId = new int[3];

		for (int i = 0; i < 3; i++) {
			MyClass mClass = new MyClass("CNAME", "RNAME", "SNAME");

			classId[i] = dbHandler.insert(mClass);
			assertFalse("Returned Row ID should not be -1", classId[i] == -1);

			MyClass testClass = dbHandler.getClass(classId[i]);
			 assertTrue("Confirm that this speific record is added to the Database", mClass.getName().equals(testClass.getName()));
			 assertTrue("Confirm that this speific record is added to the Database", mClass.getRoom().equals(testClass.getRoom()));
			 assertTrue("Confirm that this speific record is added to the Database", mClass.getSection().equals(testClass.getSection()));

			// assertTrue("Class count should be incremented by 1", dbHandler.getRecordsCount() == recordCount + 1);
			// recordCount++;
		}

		for (int i = 0; i < 3; i++)
			dbHandler.delete(classId[i]);
	}
	
	public void testGetClass() {
		// get Record by ID.
		MyClass mClass = new MyClass("CNAME","RNAME","SNAME");
		int classId = dbHandler.insert(mClass);
		
		assertTrue("returned id should match the remembered one", classId == dbHandler.getClass(classId).getRowID());
		
		dbHandler.delete(classId);
	}
	
	/**
	 * Check if it deletes the specified record.
	 * It does not affect anything if it tries to delete invalid record.
	 */ 
	public void testDeleteClass() {
		MyClass mClass = new MyClass("CNAME","RNAME","SNAME");
		MyClass testRecord;
		int rowID = dbHandler.insert(mClass);
		
		assertTrue("Confirm that Test Object is added",
				rowID != -1);

		testRecord = dbHandler.getClass(rowID);
		assertTrue("Before deletion, one can get record object from Database",
				testRecord != null);

		dbHandler.delete(rowID);
/*
		testRecord = dbHandler.getClass(rowID);
		assertTrue(
				"After deletion, one cannot get record object from Database",
				testRecord == null);
*/
	}
	

	public void testGetAllClasses() {
		int[] classId = new int[3];

		// add few more test dummies
		for (int i = 0; i < 3; i++) {
			MyClass mClass = new MyClass("CNAME","RNAME","SNAME");

			classId[i] = dbHandler.insert(mClass);
		}

		ArrayList<MyClass> cList = (ArrayList<MyClass>) dbHandler
				.getAllClasses();

		SQLiteDatabase db = dbHandler.getWritableDatabase();
		Cursor cursor = db.query("table_class", null, null, null, null, null,
				null);

		assertTrue("List should not be empty and cursor should be returned",
				cursor.moveToFirst());

		// compare and verify that every single records are loaded correctly
		for (int i = 0; i < cursor.getCount(); i++) {
			MyClass mClass = cList.get(i);

			MyClass testClass = dbHandler.getClass(mClass.getRowID());
			assertTrue(
					"Confirm that the record with the given ID is correctly returned",
					testClass != null);

			assertTrue(
					"Confirm that this speific record is added to the Database",
					mClass.getName().equals(testClass.getName()) && mClass.getRoom().equals(testClass.getRoom())
					&& mClass.getSection().equals(testClass.getSection()));
		}
	}
}
