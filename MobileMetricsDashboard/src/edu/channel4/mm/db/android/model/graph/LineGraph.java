package edu.channel4.mm.db.android.model.graph;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.TableRow;
import com.google.visualization.datasource.datatable.value.NumberValue;
import com.google.visualization.datasource.datatable.value.TextValue;

public class LineGraph {

   /*package*/ static Intent getLineGraphIntent(Context context, DataTable datatable) {
      String[] titles = new String[] {"Count"};
      List<double[]> values = new ArrayList<double[]>();
      List<String> categories = new ArrayList<String>();

      int xmax = 0;
      double ymax = 0;

      double[] firstVals = new double[datatable.getRows().size()];

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

      // int[] colors = new int[] {Color.parseColor("#77c4d3")};
      XYMultipleSeriesRenderer renderer = getLineRenderer();
      /*
       * renderer.setOrientation(Orientation.HORIZONTAL);
       * setChartSettings(renderer,
       * datatable.getColumnDescription(0).getLabel(),
       * datatable.getColumnDescription(0).getLabel(), "Count", 0, xmax + 1, 0,
       * 1.3 * ymax, Color.BLACK, Color.BLACK);
       */
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

      return ChartFactory.getLineChartIntent(context,
               buildBarDataset(titles, values), renderer, "activitytitle");
   }

   // TODO: Make "buildLineDataset" instead of "buildBarDataset" here.
   private static XYMultipleSeriesDataset buildBarDataset(String[] titles,
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

   private static XYMultipleSeriesRenderer getLineRenderer() {
      // Define a renderer
      XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
      renderer.setAxisTitleTextSize(16);
      renderer.setChartTitleTextSize(20);
      renderer.setLabelsTextSize(15);
      renderer.setLegendTextSize(15);
      renderer.setMargins(new int[] {20, 30, 15, 0});

      XYSeriesRenderer r = new XYSeriesRenderer();
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
