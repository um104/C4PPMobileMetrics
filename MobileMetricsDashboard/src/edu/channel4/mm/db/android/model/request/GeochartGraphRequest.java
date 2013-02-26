package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import edu.channel4.mm.db.android.util.Keys;
import android.content.Context;
import android.content.Intent;

public class GeochartGraphRequest implements GraphRequest {
   
   private String appLabel;

   @Override
   public String getRestRequestType() {
      return "GEOCHART";
   }

   @Override
   public String getUrlParameterString(Context context) {
      
      appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0)
               .getString(Keys.APP_LABEL, null);
      
      List<NameValuePair> params = new ArrayList<NameValuePair>();

      //Should be "custom" "SessionOverTime", "EventOverTime", etc.
      params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, getRestRequestType()));
      params.add(new BasicNameValuePair(Keys.APP_LABEL_URL_PARAMETER_NAME, appLabel));

      String paramString = URLEncodedUtils.format(params, "utf-8");

      return paramString;
   }

   @Override
   public Intent constructGraphRequestIntent(Context context) {
      // TODO Auto-generated method stub
      return null;
   }
}