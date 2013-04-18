package edu.channel4.mm.db.android.model.graph;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import android.content.Context;
import android.content.Intent;

import com.google.visualization.datasource.datatable.DataTable;

public class GraphTest extends TestCase {

   protected void setUp() throws Exception {
      super.setUp();
   }

   protected void tearDown() throws Exception {
      super.tearDown();
   }

   public void testGetIntent() {

      Context context = mock(Context.class);

      // TODO (mlerner): How do I test this method? It returns an intent, but I'm not the
      // one creating the intent -- a library ends up creating it. To be honest,
      // the thing to test here is to make sure that it creates the intent for
      // the correct type of graph -- Pie, Bar, or Line. Also, that it gives
      // error messages. It may not be worth testing.

      DataTable datatable = new DataTable();
      String title = "title";
      List<GraphType> validGraphTypes = new ArrayList<GraphType>();
      validGraphTypes.add(GraphType.PIE);
      validGraphTypes.add(GraphType.BAR);

      Graph graph = new Graph(title, validGraphTypes, datatable);

      Intent result = graph.getIntent(GraphType.PIE, context);

      fail("Not yet implemented");
   }

}
