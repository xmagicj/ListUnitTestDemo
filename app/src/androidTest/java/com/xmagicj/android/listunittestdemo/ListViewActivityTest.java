package com.xmagicj.android.listunittestdemo;

import android.content.Intent;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

/**
 * Unit Test Sample
 * Created by mumu on 14/12/29.
 */
public class ListViewActivityTest extends ActivityInstrumentationTestCase2<ListViewActivity> {

    private final static int TEST_LISTVIEW_POSITION_1 = 1;
    private final static int TEST_LISTVIEW_POSITION_10 = 10;
    private final static int TEST_LISTVIEW_POSITION_37 = 37;
    private ListViewActivity mListViewActivity;
    private ListView mListView;

    /**
     * constructor
     */
    public ListViewActivityTest() {
        super(ListViewActivity.class);
    }

    /**
     * Creates an {@link android.test.ActivityInstrumentationTestCase2}.
     *
     * @param activityClass The activity to test. This must be a class in the instrumentation
     *                      targetPackage specified in the AndroidManifest.xml
     */
    public ListViewActivityTest(Class<ListViewActivity> activityClass) {
        super(activityClass);
    }

    /**
     * Before testing initialize variables and test environment
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Launch the activity
        mListViewActivity = this.getActivity();
        mListView = (ListView) mListViewActivity.findViewById(R.id.lv_main);
    }

    /**
     * testPreconditions always be the first test case
     */
    public void testPreconditions() {
        assertNotNull("instance mListViewActivity", mListViewActivity);
        assertNotNull("instance mListView", mListView);
    }

    /**
     * test begin , sets a listview value, closes the activity, then relaunches
     * that activity. Checks to make sure that the listview values match what we
     * set them to.
     *
     * @throws Throwable
     */
    public void testCase() throws Throwable {
        assertEquals(TEST_LISTVIEW_POSITION_37, mListView.getCount()); // actual 37
        // BEGIN_INCLUDE (write_to_ui)
        // Set listview to test position 10
        mListViewActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Attempts to manipulate the UI must be performed on a UI
                // thread.
                // Calling this outside runOnUiThread() will cause an exception.
                //
                // You could also use @UiThreadTest, but activity lifecycle
                // methods
                // cannot be called if this annotation is used.
                mListView.requestFocus();
                mListView.setSelection(TEST_LISTVIEW_POSITION_10);
            }
        });
        // END_INCLUDE (write_to_ui)

        // BEGIN_INCLUDE (relaunch_activity)
        // Close the activity
        SystemClock.sleep(2000);
        mListViewActivity.finish();
        setActivity(null);  // Required to force creation of a new activity

        // Relaunch the activity
        mListViewActivity = this.getActivity();
        // END_INCLUDE (relaunch_activity)

        // BEGIN_INCLUDE (check_results)
        // Verify that the spinner was saved at position 10
        mListView = (ListView) mListViewActivity.findViewById(R.id.lv_main);
        int currentPosition = mListView.getSelectedItemPosition();
        assertEquals(TEST_LISTVIEW_POSITION_10, currentPosition);
        // END_INCLUDE (check_results)

        // Set listview to test position 1
        mListViewActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mListView.requestFocus();
                mListView.setSelection(TEST_LISTVIEW_POSITION_1);
            }
        });

        // Close the activity
        mListViewActivity.finish();
        setActivity(null);  // Required to force creation of a new activity

        // Relaunch the activity
        mListViewActivity = this.getActivity();

        // Verify that the listview was saved at position 1
        mListView = (ListView) mListViewActivity.findViewById(R.id.lv_main);
        currentPosition = mListView.getSelectedItemPosition();
        assertEquals(TEST_LISTVIEW_POSITION_1, currentPosition);
    }

    /**
     * When the end of the test this method will be call
     * we can release some resource here
     */
    @Override
    protected void tearDown() throws Exception {
        mListViewActivity.finish();
        super.tearDown();
    }

    @Override
    protected void runTest() throws Throwable {
        super.runTest();
    }

    @Override
    public void setActivityInitialTouchMode(boolean initialTouchMode) {
        super.setActivityInitialTouchMode(initialTouchMode);
    }

    @Override
    public void setActivityIntent(Intent i) {
        super.setActivityIntent(i);
    }
}
