package cs428.mobiletaandroidapp.section;

import java.util.ArrayList;
import java.util.Random;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * 
 * Test Cases for SectionDatabaseHandler
 * 
 * @author Junhyun Park
 *
 */
public class SectionDbHandlerTest extends AndroidTestCase {

	private static final String TEST_FILE_PREFIX = "test_";
	
	private static final int RANDOM_SEED = 19580427;
	
	private static final int NUM_OF_DUMMIES = 3;
	
	private SectionDbHandler dbHandler;
	
	private Random generator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
		
		dbHandler = new SectionDbHandler(context);
		generator = new Random(RANDOM_SEED);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		dbHandler.closeSectionDB();
		dbHandler = null;
	}
	
	public void testPreConditions() {
	    assertNotNull(dbHandler);
	}

	public void testInsertSectionAndGetSectionWithRowID() {
		int[] sectionId = new int[NUM_OF_DUMMIES];

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			String testName = "name" + generator.nextInt();
			String testCourse = "course" + generator.nextInt();
			
			Section section = new Section(testName, testCourse);

			sectionId[i] = dbHandler.insertSection(section);
			assertFalse(sectionId[i] == -1);

			Section testSection = dbHandler.getSectionWithRowID(sectionId[i]);
			assertTrue(section.getName().equals(testSection.getName()));
			assertTrue(section.getCourse().equals(testSection.getCourse()));
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++)
			dbHandler.deleteSection(sectionId[i]);	// not sure where to put it
	}
	
	public void testDeleteSection() {
		String testName = "name" + generator.nextInt();
		String testCourse = "course" + generator.nextInt();
		
		Section section = new Section(testName, testCourse);
		
		int rowID = dbHandler.insertSection(section);
		Section testSection = dbHandler.getSectionWithRowID(rowID);
		assertNotNull(testSection);

		dbHandler.deleteSection(rowID);

		testSection = dbHandler.getSectionWithRowID(rowID);
		assertNull(testSection);
	}

	public void testGetAllSections() {
		ArrayList<Section> sectionList;
		int[] sectionId = new int[NUM_OF_DUMMIES];
		int beforeCount = dbHandler.getAllSections().size();

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			String testName = "name" + generator.nextInt();
			String testCourse = "course" + generator.nextInt();
			
			Section section = new Section(testName, testCourse);

			sectionId[i] = dbHandler.insertSection(section);
		}

		sectionList = dbHandler.getAllSections();
		int afterCount = sectionList.size();
		
		assertEquals(beforeCount + NUM_OF_DUMMIES, afterCount);

		for (int i = 0; i < afterCount; i++) {
			Section section = sectionList.get(i);
			Section testSection = dbHandler.getSectionWithRowID(section.getId());
			assertNotNull(testSection);
			assertTrue(section.getName().equals(testSection.getName()));
			assertTrue(section.getCourse().equals(testSection.getCourse()));
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			dbHandler.deleteSection(sectionId[i]);
		}
	}
}
