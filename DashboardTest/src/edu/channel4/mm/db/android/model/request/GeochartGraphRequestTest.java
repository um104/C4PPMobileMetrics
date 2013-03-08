package edu.channel4.mm.db.android.model.request;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;
import edu.channel4.mm.db.android.util.Keys;

public class GeochartGraphRequestTest extends AndroidTestCase {
   
   private GeochartGraphRequest geochartRequest;
   private Context context;

   protected void setUp() throws Exception {
      super.setUp();
      
      geochartRequest = new GeochartGraphRequest();
   }

   protected void tearDown() throws Exception {
      super.tearDown();
   }

   public void testToString() {
      String actual = geochartRequest.toString();
      String expected = "Nationality Breakdown";
      assertEquals(expected, actual);
      
   }

   public void testGetRestRequestType() {
      String actual = geochartRequest.getRestRequestType();
      String expected = "GEOCHART";
      assertEquals(expected, actual);
   }

   public void testGetUrlParameterString() {
      
      //set the appLabel in sharedPreferences in the context
      setAppLabelInContext();
      
      String actual = geochartRequest.getUrlParameterString(context);
      String expected = "requestType=GEOCHART&appLabel=MySecondApp";
      
      assertEquals(actual, expected);
   }

   public void testConstructGraphRequestIntent() {
      //set the appLabel in the sharedPreferences in the context
      setAppLabelInContext();
      
      Intent intent = geochartRequest.constructGraphRequestIntent(context);
      
      //check intent for correct extra, and correct class
      String actualClassName = intent.getComponent().getClassName();
      String actualPackageName = intent.getComponent().getPackageName();
      
      String expectedClassName = GraphViewerActivity.class.getName();
      String expectedPackageName = context.getApplicationContext().getPackageName();
      
      assertEquals(expectedClassName, actualClassName);
      assertEquals(expectedPackageName, actualPackageName);
   }
   
   public void setAppLabelInContext() {
      context = getContext();
      SharedPreferences.Editor editor = context.getSharedPreferences(Keys.PREFS_NS, 0).edit();
      editor.putString(Keys.APP_LABEL, "MySecondApp");
      editor.commit();
   }
}
