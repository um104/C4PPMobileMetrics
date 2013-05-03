package edu.channel4.mm.db.android.database;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContextSingleton;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;

import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;

@ContextSingleton
public class CustomGraphRequestDataSource {

   private SQLiteDatabase database;
   private MySQLIteHelper dbHelper;
   private String[] allColumns = {MySQLIteHelper.COLUMN_ID,
                                  MySQLIteHelper.COLUMN_GRAPH_NAME,
                                  MySQLIteHelper.COLUMN_EVENT1_NAME,
                                  MySQLIteHelper.COLUMN_ATTRIB1_NAME,
                                  MySQLIteHelper.COLUMN_ICON_ID,
                                  MySQLIteHelper.COLUMN_APP_ID};

   @Inject
   public CustomGraphRequestDataSource(Context context) {
      dbHelper = new MySQLIteHelper(context);
   }

   public void open() throws SQLException {
      database = dbHelper.getWritableDatabase();
   }

   public void close() {
      dbHelper.close();
   }

   public void insertCustomGraphRequest(CustomGraphRequest customGraphRequest,
            String appId) {
      ContentValues values = new ContentValues();

      // Fill up the row you're about to insert to the table
      values.put(MySQLIteHelper.COLUMN_GRAPH_NAME,
               customGraphRequest.getGraphName());
      values.put(MySQLIteHelper.COLUMN_EVENT1_NAME,
               customGraphRequest.getEventName());
      values.put(MySQLIteHelper.COLUMN_ATTRIB1_NAME,
               customGraphRequest.getAttributeName());
      values.put(MySQLIteHelper.COLUMN_ICON_ID, customGraphRequest.getIconId());
      values.put(MySQLIteHelper.COLUMN_APP_ID, appId);

      // Insert the CustomGraphRequest into SQLite.
      database.insert(MySQLIteHelper.TABLE_CUSTOM_GRAPHS, null, values);
   }

   /**
    * Returns the CustomGraphRequests saved to SQLite for a particular app.
    * 
    * @param appId
    *           The unique App ID for the app you want CustomGraphRequests for.
    * @return
    */
   public List<GraphRequest> getAllGraphRequests(String appId) {
      List<GraphRequest> customGraphRequests = new ArrayList<GraphRequest>();

      // Execute the query.
      Cursor cursor = database.query(MySQLIteHelper.TABLE_CUSTOM_GRAPHS,
               allColumns, MySQLIteHelper.COLUMN_APP_ID + " = '" + appId + "'", null,
               null, null, null);

      // Set the cursor to point to the first CustomGraphRequest.
      cursor.moveToFirst();

      // Fill up the List<CustomGraphRequest> using the cursor to iterate.
      while (!cursor.isAfterLast()) {
         CustomGraphRequest customGraphRequest = cursorToCustomGraphRequest(cursor);
         customGraphRequests.add(customGraphRequest);
         cursor.moveToNext();
      }

      // Don't forget to close the cursor.
      cursor.close();

      return customGraphRequests;
   }

   private CustomGraphRequest cursorToCustomGraphRequest(Cursor cursor) {
      CustomGraphRequest customGraphRequest = new CustomGraphRequest();

      // TODO: Remove magic numbers (used Vogella tutorial originally).
      customGraphRequest.setReadOnly(true);
      customGraphRequest.setName(cursor.getString(1));
      customGraphRequest.setEventName(cursor.getString(2));
      customGraphRequest.setAttributeName(cursor.getString(3));
      customGraphRequest.setIconId(cursor.getInt(4));

      return customGraphRequest;
   }

}
