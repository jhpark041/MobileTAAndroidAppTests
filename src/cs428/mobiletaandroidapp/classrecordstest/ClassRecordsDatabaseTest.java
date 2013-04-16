package cs428.mobiletaandroidapp.classrecordstest;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import cs428.mobiletaandroidapp.classactivity.ClassRecords;
import cs428.mobiletaandroidapp.classactivity.ClassRecordsDatabaseHandler;
import cs428.mobiletaandroidapp.student.ViewStudentListActivity;

/**
 * 
 * Test Cases for ClassRecordsDatabaseHandler
 * 
 * @author Junhyun Park
 *
 */
public class ClassRecordsDatabaseTest extends
	ActivityInstrumentationTestCase2<ViewStudentListActivity>  {

	private ClassRecordsDatabaseHandler dbHandler;
	private ViewStudentListActivity viewStudentListActivity;

	public ClassRecordsDatabaseTest() {
		super(ViewStudentListActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);

		viewStudentListActivity = getActivity();
		dbHandler = new ClassRecordsDatabaseHandler(viewStudentListActivity);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Tests adding function of the ClassRecords Database.
	 * 
	 * possible cases: add invalid record
	 */
	public void testAddRecord() {
		// remember current records count.
		int recordCount = dbHandler.getRecordsCount();
		// it will add 3 dummy records.
		int[] recordId = new int[3];

		for (int i = 0; i < 3; i++) {
			ClassRecords record = new ClassRecords();

			recordId[i] = dbHandler.addRecord(record);
			assertFalse("Returned Row ID should not be -1", recordId[i] == -1);

			ClassRecords testRecord = dbHandler.getRecord(recordId[i]);
			assertTrue(
					"Confirm that this speific record is added to the Database",
					record.equals(testRecord));

			assertTrue("Record Count should be incremented by 1",
					dbHandler.getRecordsCount() == recordCount + 1);
			recordCount++;
		}

		for (int i = 0; i < 3; i++)
			dbHandler.deleteRecord(recordId[i]);
	}
	
	public void testGetRecord() {
		// get Record by ID.
		ClassRecords record = new ClassRecords();
		int recordId = dbHandler.addRecord(record);
		
		assertTrue("returned id should match the remembered one", recordId == dbHandler.getRecord(recordId).getId());
		
		dbHandler.deleteRecord(recordId);
		
		// get Record by date and student ID.
		int date = 20131126;
		int id = 379;
		
		record = new ClassRecords();
		record.setDate(date);
		record.setStudentId(id);
		
		recordId = dbHandler.addRecord(record);
		
		assertTrue("returned id should match the remembered one", recordId == dbHandler.getRecord(date, id).getId());
		
		dbHandler.deleteRecord(recordId);
	}
	
	public void testUpdateClassRecord() {
		// add a dummy record to be tested
		ClassRecords record = new ClassRecords();
		ClassRecords testRecord;
		dbHandler.addRecord(record);

		testRecord = dbHandler.getRecord(record.getId());
		assertTrue(
				"Confirm that this speific record is added to the Database",
				record.equals(testRecord));

		record.setDate(12345678);
		record.setClassId(1);
		record.setStudentId(2);
		record.setParticipation("part");

		dbHandler.updateRecord(record);
		testRecord = dbHandler.getRecord(record.getId());
		assertTrue("Confirm that all the information is correctly updated",
				record.equals(testRecord));

		dbHandler.deleteRecord(record.getId());
	}
	
	/**
	 * Check if it deletes the specified record.
	 * It does not affect anything if it tries to delete invalid record.
	 */ 
	public void testDeleteClassRecords() {
		ClassRecords record = new ClassRecords();
		ClassRecords testRecord;

		assertTrue("Confirm that Test Object is added",
				dbHandler.addRecord(record) != -1);

		testRecord = dbHandler.getRecord(record.getId());
		assertTrue("Before deletion, one can get record object from Database",
				testRecord != null);

		dbHandler.deleteRecord(record.getId());

		testRecord = dbHandler.getRecord(record.getId());
		assertTrue(
				"After deletion, one cannot get record object from Database",
				testRecord == null);

		assertTrue(
				"Trying to delete record with unvalid id should affect no row",
				dbHandler.deleteRecord(-1) == 0);
	}
	

	public void testGetAllRecords() {
		int recordCount = dbHandler.getRecordsCount();
		int[] recordId = new int[3];

		// add few more test dummies
		for (int i = 0; i < 3; i++) {
			ClassRecords record = new ClassRecords();

			recordId[i] = dbHandler.addRecord(record);
			recordCount++;
		}

		assertTrue("See if student count is updated well",
				dbHandler.getRecordsCount() == recordCount);

		ArrayList<ClassRecords> recordList = (ArrayList<ClassRecords>) dbHandler
				.getAllRecords();

		SQLiteDatabase db = dbHandler.getWritableDatabase();
		Cursor cursor = db.query("classRecords", null, null, null, null, null,
				null);

		assertTrue("List should not be empty and cursor should be returned",
				cursor.moveToFirst());

		// compare and verify that every single records are loaded correctly
		for (int i = 0; i < recordCount; i++) {
			ClassRecords record = recordList.get(i);

			ClassRecords testRecord = dbHandler.getRecord(record.getId());
			assertTrue(
					"Confirm that the record with the given ID is correctly returned",
					testRecord != null);

			assertTrue(
					"Confirm that this speific record is added to the Database",
					record.equals(testRecord));
		}
	}
}
