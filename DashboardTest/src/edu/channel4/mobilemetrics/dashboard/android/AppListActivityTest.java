package edu.channel4.mobilemetrics.dashboard.android;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class edu.channel4.mobilemetrics.dashboard.android.AppListActivityTest \
 * edu.channel4.mobilemetrics.dashboard.android.tests/android.test.InstrumentationTestRunner
 */
public class AppListActivityTest extends ActivityInstrumentationTestCase2<AppListActivity> {

    public AppListActivityTest() {
        super("edu.channel4.mobilemetrics.dashboard.android", AppListActivity.class);
    }

}
