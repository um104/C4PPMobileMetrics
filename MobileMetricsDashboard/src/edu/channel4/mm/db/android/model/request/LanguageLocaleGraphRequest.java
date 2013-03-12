package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;
import edu.channel4.mm.db.android.util.Keys;
import android.content.Context;
import android.content.Intent;

public class LanguageLocaleGraphRequest implements GraphRequest {
   
   @Override
   public String toString() {
      return "Language Locale";
   }

   @Override
   public String getRestRequestType() {
      return "CUSTOM";
   }

   @Override
   public String getUrlParameterString(Context context) {

      String appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0)
               .getString(Keys.APP_LABEL, null);
      
      List<NameValuePair> params = new ArrayList<NameValuePair>();

      params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, getRestRequestType()));
      params.add(new BasicNameValuePair(Keys.APP_LABEL_URL_PARAMETER_NAME, appLabel));
      params.add(new BasicNameValuePair(Keys.ATTRIB_NAME_1, "LanguageLocal__c"));
      params.add(new BasicNameValuePair(Keys.EVENT_NAME_1, ""));

      String paramString = URLEncodedUtils.format(params, "utf-8");

      return paramString;
   }

   @Override
   public Intent constructGraphRequestIntent(Context context) {
      Intent intent = new Intent(context, GraphViewerActivity.class);
      
      intent.putExtra(Keys.REQUEST_URL_PARAMETERS, getUrlParameterString(context));
      
      return intent;
   }

   @Override
   public int getIconId() {
      return R.drawable.language_locale;
   }

}
