package edu.channel4.mm.db.android.model.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.activity.EditGraphRequestActivity;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;
import edu.channel4.mm.db.android.network.GraphRequestAsyncTask;
import edu.channel4.mm.db.android.network.RestClientAccess;
import edu.channel4.mm.db.android.network.SalesforceConn;
import edu.channel4.mm.db.android.util.Keys;
import edu.channel4.mm.db.android.util.Log;

public class CustomGraphRequest implements GraphRequest, HasAttributeParameter,
         Parcelable {

   private final static String REQUEST_TYPE = "CUSTOM";
   private String eventName1;
   private String attribName1;
   private boolean isPredefined;
   private String graphName = "Custom Graph";
   private int iconId;

   public CustomGraphRequest() {
      this.graphName = "Custom Graph";
      this.isPredefined = false;
      this.iconId = R.drawable.ic_launcher;
   }
   
   /**
    * This constructor is to be used when you want to make a new pre-defined graph
    * that is essentially a Custom Graph with its parameters set particularly.
    * @param graphName The display name of the graph
    * @param eventName1 The name of the event of the item -- usually an empty string
    * @param attribName1 The name of the attribute in question.
    */
   public CustomGraphRequest(String graphName, String eventName1, String attribName1, int iconId) {
      this.graphName = graphName;
      this.eventName1 = eventName1;
      this.attribName1 = attribName1;
      this.iconId = iconId;
      this.isPredefined = true;
   }
   
   public String toString() {
      return graphName;
   }

   public Intent constructGraphRequestIntent(Context context) {
      Intent intent;
      
      // change what activity the Intent goes to depending on if it's predefined or not
      intent = new Intent(context, (isPredefined)? EditGraphRequestActivity.class : GraphViewerActivity.class);
      intent.putExtra(Keys.GRAPH_REQUEST_EXTRA, this);
      

      return intent;
   }

   @Override
   public int getIconId() {
      return iconId;
   }

   @Override
   public URI getUri(RestClientAccess restClientManager, Context context) {
      URI uri = null;

      // get some of the basic information we'll need to make the URI
      String instanceURL = restClientManager.getInstanceURL().toString();
      String appLabel = context.getSharedPreferences(Keys.PREFS_NS, 0)
               .getString(Keys.APP_LABEL, null);

      // make the URI String
      String uriString = "";

      // add in all non-parameterized information onto the request
      uriString += String.format(SalesforceConn.SALESFORCE_BASE_REST_URL,
               instanceURL, GraphRequestAsyncTask.GRAPH_REQUEST_URL_SUFFIX);

      // create a list for the URL parameters to add
      List<NameValuePair> params = new ArrayList<NameValuePair>();

      params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, this.REQUEST_TYPE));
      params.add(new BasicNameValuePair(Keys.APP_LABEL_URL_PARAMETER_NAME,
               appLabel));
      params.add(new BasicNameValuePair(Keys.ATTRIB_NAME_1, attribName1));
      params.add(new BasicNameValuePair(Keys.EVENT_NAME_1, eventName1));

      // add the parameters to the uriString
      String paramString = URLEncodedUtils.format(params, "utf-8");
      uriString += "?" + paramString;

      // turn the string into a URI
      try {
         uri = new URI(uriString);
      }
      catch (URISyntaxException e) {
         Log.e(e.getMessage());
      }

      return uri;
   }

   @Override
   public void setEventName(String eventName) {
      this.eventName1 = eventName;
   }

   @Override
   public String getEventName() {
      return eventName1;
   }

   @Override
   public void setAttributeName(String attribName) {
      this.attribName1 = attribName;
   }

   @Override
   public String getAttributeName() {
      return attribName1;
   }

   /* Everything from here down is for implementing the Parcelable interface */
   @Override
   public int describeContents() {
      // Auto-generated method stub
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      // Note: Parcel data is read in a FIFO manner.
      dest.writeString(eventName1);
      dest.writeString(attribName1);
   }

   public static final Parcelable.Creator<CustomGraphRequest> CREATOR = new Parcelable.Creator<CustomGraphRequest>(){
      public CustomGraphRequest createFromParcel(Parcel in) {
         return new CustomGraphRequest(in);
      }

      @Override
      public CustomGraphRequest[] newArray(int size) {
         return new CustomGraphRequest[size];
      }
   };

   private CustomGraphRequest(Parcel in) {
      // Note: Parcel data is read in a FIFO manner.
      this.eventName1 = in.readString();
      this.attribName1 = in.readString();
   }

}
