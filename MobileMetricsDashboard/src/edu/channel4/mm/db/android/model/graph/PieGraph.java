package edu.channel4.mm.db.android.model.graph;

import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.TableRow;
import com.google.visualization.datasource.datatable.value.NumberValue;
import com.google.visualization.datasource.datatable.value.TextValue;

public class PieGraph {
   
   /* package */static Intent getPieGraphIntent(Context context,
            DataTable datatable) {
      Intent intent = null;
      
      CategorySeries categorySeries = new CategorySeries("unknown pie chart");
      DefaultRenderer renderer = new DefaultRenderer();
      
      parseDataTable(datatable, categorySeries, renderer);

      intent = ChartFactory.getPieChartIntent(context, categorySeries,
               renderer, "some activity");

      return intent;
   }
   
   /*package*/ static GraphicalView getPieGraphView(Context context, DataTable datatable) {
      CategorySeries categorySeries = new CategorySeries("unknown pie chart");
      DefaultRenderer renderer = new DefaultRenderer();
      
      parseDataTable(datatable, categorySeries, renderer);
      
      GraphicalView view = ChartFactory.getPieChartView(context, categorySeries, renderer);
      
      return view;
   }

   /**
    * Convenience method (for using as well as for testing) that takes the
    * DataTable to be parsed and puts its data into the CategorySeries and
    * DefaultRenderer passed in.
    * 
    * @param datatable
    *           The DataTable to be parsed
    * @param categorySeries
    *           The CategorySeries to be filled with the data from the DataTable
    * @param renderer
    *           The Renderer to be used to display the data
    */
   /* package */static void parseDataTable(DataTable datatable,
            CategorySeries categorySeries, DefaultRenderer renderer) {
      Random rand = new Random();

      for (TableRow tableRow : datatable.getRows()) {
         // FIXME: Hack that assumes that column 0 is string and column 1 is
         // number
         String category = ((TextValue) tableRow.getCell(0).getValue())
                  .getValue();
         Double value = ((NumberValue) tableRow.getCell(1).getValue())
                  .getValue();
         categorySeries.add(category, value);
         SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
         simpleSeriesRenderer.setColor(Color.rgb(rand.nextInt(255),
                  rand.nextInt(255), rand.nextInt(255)));
         renderer.addSeriesRenderer(simpleSeriesRenderer);
      }
      
   }
}
