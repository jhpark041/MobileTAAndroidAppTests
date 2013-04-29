package cs428.mobiletaandroidapp.section;

import android.test.ActivityInstrumentationTestCase2;

/**
 * 
 * Test Cases for StudentDatabaseHandler
 * 
 * @author Junhyun
 *
 */
public class ViewDatesActivityTest extends
		ActivityInstrumentationTestCase2<ViewSectionListActivity> {

	private SectionDbHandler dbHandler;
	
	private ViewSectionListActivity viewSectionListActivity;

	public ViewDatesActivityTest() {
		super(ViewSectionListActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);

		viewSectionListActivity = getActivity();
		dbHandler = new SectionDbHandler(viewSectionListActivity);
	}

	@Override
	protected void tearDown() throws Exception {
		dbHandler.closeSectionDB();
		super.tearDown();
	}

	public void testPreConditions() {
		assertTrue(dbHandler != null);
	}
	
	public void testOne() {

	}
	
	public void testTwo() {
		
	}
}
