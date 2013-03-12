package edu.channel4.mm.db.android.activity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.test.ActivityUnitTestCase;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.database.TempoDatabase;
import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.network.SalesforceConn;

public class AppListActivityTest extends ActivityUnitTestCase<AppListActivity> {

   AppListActivity activity;
   TempoDatabase mockTempDB;
   SalesforceConn mockSFConn;
   Context mockContext;
   SharedPreferences mockSharedPrefs;
   SharedPreferences.Editor mockEditor;

   private static final String INSTANCE_MEMBER_NAME = "instance";

   public AppListActivityTest() {
      super(AppListActivity.class);
   }

   @Override
   public void setUp() throws Exception {
      super.setUp();

      // initialize some mocks
      initializeMockTempDB();
      initializeMockSFConn();
      mockContext = mock(Context.class);
      setActivityContext(mockContext);
      mockSharedPrefs = mock(SharedPreferences.class);
      mockEditor = mock(SharedPreferences.Editor.class);
   }

   public void testPreConditions() {

      //TODO FIXME: java.lang.AssertionError: LayoutIflator not found. 
      // start the activity under test
      //activity = startActivity(new Intent(), null, null);
      activity = startActivity(new Intent(getInstrumentation().getTargetContext(), AppListActivity.class), null, null);

      // Get the ListView and ListAdapter to check that they were set correctly
      ListView listView = (ListView) getActivity().findViewById(
               R.id.listViewAppList);
      ListAdapter adapter = listView.getAdapter();

      // verify that R.layout.activity_app_list was used as contentView
      //TODO: not possible

      // verify that SalesforceConn instance was made
      //TODO: not possible

      // verify that R.id.listViewAppList had a setOnItemClickListener set
      OnItemClickListener clickListener = listView.getOnItemClickListener();
      assertTrue(null != clickListener);
      
      // verify that R.id.listViewAppList had its adapter set to an
      // AppDataArrayAdapter
      assertTrue(null != adapter);

      // verify that mockTempoDB.addOnAppDescChangedList gets called with the
      // activity
      verify(mockTempDB).addOnAppDescriptionChangedListener(activity);

      // verify that sfConn.getAppList() is called
      verify(mockSFConn).getAppList();
   }

   public void testOnClickListener() {
      final String APP_NAME = "MySecondApp";
      
      // prepare for calling of onItemClick
      when(mockContext.getSharedPreferences("edu.channel4.mm.db.android", 0))
               .thenReturn(mockSharedPrefs);
      when(mockSharedPrefs.edit()).thenReturn(mockEditor);
      
      // create a mock List<AppDescription> to have the right appname
      List<AppDescription> mockAppList = mock(List.class);
      AppDescription mockAppDesc = mock(AppDescription.class);
      when(mockAppList.get(0)).thenReturn(mockAppDesc);
      when(mockAppDesc.getAppName()).thenReturn(APP_NAME);
      
      //TODO FIXME: java.lang.AssertionError: LayoutInflator not found. 
      // start the activity under test
      //activity = startActivity(new Intent(), null, null);
      activity = startActivity(new Intent(getInstrumentation().getTargetContext(), AppListActivity.class), null, null);
      
      
      // set AppList to be our mock version
      activity.appList = mockAppList;
      
      // get the listView used in the activity
      ListView listView = (ListView) getActivity().findViewById(
               R.id.listViewAppList);

      // get the clickListener set on the listView
      OnItemClickListener clickListener = listView.getOnItemClickListener();
      
      // verify onCLickListener stuff
      clickListener.onItemClick(null, null, 0, 0L);

      // verify that correct string is put in sharedPrefs
      verify(mockEditor).putString("C4PPMM__Label__c", APP_NAME);
      verify(mockEditor).commit();

      // verify that an intent to DashboardActivity is started
      Intent intent = getStartedActivityIntent();
      assertEquals(DashboardActivity.class.getName(), intent.getComponent()
               .getClassName());

   }

   private void initializeMockSFConn() {
      // Set the SalesforceConn instance to be a mock for full testing purposes
      mockSFConn = mock(SalesforceConn.class);
      initializeMockSingleton(SalesforceConn.class, mockSFConn);
   }

   private void initializeMockTempDB() {
      // Set the TempoDatabase instance to be a mock for full testing purposes
      mockTempDB = mock(TempoDatabase.class);
      initializeMockSingleton(TempoDatabase.class, mockTempDB);
   }

   /**
    * Inspiration: http://pastebin.com/M2HTGhu6
    * @param clz
    * @param value
    */
   private <T> void initializeMockSingleton(Class<?> clz, T value) {
      try {
         Field field = clz.getDeclaredField(INSTANCE_MEMBER_NAME);
         boolean oldAccessible = field.isAccessible();
         field.setAccessible(true);
         field.set(null, value);
         field.setAccessible(oldAccessible);
      }
      catch (NoSuchFieldException e) {
         e.printStackTrace();
         fail();
      }
      catch (IllegalArgumentException e) {
         // Object is not compatible with declaring class
         e.printStackTrace();
         fail();
      }
      catch (IllegalAccessException e) {
         // The field is not accessible (should never be a problem)
         e.printStackTrace();
         fail();
      }
   }
}
