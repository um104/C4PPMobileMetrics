package edu.channel4.mm.db.android.util;

import com.google.inject.Inject;

import roboguice.inject.ContextSingleton;
import roboguice.util.RoboAsyncTask;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

@ContextSingleton
public abstract class BaseAsyncTask2<ResultT> extends RoboAsyncTask<ResultT> {

   @Inject
   protected BaseAsyncTask2(Context context) {
      super(context);
   }

   @Override
   public abstract ResultT call() throws Exception;

   /**
    * Before every AsyncTask runs, we should verify that we're actually
    * connected to the Internet.
    */
   @Override
   protected void onPreExecute() {
      // do this in the UI thread before executing call()
      if (!phoneConnectedToInternet()) {
         Log.toastE(context, "Error: Not connected to Internet.");
         cancel(true);
      }
   }

   /**
    * Do this in the UI thread if call() succeeds.
    */
   @Override
   protected abstract void onSuccess(ResultT result);

   /**
    * Do this in the UI thread if call() threw an exception.
    */
   @Override
   protected abstract void onException(Exception e);

   /**
    * Always do this in the UI thread after calling call()
    */
   @Override
   protected void onFinally() {
   }

   /**
    * Helper method to determine if this phone is connected to the Internet or
    * not.
    * 
    * @return
    */
   private boolean phoneConnectedToInternet() {
      ConnectivityManager connMgr = (ConnectivityManager) context
               .getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

      return networkInfo != null && networkInfo.isConnected();
   }

}
