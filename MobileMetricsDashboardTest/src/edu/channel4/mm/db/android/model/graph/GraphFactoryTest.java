package edu.channel4.mm.db.android.model.graph;

import org.json.JSONException;

import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import com.google.visualization.datasource.base.TypeMismatchException;

import junit.framework.TestCase;
import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import android.app.Application;
import android.content.Context;
import edu.channel4.mm.db.android.model.callback.GraphLoadCallback;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.network.SalesforceConn;
import static org.mockito.Mockito.*;

public class GraphFactoryTest extends TestCase {
   
   protected Application mockApplication = mock(Application.class, RETURNS_DEEP_STUBS);
   protected Context mockContext = mock(RoboActivity.class, RETURNS_DEEP_STUBS);
   
   protected SalesforceConn mockSfConn= mock(SalesforceConn.class);

   public void setUp() throws Exception {
      // Override the default RoboGuice module
      RoboGuice.setBaseApplicationInjector(mockApplication, RoboGuice.DEFAULT_STAGE, Modules.override(RoboGuice.newDefaultRoboModule(mockApplication)).with(new MyTestModule()));

      when(mockContext.getApplicationContext()).thenReturn(mockApplication);
      when(mockApplication.getApplicationContext()).thenReturn(mockApplication);
   }

   public void tearDown() throws Exception {
      // Don't forget to tear down our custom injector to avoid polluting other test classes
      RoboGuice.util.reset();
   }
   
   public void testGetGraph() {
      GraphFactory graphFactory = new GraphFactory();
      
      GraphRequest mockGraphRequest = mock(GraphRequest.class);
      GraphLoadCallback mockCallback = mock(GraphLoadCallback.class);
      
      graphFactory.getGraph(mockGraphRequest, mockCallback);
      
      verify(mockSfConn).getGraphViaNetwork(mockGraphRequest, mockCallback);
   }
   
   public void testParseGraphBadJSON() {
      try {
         GraphFactory.parseGraph("{NotrealJson:[");
         fail();
      }
      catch (TypeMismatchException e) {
         fail(e.toString());
      }
      catch (JSONException e) {
      }
   }
   
   public void testParseGraphGoodJSON() {
      try{
         GraphFactory.parseGraph("someJSON");
         fail("not yet implemented");
      }
      catch (TypeMismatchException e) {
         fail(e.toString());
      }
      catch (JSONException e) {
         fail(e.toString());
      }
   }
   
   private class MyTestModule extends AbstractModule {
      @Override
      protected void configure() {
          bind(SalesforceConn.class).toInstance(mockSfConn);
          bind(Context.class).toInstance(mockContext);
      }
  }
}