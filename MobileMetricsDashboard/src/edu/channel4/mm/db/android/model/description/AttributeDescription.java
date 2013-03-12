package edu.channel4.mm.db.android.model.description;


public class AttributeDescription {

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


}
