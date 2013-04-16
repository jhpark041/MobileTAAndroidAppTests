package cs428.mobiletaandroidapp;

import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

/**
 * 
 * Test Cases for ClassRecordsDatabaseHandler
 * 
 * @author Junhyun Park
 *
 */
public class MainActivityTest extends
	ActivityInstrumentationTestCase2<MainActivity>  {

	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);

		getActivity();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Simply checks if main button does not break and leads to the next activities.
	 */
	public void testButtons() {
		for(int i = 0; i < 3; i++) {
			sendKeys(KeyEvent.KEYCODE_ENTER);
			sendKeys(KeyEvent.KEYCODE_BACK);
			sendKeys(KeyEvent.KEYCODE_TAB);
		}
	}
}
