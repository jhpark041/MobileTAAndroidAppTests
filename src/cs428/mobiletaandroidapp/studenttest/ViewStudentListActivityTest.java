package cs428.mobiletaandroidapp.studenttest;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.ListView;
import cs428.mobiletaandroidapp.student.Student;
import cs428.mobiletaandroidapp.student.StudentDatabaseHandler;
import cs428.mobiletaandroidapp.student.ViewStudentListActivity;

//Test cases to be added:
	// 1) navigate the list up and down (done)
	// 2) click the attendance buttons (yet)
	// 3) click the item (done)
	// 4) get student attendance of a specific date (yet)
	// 5) add a student from the list activity (done: @AddStudentActivityTest)
/**
 * 
 * Test Cases for ViewStudentListActivity
 * 
 * @author Junhyun Park
 *
 */
public class ViewStudentListActivityTest extends
		ActivityInstrumentationTestCase2<ViewStudentListActivity> {

	private ViewStudentListActivity viewStudentListActivity;
	//private ListView stdtListView; // List view
	private StudentDatabaseHandler dbHandler;
	private int[] studentId;

	public static final int TEST_STUDENTS_NUM = 10;
	public static final int INITIAL_POSITION = 0;
	public static final int TEST_POSITION = 5;

	public ViewStudentListActivityTest() {
		super(ViewStudentListActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		viewStudentListActivity = getActivity();

		//stdtListView = (ListView) viewStudentListActivity.findViewById(cs428.mobiletaandroidapp.R.id.lvStudentList);

		dbHandler = new StudentDatabaseHandler(viewStudentListActivity);
		studentId = new int[TEST_STUDENTS_NUM];

		for (int i = 0; i < TEST_STUDENTS_NUM; i++) {
			Student student = new Student("FName" + i, "LName" + i, "TS" + i, "ID" + i);
			studentId[i] = dbHandler.addStudent(student);
		}
		
		Intent studentListIntent = new Intent(viewStudentListActivity, ViewStudentListActivity.class);
		viewStudentListActivity.startActivity(studentListIntent);
	}
	
	@Override
	protected void tearDown() throws Exception {
		for (int i = 0; i < TEST_STUDENTS_NUM; i++)
			dbHandler.deleteStudent(studentId[i]);

		super.tearDown();
	}

	public void testListUI() {
		for (int i = 1; i <= TEST_STUDENTS_NUM; i++) {
			this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		} // navigate down

		for (int i = 1; i <= TEST_STUDENTS_NUM; i++) {
			this.sendKeys(KeyEvent.KEYCODE_DPAD_UP);
		} // navigate up

		for (int i = 1; i <= TEST_POSITION; i++) {
			this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		} // navigate to desired position
		
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		this.sendKeys(KeyEvent.KEYCODE_BACK);
		this.sendKeys(KeyEvent.KEYCODE_BACK);

		//int mPos = stdtListView.getSelectedItemPosition();
		//assertTrue("Navigating the list should work correctly", mPos == TEST_POSITION - 1);
		
		//Student item = (Student) stdtListView.getItemAtPosition(mPos);
		//assertTrue("Item returned by the list should be valid", item != null);
	}
	
}
