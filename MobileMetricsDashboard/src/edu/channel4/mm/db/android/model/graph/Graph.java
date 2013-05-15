package edu.channel4.mm.db.android.model.graph;

import java.util.List;

import org.achartengine.GraphicalView;

import android.content.Context;
import android.content.Intent;

import com.google.visualization.datasource.datatable.DataTable;

import edu.channel4.mm.db.android.util.Log;

public class Graph {

   private String title;
   private List<GraphType> validGraphTypes;
   private DataTable datatable;

   protected Graph(String title, List<GraphType> validGraphTypes,
                   DataTable datatable) {
      this.title = title;
      this.validGraphTypes = validGraphTypes;
      this.datatable = datatable;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public List<GraphType> getValidGraphTypes() {
      return validGraphTypes;
   }

   public Intent getIntent(GraphType graphType, Context context) {
      switch (graphType) {
         case PIE:
            return PieGraph.getPieGraphIntent(context, datatable);
         case BAR:
            return BarGraph.getBarGraphIntent(context, datatable);
         case LINE:
            return LineGraph.getLineGraphIntent(context, datatable);
         default:
            Log.e("Unknown graphType: " + graphType.toString());
            return null;
      }
   }

   public GraphicalView getView(GraphType graphType, Context context) {
      switch (graphType) {
         case PIE:
            return PieGraph.getPieGraphView(context, datatable);
         case BAR:
            return BarGraph.getBarGraphView(context, datatable);
         case LINE:
            return LineGraph.getLineGraphView(context, datatable);
         default:
            Log.e("Unknown graphType: " + graphType.toString());
            return null;
      }
   }
}
