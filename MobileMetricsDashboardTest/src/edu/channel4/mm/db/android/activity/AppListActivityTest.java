package edu.channel4.mm.db.android.activity;

import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityUnitTestCase;

public class AppListActivityTest extends ActivityUnitTestCase<AppListActivity> {

   AppListActivity activity;
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
   }

   public void testOnClickListener() {
   }

   private void initializeMockSFConn() {
      // Set the SalesforceConn instance to be a mock for full testing purposes
   }

   private void initializeMockTempDB() {
      // Set the TempoDatabase instance to be a mock for full testing purposes
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
