package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import edu.channel4.mm.db.android.R;
import edu.channel4.mm.db.android.activity.EditGraphRequestActivity;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;
import edu.channel4.mm.db.android.util.Keys;

public class CustomGraphRequest implements GraphRequest, HasAttributeParameter {

   private final static String REQUEST_TYPE = "Custom";
   private String eventName1;
   private String attribName1;

   /**
    * This field used to be called "isPredefined." Let's call it "readOnly"
    * since we change the field at runtime now (true CustomGraphRequests turn
    * into "readOnly" CustomGraphRequests once they're finalized, as to not
    * bring up the EditGraphRequestActivity again the second time they're
    * clicked).
    */
   private boolean readOnly;
   
   private String graphName = "Custom Graph";
   private int iconId;

   public CustomGraphRequest() {
      this.graphName = "Custom Graph";
      this.readOnly = false;
      this.iconId = R.drawable.ic_launcher;
   }

   /**
    * This constructor is to be used when you want to make a new pre-defined
    * graph that is essentially a Custom Graph with its parameters set
    * particularly.
    * 
    * @param graphName
    *           The display name of the graph
    * @param eventName1
    *           The name of the event of the item -- usually an empty string
    * @param attribName1
    *           The name of the attribute in question.
    */
   public CustomGraphRequest(String graphName, String eventName1,
                             String attribName1, int iconId) {
      this.graphName = graphName;
      this.eventName1 = eventName1;
      this.attribName1 = attribName1;
      this.iconId = iconId;
      this.readOnly = true;
   }

   public String toString() {
      return graphName;
   }

   public Intent constructGraphRequestIntent(Context context) {
      Intent intent;

      // change what activity the Intent goes to depending on if it's predefined
      // or not
      intent = new Intent(context, (readOnly) ? GraphViewerActivity.class
               : EditGraphRequestActivity.class);
      intent.putExtra(Keys.GRAPH_REQUEST_EXTRA, this);

      return intent;
   }

   @Override
   public int getIconId() {
      return iconId;
   }

   /**
    * This setter is used to turn a true Custom Graph into a regular old
    * Predefined Custom Graph once its been saved into the DB. That way,
    * clicking the saved CustomGraph won't bring up the
    * EditGraphRequestActivity.
    */
   public void setReadOnly(boolean readOnly) {
      this.readOnly = readOnly;
   }

   public void setName(String graphName) {
      this.graphName = graphName;
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
      dest.writeInt(iconId);
   }

   public static final Parcelable.Creator<CustomGraphRequest> CREATOR = new Parcelable.Creator<CustomGraphRequest>() {
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
      this.iconId = in.readInt();
   }

   @Override
   public List<NameValuePair> getAdditionalUriParameters() {
      List<NameValuePair> params = new ArrayList<NameValuePair>();

      params.add(new BasicNameValuePair(Keys.REQUEST_TYPE, REQUEST_TYPE));
      params.add(new BasicNameValuePair(Keys.ATTRIB_NAME_1, attribName1));
      params.add(new BasicNameValuePair(Keys.EVENT_NAME_1, eventName1));

      return params;
   }

}
