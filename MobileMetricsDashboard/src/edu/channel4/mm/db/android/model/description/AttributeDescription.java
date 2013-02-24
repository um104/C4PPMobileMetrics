package edu.channel4.mm.db.android.model.description;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.channel4.mm.db.android.util.Keys;

public abstract class AttributeDescription {

	private String name;

	public AttributeDescription(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static List<AttributeDescription> parseList(
			String appDescriptionListString) throws JSONException {
		List<AttributeDescription> attributeDescriptions = new ArrayList<AttributeDescription>();

		JSONObject attribuDescListJSONObject = new JSONObject(
				appDescriptionListString);

		// Extract sessionAttributeDescriptions
		JSONArray sessionAttributeDescriptions = attribuDescListJSONObject
				.getJSONArray(Keys.SESSION_ATTRIBUTE_DESCRIPTIONS);
		for (int i = 0; i < sessionAttributeDescriptions.length(); i++) {
			String sessionAttributeDescriptionString = sessionAttributeDescriptions
					.getString(i);

			attributeDescriptions.add(new SessionAttributeDescription(
					sessionAttributeDescriptionString));
		}

		// Extract eventAttributeDescriptions
		JSONArray eventDescriptions = attribuDescListJSONObject
				.getJSONArray(Keys.EVENT_DESCRIPTIONS);
		for (int i = 0; i < eventDescriptions.length(); i++) {
			JSONObject eventAttributeDescription = eventDescriptions
					.getJSONObject(i);

			// Pull the out the EventDescription and its
			// EventAttributeDescriptionsJSONArray
			String eventDescriptionString = eventAttributeDescription
					.getString(Keys.EVENT_NAME);
			JSONArray eventAttributesJSONArray = eventAttributeDescription
					.getJSONArray(Keys.EVENT_ATTRIBUTES);

			// Add all of the EventAttributeDescriptions to a POJO List
			List<EventAttributeDescription> eventAttributeDescriptions = new ArrayList<EventAttributeDescription>();
			for (int j = 0; j < eventAttributesJSONArray.length(); j++) {
				String eventAttributeDescriptionString = eventAttributesJSONArray
						.getString(j);
				eventAttributeDescriptions.add(new EventAttributeDescription(
						eventAttributeDescriptionString));
			}

			// Put the list of EventAttributeDescriptions in an EventDescription
			EventDescription eventDescription = new EventDescription(
					eventDescriptionString, eventAttributeDescriptions);
			
			// Put this EventDescription into the list of AttributeDescriptions
			attributeDescriptions.add(eventDescription);
		}

		return attributeDescriptions;
	}
	// /**
	// * Parses a JSON List of AttribDescription into a proper List<{@code
	// AttribDescription}>
	// *
	// * @param appDataListString
	// * @return
	// * @throws JSONException
	// */
	// public static List<AttributeDescription> parseList(String
	// attribDataListString)
	// throws JSONException {
	// List<AttributeDescription> attribDataList = new
	// ArrayList<AttributeDescription>();
	//
	// JSONArray attribDataArray = new JSONArray(attribDataListString);
	//
	// for (int i = 0; i < attribDataArray.length(); i++) {
	// JSONObject attribDataObject = attribDataArray.getJSONObject(i);
	//
	// String attributeName = attribDataObject.getString(Keys.ATTRIB_NAME);
	// String eventName = attribDataObject.getString((Keys.EVENT_NAME));
	//
	// AttributeDescription attribDesc = new AttributeDescription(attributeName,
	// eventName);
	//
	// attribDataList.add(attribDesc);
	// }
	//
	// return attribDataList;
	// }
	//
	// @Override
	// public String toString() {
	// StringBuilder sb = new StringBuilder();
	//
	// if (attribEventName.length() != 0) {
	// sb.append(attribEventName + " - ");
	// }
	// sb.append(attribName);
	//
	// return sb.toString();
	// }
	//
	// @Override
	// public int compareTo(AttributeDescription attribDesc) {
	// int retval;
	//
	// // Both are session attribs
	// if (attribEventName.length() == 0 &&
	// attribDesc.getAttribEventName().length() == 0) {
	// retval = attribName.compareTo(attribDesc.getAttribName());
	// }
	// // Both are event attribs
	// else if (attribEventName.length() != 0 &&
	// attribDesc.getAttribEventName().length() != 0) {
	// if (attribEventName.equals(attribDesc.getAttribEventName())) { // same
	// event
	// retval = attribName.compareTo(attribDesc.getAttribName());
	// }
	// else {
	// retval = attribEventName.compareTo(attribDesc.getAttribEventName());
	// }
	// }
	// else { // either me or attribDesc is a session
	// retval = (attribEventName.length() == 0) ? 1 : -1;
	// }
	//
	// return retval;
	// }

	// /* Parcel methods below */
	//
	// @Override
	// public int describeContents() {
	// // Leave as is
	// return 0;
	// }
	//
	// @Override
	// public void writeToParcel(Parcel dest, int arg1) {
	// // Note: Parcel data is read in a FIFO manner.
	// dest.writeString(attribName);
	// dest.writeString(attribEventName);
	// }
	//
	// public static final Parcelable.Creator<AttributeDescription> CREATOR =
	// new Parcelable.Creator<AttributeDescription>() {
	// public AttributeDescription createFromParcel(Parcel in) {
	// return new AttributeDescription(in);
	// }
	//
	// @Override
	// public AttributeDescription[] newArray(int size) {
	// return new AttributeDescription[size];
	// }
	// };
	//
	// private AttributeDescription(Parcel in) {
	// //Note: Parcel data is read in a FIFO manner.
	// attribName = in.readString();
	// attribEventName = in.readString();
	// }

}
