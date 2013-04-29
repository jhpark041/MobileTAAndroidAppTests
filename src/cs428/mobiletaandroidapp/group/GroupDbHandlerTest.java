package cs428.mobiletaandroidapp.group;

import java.util.ArrayList;
import java.util.Random;

import cs428.mobiletaandroidapp.group.Group;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

/**
 * 
 * Test Cases for GroupDatabaseHandler
 * 
 * @author Junhyun Park
 *
 */
public class GroupDbHandlerTest extends AndroidTestCase {

	private static final String TEST_FILE_PREFIX = "test_";
	
	private static final int RANDOM_SEED = 19580427;
	
	private static final int NUM_OF_DUMMIES = 3;
	
	private GroupDbHandler dbHandler;
	
	private Random generator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
		
		dbHandler = new GroupDbHandler(context);
		generator = new Random(RANDOM_SEED);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		dbHandler.closeGroupDB();
		dbHandler = null;
	}
	
	public void testPreConditions() {
	    assertNotNull(dbHandler);
	}

	public void testInsertGroupAndGetGroupWithRowID() {
		int[] groupId = new int[NUM_OF_DUMMIES];

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			String testName = "Name" + generator.nextInt();
			int testSectionID = generator.nextInt();
			int testColor = generator.nextInt();
			
			Group group = new Group(testName, testSectionID, testColor);

			groupId[i] = dbHandler.insertGroup(group);
			assertFalse(groupId[i] == -1);

			Group testGroup = dbHandler.getGroupWithRowID(groupId[i]);
			assertTrue(group.getName().equals(testGroup.getName()));
			assertEquals(group.getSectionID(), testGroup.getSectionID());
			assertEquals(group.getColor(), testGroup.getColor());
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++)
			dbHandler.deleteGroup(groupId[i]);	// not sure where to put it
	}
	
	public void testDeleteGroup() {
		String testName = "Name" + generator.nextInt();
		int testSectionID = generator.nextInt();
		int testColor = generator.nextInt();
		
		Group group = new Group(testName, testSectionID, testColor);
		
		int rowID = dbHandler.insertGroup(group);
		Group testGroup = dbHandler.getGroupWithRowID(rowID);
		assertNotNull(testGroup);

		dbHandler.deleteGroup(rowID);

		testGroup = dbHandler.getGroupWithRowID(rowID);
		assertNull(testGroup);
	}
	
	private int getUniqueSectionID() {
		int count;
		int SectionID = generator.nextInt();
		do {
			count = dbHandler.getGroupsInSection(SectionID).size();
			if(count != 0)
				SectionID = generator.nextInt();
		} while (count != 0);
		
		return SectionID;
	}
	
	public void testDeleteGroupInSection() {
		int[] groupId = new int[NUM_OF_DUMMIES];
		int testSectionID = getUniqueSectionID();
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			String testName = "Name" + generator.nextInt();
			int testColor = generator.nextInt();
			
			Group group = new Group(testName, testSectionID, testColor);
			
			int rowID = dbHandler.insertGroup(group);
			Group testGroup = dbHandler.getGroupWithRowID(rowID);
			assertNotNull(testGroup);
		}
		
		int groupsCount = dbHandler.getGroupsInSection(testSectionID).size();
		assertEquals(NUM_OF_DUMMIES, groupsCount);

		dbHandler.deleteGroupsInSection(testSectionID);
		
		groupsCount = dbHandler.getGroupsInSection(testSectionID).size();
		assertEquals(0, groupsCount);

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			Group testGroup = dbHandler.getGroupWithRowID(groupId[i]);
			assertNull(testGroup);
		}
	}

	public void testGetGroupsInSection() {
		ArrayList<Group> groupList;
		int[] groupId = new int[NUM_OF_DUMMIES];
		int testSectionID = generator.nextInt();
		int beforeCount = dbHandler.getGroupsInSection(testSectionID).size();

		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			String testName = "Name" + generator.nextInt();
			int testColor = generator.nextInt();
			
			Group group = new Group(testName, testSectionID, testColor);

			groupId[i] = dbHandler.insertGroup(group);
		}

		groupList = dbHandler.getGroupsInSection(testSectionID);
		int afterCount = groupList.size();
		
		assertEquals(beforeCount + NUM_OF_DUMMIES, afterCount);

		for (int i = 0; i < afterCount; i++) {
			Group group = groupList.get(i);
			Group testGroup = dbHandler.getGroupWithRowID(group.getId());
			assertNotNull(testGroup);
			assertTrue(group.getName().equals(testGroup.getName()));
			assertEquals(group.getSectionID(), testGroup.getSectionID());
			assertEquals(group.getColor(), testGroup.getColor());
		}
		
		for (int i = 0; i < NUM_OF_DUMMIES; i++) {
			dbHandler.deleteGroup(groupId[i]);
		}
	}
}
