package edu.channel4.mm.db.android.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.channel4.mm.db.android.util.Keys;
import android.os.Parcel;
import android.os.Parcelable;

public class AttribDescription implements Comparable<AttribDescription>, Parcelable {

	private String attribName;
	private String attribEventName;
	
	public AttribDescription(String attribName, String attribEventName) {
		this.attribName = attribName;
		this.attribEventName = attribEventName;
	}

	public String getAttribName() {
		return attribName;
	}

	public String getAttribEventName() {
		return attribEventName;
	}
	
	/**
	 * Parses a JSON List of AttribDescription into a proper List<{@code AttribDescription}>
	 * 
	 * @param appDataListString
	 * @return
	 * @throws JSONException
	 */
	public static List<AttribDescription> parseList(String attribDataListString)
			throws JSONException {
		List<AttribDescription> attribDataList = new ArrayList<AttribDescription>();

		// definitely keep 5. Test the others.
		String fixedJson1 = attribDataListString.replace("\\n", "");
		String fixedJson2 = fixedJson1.replace("\n", "");
		String fixedJson3 = fixedJson2.replace("\\\\", "");
		String fixedJson4 = fixedJson3.replace("\\", "");
		String fixedJson5 = fixedJson4.substring(1, fixedJson4.length() - 1);
		
		JSONArray attribDataArray = new JSONArray(fixedJson5);

		for (int i = 0; i < attribDataArray.length(); i++) {
			JSONObject attribDataObject = attribDataArray.getJSONObject(i);

			String attributeName = attribDataObject.getString(Keys.ATTRIB_NAME);
			String eventName = attribDataObject.getString((Keys.EVENT_NAME));
			
			AttribDescription attribDesc = new AttribDescription(attributeName, eventName);
			
			attribDataList.add(attribDesc);
		}

		return attribDataList;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if (attribEventName.length() != 0) {
			sb.append(attribEventName + " - ");
		}
		sb.append(attribName);
		
		return sb.toString();
	}

	@Override
	public int compareTo(AttribDescription attribDesc) {
		int retval;
		
		// Both are session attribs
		if (attribEventName.length() == 0 && attribDesc.getAttribEventName().length() == 0) {
			retval = attribName.compareTo(attribDesc.getAttribName());
		}
		// Both are event attribs
		else if (attribEventName.length() != 0 && attribDesc.getAttribEventName().length() != 0) {
			if (attribEventName.equals(attribDesc.getAttribEventName())) { // same event
				retval = attribName.compareTo(attribDesc.getAttribName());
			}
			else {
				retval = attribEventName.compareTo(attribDesc.getAttribEventName());
			}
		}
		else { // either me or attribDesc is a session
			retval = (attribEventName.length() == 0) ? 1 : -1;
		}

		return retval;
	}
	
	/* Parcel methods below */
	
	@Override
	public int describeContents() {
		// Leave as is
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		// Note: Parcel data is read in a FIFO manner.
		dest.writeString(attribName);
		dest.writeString(attribEventName);
	}
	
	public static final Parcelable.Creator<AttribDescription> CREATOR = new Parcelable.Creator<AttribDescription>() {
		public AttribDescription createFromParcel(Parcel in) {
			return new AttribDescription(in);
		}

		@Override
		public AttribDescription[] newArray(int size) {
			return new AttribDescription[size];
		}
	};
	
	private AttribDescription(Parcel in) {
		//Note: Parcel data is read in a FIFO manner.
		attribName = in.readString();
		attribEventName = in.readString();
	}

}
