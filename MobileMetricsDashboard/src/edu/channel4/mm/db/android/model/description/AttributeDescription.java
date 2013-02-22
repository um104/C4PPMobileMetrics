package edu.channel4.mm.db.android.model.description;


public abstract class AttributeDescription {

	private String name;
	
	public AttributeDescription(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
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
