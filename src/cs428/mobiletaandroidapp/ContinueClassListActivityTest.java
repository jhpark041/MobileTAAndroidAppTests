package cs428.mobiletaandroidapp;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.ListView;

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
public class ContinueClassListActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mainActivity;
	private ClassDbHelper dbHandler;
	private int[] classId;

	public static final int TEST_CLASSES_NUM = 10;
	public static final int INITIAL_POSITION = 0;
	public static final int TEST_POSITION = 5;

	public ContinueClassListActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mainActivity = getActivity();

		dbHandler = new ClassDbHelper(mainActivity);
		classId = new int[TEST_CLASSES_NUM];

		for (int i = 0; i < TEST_CLASSES_NUM; i++) {
			MyClass mClass = new MyClass("CName" + i, "RName" + i, "SName" + i);
			classId[i] = dbHandler.insert(mClass);
		}
		
		Intent i = new Intent(mainActivity, ContinueClassListActivity.class);
		mainActivity.startActivity(i);
	}

	@Override
	protected void tearDown() throws Exception {
		for (int i = 0; i < TEST_CLASSES_NUM; i++)
			dbHandler.delete(classId[i]);

		super.tearDown();
	}

	public void testListUI() {
		for (int i = 1; i <= TEST_CLASSES_NUM; i++) {
			this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		} // navigate down

		for (int i = 1; i <= TEST_CLASSES_NUM; i++) {
			this.sendKeys(KeyEvent.KEYCODE_DPAD_UP);
		} // navigate up

		for (int i = 1; i <= TEST_POSITION; i++) {
			this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
		} // navigate to desired position
		
		this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		this.sendKeys(KeyEvent.KEYCODE_BACK);
		this.sendKeys(KeyEvent.KEYCODE_BACK);

		//int mPos = listView.getSelectedItemPosition();
		//assertTrue("Navigating the list should work correctly", mPos == TEST_POSITION - 1);
		
		//MyClass item = (MyClass) listView.getItemAtPosition(mPos);
		//assertTrue("Item returned by the list should be valid", item != null);
	}
	
}
