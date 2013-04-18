package edu.channel4.mm.db.android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.channel4.mm.db.android.util.Log;

class MySQLIteHelper extends SQLiteOpenHelper {

   static final String TABLE_CUSTOM_GRAPHS = "custom_graphs";
   static final String COLUMN_ID = "_id";
   static final String COLUMN_GRAPH_NAME = "graph_name";
   static final String COLUMN_EVENT1_NAME = "event1_name";
   static final String COLUMN_ATTRIB1_NAME = "attrib1_name";
   static final String COLUMN_ICON_ID = "icon_id";
   static final String COLUMN_APP_ID = "app_id";

   private static final String DATABASE_NAME = "custom_graph_requests_database";
   private static final int DATABASE_VERSION = 1;

   private static final String DATABASE_CREATE = "create table "
            + TABLE_CUSTOM_GRAPHS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_GRAPH_NAME
            + " text, " + COLUMN_EVENT1_NAME + " text not null, "
            + COLUMN_ATTRIB1_NAME + " text not null, " + COLUMN_ICON_ID
            + " integer, " + COLUMN_APP_ID + " text not null);";

   public MySQLIteHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
   }

   @Override
   public void onCreate(SQLiteDatabase database) {
      database.execSQL(DATABASE_CREATE);
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      Log.w("Upgrading database from version " + oldVersion + " to "
               + newVersion + ", which will destroy all old data");
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOM_GRAPHS);
      onCreate(db);
   }

}
