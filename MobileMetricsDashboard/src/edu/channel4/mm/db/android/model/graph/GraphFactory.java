package edu.channel4.mm.db.android.model.graph;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import roboguice.inject.ContextSingleton;
import android.content.Context;

import com.google.inject.Inject;
import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.TableCell;
import com.google.visualization.datasource.datatable.TableRow;
import com.google.visualization.datasource.datatable.value.ValueType;

import edu.channel4.mm.db.android.model.callback.GraphLoadCallback;
import edu.channel4.mm.db.android.model.request.GraphRequest;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

/**
 * Factory used to get {@link Graph}s. This should be the only way to get a
 * graph for business logic.
 * 
 * This class exists to defer choosing where the Graph should come from (either
 * from cached data if it's from the past 24 hours, or from Salesforce if it's
 * older than that). It also opens up this project to unit testing.
 * 
 * @author girum
 */
@ContextSingleton
public class GraphFactory {

   @Inject private Context context;
   @Inject private SalesforceConn sfConn;

   public void getGraph(GraphRequest graphRequest, GraphLoadCallback callback) {
      sfConn.getGraphViaNetwork(graphRequest, callback);
   }

   public static Graph parseGraph(String graphJSONString) throws JSONException,
            TypeMismatchException {
      List<GraphType> validGraphTypes = new ArrayList<GraphType>();
      DataTable datatable = new DataTable();
      
      Log.d(graphJSONString);

      JSONObject graphJSONObject = new JSONObject(graphJSONString);

      // Parse the valid views into the graph
      JSONArray validViewsJSONArray = graphJSONObject
               .getJSONArray("validViews");
      for (int validViewIndex = 0; validViewIndex < validViewsJSONArray
               .length(); validViewIndex++) {
         String graphTypeString = validViewsJSONArray.getString(validViewIndex);

         if (graphTypeString.equals(Keys.LINE)) {
            validGraphTypes.add(GraphType.LINE);
         }
         else if (graphTypeString.equals(Keys.PIE)) {
            validGraphTypes.add(GraphType.PIE);
         }
         else if (graphTypeString.equals(Keys.BAR)) {
            validGraphTypes.add(GraphType.BAR);
         }
         else {
            Log.e("Unknown graph type: " + graphTypeString);
         }
      }

      JSONObject datatableJSONObject = graphJSONObject.getJSONObject("table");

      // Parse the columns of the datatable
      JSONArray colsJsonArray = datatableJSONObject.getJSONArray("cols");

      for (int col = 0; col < colsJsonArray.length(); col++) {
         JSONObject columnJSONObject = colsJsonArray.getJSONObject(col);

         String label = columnJSONObject.getString("label");

         // Parse Google's ValueType enum from the string
         ValueType valueType = null;
         String valueTypeString = columnJSONObject.getString("type");

         if (valueTypeString.equals("string")) {
            valueType = ValueType.TEXT;
         }
         else if (valueTypeString.equals("number")) {
            valueType = ValueType.NUMBER;
         }
         else {
            try {
               valueType = ValueType.valueOf(valueTypeString);
            }
            catch (IllegalArgumentException e) {
               Log.e(e.getMessage());
               throw new JSONException(e.getMessage());
            }
         }

         String id = columnJSONObject.getString("id");

         // Throw this ColumnDescription into the DataTable
         ColumnDescription columnDescription = new ColumnDescription(id,
                  valueType, label);
         datatable.addColumn(columnDescription);
      }

      // Parse the rows of the datatable
      JSONArray rowsJsonArray = datatableJSONObject.getJSONArray("rows");

      for (int row = 0; row < rowsJsonArray.length(); row++) {
         JSONObject tableRowJSONObject = rowsJsonArray.getJSONObject(row);
         TableRow tableRow = new TableRow();

         JSONArray tableCellJSONArray = tableRowJSONObject.getJSONArray("c");
         for (int cellIndex = 0; cellIndex < tableCellJSONArray.length(); cellIndex++) {
            Object value = tableCellJSONArray.getJSONObject(cellIndex).get("v");

            TableCell tableCell = null;

            if (value instanceof Boolean) {
               tableCell = new TableCell((Boolean) value);
            }
            else if (value instanceof Integer) {
               tableCell = new TableCell((Integer) value);
            }
            else if (value instanceof Double) {
               tableCell = new TableCell((Double) value);
            }
            else if (value instanceof String) {
               tableCell = new TableCell((String) value);
            }
            else {
               throw new TypeMismatchException(
                        "Invalid value type for object: " + value.toString());
            }

            tableRow.addCell(tableCell);
         }

         datatable.addRow(tableRow);
      }

      Log.i("Finished loading datatable:");
      for (TableRow tableRow : datatable.getRows()) {
         Log.i(tableRow.getCells().toString());
      }

      return new Graph("Your graph!", validGraphTypes, datatable);
   }
}
