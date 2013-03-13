package edu.channel4.mm.db.android.model.graph;

import java.util.List;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.TableRow;
import com.google.visualization.datasource.datatable.value.NumberValue;
import com.google.visualization.datasource.datatable.value.TextValue;

import edu.channel4.mm.db.android.util.Log;

public class Graph {

   private String title;
   private List<GraphType> validGraphTypes;
   private DataTable datatable;

   public Graph(String title, List<GraphType> validGraphTypes,
                DataTable datatable) {
      this.title = title;
      this.validGraphTypes = validGraphTypes;
      this.datatable = datatable;
   }

   public String getTitle() {
      return title;
   }

   /**
    * Gets a random Intent for ACE to draw.
    * 
    * @param context
    * @return
    */
   public Intent getRandomIntent(Context context) {

      // Pick a random number in the validGraphTypes
      Random rand = new Random();
      int element = rand.nextInt(validGraphTypes.size());

      GraphType graphType = validGraphTypes.get(element);

      switch (graphType) {
         case PIE:
            return getPieGraphIntent(context);
         case BAR:
            return getBarGraphIntent(context);
         case LINE:
            return getLineGraphIntent(context);
         default:
            Log.e("Unknown graphType: " + graphType.toString());
            return null;
      }
   }

   private Intent getPieGraphIntent(Context context) {
      Intent intent = null;
      Random rand = new Random();

      CategorySeries categorySeries = new CategorySeries("unknown pie chart");
      DefaultRenderer renderer = new DefaultRenderer();

      for (TableRow tableRow : datatable.getRows()) {
         // FIXME: Hack that assumes that column 0 is string and column 1 is
         // number
         String category = ((TextValue) tableRow.getCell(0).getValue())
                  .getValue();
         Double value = ((NumberValue) tableRow.getCell(1).getValue())
                  .getValue();
         categorySeries.add(category, value);
         SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
         simpleSeriesRenderer.setColor(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
         renderer.addSeriesRenderer(simpleSeriesRenderer);
      }

      /*SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
      renderer.addSeriesRenderer(simpleSeriesRenderer);*/

      intent = ChartFactory.getPieChartIntent(context, categorySeries,
               renderer, "some activity");

      return intent;
   }

   private Intent getBarGraphIntent(Context context) {
      return setupXYMultipleSeriesIntent(getBarRenderer(), context);
   }

   private Intent getLineGraphIntent(Context context) {
      return setupXYMultipleSeriesIntent(getLineRenderer(), context);
   }

   private Intent setupXYMultipleSeriesIntent(
            XYMultipleSeriesRenderer renderer, Context context) {
      Intent intent = null;

      XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
      XYSeries xySeries = new XYSeries("some xy series");

      int i = 1;
      for (TableRow tableRow : datatable.getRows()) {

         // FIXME: Hack that assumes that column 0 is string and column 1 is
         // number
         String category = ((TextValue) tableRow.getCell(0).getValue())
                  .getValue();
         Double value = ((NumberValue) tableRow.getCell(1).getValue())
                  .getValue();
         xySeries.add(i, value);
         renderer.addXTextLabel(i, category);
         i++;
      }

      dataset.addSeries(xySeries);

      intent = ChartFactory.getBarChartIntent(context, dataset, renderer,
               Type.DEFAULT);

      return intent;
   }

   private static XYMultipleSeriesRenderer getBarRenderer() {
      // Define a renderer
      XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
      renderer.setAxisTitleTextSize(16);
      renderer.setChartTitleTextSize(20);
      renderer.setLabelsTextSize(15);
      renderer.setLegendTextSize(15);
      renderer.setMargins(new int[] {20, 30, 15, 0});
      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
      r.setColor(Color.GREEN);
      renderer.addSeriesRenderer(r);

      // Set chart settings
      renderer.setChartTitle("Chart demo");
      renderer.setXTitle("x values");
      renderer.setYTitle("y values");
      renderer.setXAxisMin(0.5);
      renderer.setXAxisMax(10.5);
      renderer.setYAxisMin(0);
      renderer.setYAxisMax(210);

      return renderer;
   }

   private static XYMultipleSeriesRenderer getLineRenderer() {
      // Define a renderer
      XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
      renderer.setAxisTitleTextSize(16);
      renderer.setChartTitleTextSize(20);
      renderer.setLabelsTextSize(15);
      renderer.setLegendTextSize(15);
      renderer.setMargins(new int[] {20, 30, 15, 0});
      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
      r.setColor(Color.GREEN);
      renderer.addSeriesRenderer(r);

      // Set chart settings
      renderer.setChartTitle("Chart demo");
      renderer.setXTitle("x values");
      renderer.setYTitle("y values");
      renderer.setXAxisMin(0.5);
      renderer.setXAxisMax(10.5);
      renderer.setYAxisMin(0);
      renderer.setYAxisMax(210);

      return renderer;
   }

}
