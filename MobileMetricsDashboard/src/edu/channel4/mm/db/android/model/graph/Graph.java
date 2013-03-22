package edu.channel4.mm.db.android.model.graph;

import java.util.ArrayList;
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
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.TableRow;
import com.google.visualization.datasource.datatable.value.NumberValue;
import com.google.visualization.datasource.datatable.value.TextValue;

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

   public List<GraphType> getValidGraphTypes() {
      return validGraphTypes;
   }

   public Intent getIntent(GraphType graphType, Context context) {
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

   // /**
   // * Gets a random Intent for ACE to draw.
   // *
   // * @param context
   // * @return
   // */
   // public Intent getRandomIntent(Context context) {
   //
   // // Pick a random number in the validGraphTypes
   // Random rand = new Random();
   // int element = rand.nextInt(validGraphTypes.size());
   //
   // GraphType graphType = validGraphTypes.get(element);
   //
   // switch (graphType) {
   // case PIE:
   // return getPieGraphIntent(context);
   // case BAR:
   // return getBarGraphIntent(context);
   // case LINE:
   // return getLineGraphIntent(context);
   // default:
   // Log.e("Unknown graphType: " + graphType.toString());
   // return null;
   // }
   // }

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
         simpleSeriesRenderer.setColor(Color.rgb(rand.nextInt(255),
                  rand.nextInt(255), rand.nextInt(255)));
         renderer.addSeriesRenderer(simpleSeriesRenderer);
      }

      /*
       * SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
       * renderer.addSeriesRenderer(simpleSeriesRenderer);
       */

      intent = ChartFactory.getPieChartIntent(context, categorySeries,
               renderer, "some activity");

      return intent;
   }

   private Intent getBarGraphIntent(Context context) {
      String[] titles = new String[] {"Count"};
      List<double[]> values = new ArrayList<double[]>();
      List<String> categories = new ArrayList<String>();

      int xmax = 0;
      double ymax = 0;

      double[] firstVals = new double[20];

      for (TableRow tableRow : datatable.getRows()) {
         // FIXME: Hack that assumes that column 0 is string and column 1 is
         // number
         String category = ((TextValue) tableRow.getCell(0).getValue())
                  .getValue();
         Double value = ((NumberValue) tableRow.getCell(1).getValue())
                  .getValue();
         if (value > ymax) {
            ymax = value;
         }

         firstVals[xmax] = value;
         categories.add(category);
         xmax++;
      }

      values.add(firstVals);

      int[] colors = new int[] {Color.parseColor("#77c4d3")};
      XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
      renderer.setOrientation(Orientation.HORIZONTAL);
      setChartSettings(renderer, datatable.getColumnDescription(0).getLabel(),
               datatable.getColumnDescription(0).getLabel(), "Count", 0,
               xmax + 1, 0, 1.3 * ymax, Color.BLACK, Color.BLACK);

      renderer.setXLabels(1);
      renderer.setYLabels(10);

      for (int i = 1; i <= xmax; i++) {
         renderer.addXTextLabel(i, categories.get(i - 1));
      }

      int length = renderer.getSeriesRendererCount();
      for (int i = 0; i < length; i++) {
         SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
         seriesRenderer.setDisplayChartValues(true);
      }

      return ChartFactory.getBarChartIntent(context,
               buildBarDataset(titles, values), renderer, Type.DEFAULT);

   }

   private Intent getLineGraphIntent(Context context) {

      XYMultipleSeriesDataset dataset = setupXYMultipleSeriesIntent(
               getLineRenderer(), context);
      
      XYMultipleSeriesRenderer renderer = null;
//      SimpleSeriesRenderer simpleSeriesRenderer = null;
      
      Intent intent = ChartFactory.getLineChartIntent(context, dataset, renderer);

      return intent;
   }

   private XYMultipleSeriesDataset setupXYMultipleSeriesIntent(
            XYMultipleSeriesRenderer renderer, Context context) {
//      Intent intent = null;

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

      return dataset;
   }

   private XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
      XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
      renderer.setAxisTitleTextSize(16);
      renderer.setChartTitleTextSize(20);
      renderer.setLabelsTextSize(15);
      renderer.setLegendTextSize(15);
      renderer.setBarSpacing(1);

      renderer.setMarginsColor(Color.parseColor("#EEEDED"));
      renderer.setXLabelsColor(Color.BLACK);
      renderer.setYLabelsColor(0, Color.BLACK);

      renderer.setApplyBackgroundColor(true);
      renderer.setBackgroundColor(Color.parseColor("#FBFBFC"));

      int length = colors.length;
      for (int i = 0; i < length; i++) {
         SimpleSeriesRenderer r = new SimpleSeriesRenderer();
         r.setColor(colors[i]);
         r.setChartValuesSpacing(15);
         renderer.addSeriesRenderer(r);
      }
      return renderer;
   }

   private XYMultipleSeriesDataset buildBarDataset(String[] titles,
            List<double[]> values) {
      XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
      int length = titles.length;
      for (int i = 0; i < length; i++) {
         CategorySeries series = new CategorySeries(titles[i]);
         double[] v = values.get(i);
         int seriesLength = v.length;
         for (int k = 0; k < seriesLength; k++) {
            series.add(v[k]);
         }
         dataset.addSeries(series.toXYSeries());
      }
      return dataset;
   }

   private void setChartSettings(XYMultipleSeriesRenderer renderer,
            String title, String xTitle, String yTitle, double xMin,
            double xMax, double yMin, double yMax, int axesColor,
            int labelsColor) {
      renderer.setChartTitle(title);
      renderer.setYLabelsAlign(Align.RIGHT);
      renderer.setXTitle(xTitle);
      renderer.setYTitle(yTitle);
      renderer.setXAxisMin(xMin);
      renderer.setXAxisMax(xMax);
      renderer.setYAxisMin(yMin);
      renderer.setYAxisMax(yMax);
      renderer.setMargins(new int[] {10, 65, 10, 15});
      renderer.setAxesColor(axesColor);
      renderer.setLabelsColor(labelsColor);
   }

//   private static XYMultipleSeriesRenderer getBarRenderer() {
//      // Define a renderer
//      XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
//      renderer.setAxisTitleTextSize(16);
//      renderer.setChartTitleTextSize(20);
//      renderer.setLabelsTextSize(15);
//      renderer.setLegendTextSize(15);
//      renderer.setMargins(new int[] {20, 30, 15, 0});
//      SimpleSeriesRenderer r = new SimpleSeriesRenderer();
//      r.setColor(Color.GREEN);
//      renderer.addSeriesRenderer(r);
//
//      // Set chart settings
//      renderer.setChartTitle("Chart demo");
//      renderer.setXTitle("x values");
//      renderer.setYTitle("y values");
//      renderer.setXAxisMin(0.5);
//      renderer.setXAxisMax(10.5);
//      renderer.setYAxisMin(0);
//      renderer.setYAxisMax(210);
//
//      return renderer;
//   }

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
