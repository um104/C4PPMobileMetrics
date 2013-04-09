package edu.channel4.mm.db.android.model.graph;

import java.util.Random;

import org.achartengine.ChartFactory;
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

   static Intent getPieGraphIntent(Context context, DataTable datatable) {
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
}
