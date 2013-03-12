package cs428.mobiletaandroidapp.classrecordstest;

import android.test.ActivityInstrumentationTestCase2;
import cs428.mobiletaandroidapp.classactivity.ClassRecords;
import cs428.mobiletaandroidapp.classactivity.ClassRecordsDatabaseHandler;
import cs428.mobiletaandroidapp.student.ViewStudentListActivity;

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

	public void testAddRecord() {
		ClassRecords testRecord;
		int recordCount = dbHandler.getRecordsCount();
		int[] recordId = new int[3];

		for (int i = 0; i < 3; i++) {
			ClassRecords record = new ClassRecords();

			recordId[i] = dbHandler.addRecord(record);
			assertFalse("Returned Row ID should not be -1", recordId[i] == -1);

			testRecord = dbHandler.getRecord(recordId[i]);
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
	
	public void testUpdateStudent() {
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
}
