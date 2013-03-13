package edu.channel4.mm.db.android.model.description;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.channel4.mm.db.android.util.Keys;

/**
 * This class is used to describe events as a whole event -- attributes and all.
 * Use this when you need to store the event name and its related attributes
 * 
 * @author mlerner
 *
 */
public class EventDescription {
   
   private String name;
	private List<AttributeDescription> eventAttributes;

	public EventDescription(String name, List<AttributeDescription> eventAttributes) {
		this.name = name;
		this.eventAttributes = eventAttributes;
	}
	
	@Override
	public String toString() {
	   return name;
	}
	
	public String getName() {
	   return name;
	}
	
	public List<AttributeDescription> getAttributes() {
	   return eventAttributes;
	}
	
	//TODO: look into Jackson for JSON parsing (https://github.com/FasterXML/jackson-databind)
	/**
	 * Takes a properly formatted JSON string representing the attributes in an app organized by events.
	 * This should be used to parse the response from "apexrest/channel4_attributes/". 
	 * 
	 * @param eventDescriptionListString
	 * @return
	 * @throws JSONException
	 */
	public static List<EventDescription> parseList(String eventDescriptionListString) throws JSONException {
	   
	   // Make list to return
	   List<EventDescription> eventDescriptions = new ArrayList<EventDescription>();
	   
	   // transform string parameter into JSON object for parsing
	   JSONObject eventDescriptionListJSONObject = new JSONObject(eventDescriptionListString);
	   
	   // Extract the session attributes
	   JSONArray sessionAttributesJSON = eventDescriptionListJSONObject.getJSONArray(Keys.SESSION_ATTRIBUTE_DESCRIPTIONS);
	   List<AttributeDescription> sessionAttributesList = new ArrayList<AttributeDescription>();
	   
	   // go through the session attributes JSON array
	   for (int i = 0; i < sessionAttributesJSON.length(); i++) {
	      // get each attribute name
	      String attribName = sessionAttributesJSON.getString(i);
	      //TODO: cut the __c off the end of each name
	      
	      // add the attribute to the sessionAttribList
	      sessionAttributesList.add(new AttributeDescription(attribName));
	   }
	   // add the session to the event descriptions returned
	   eventDescriptions.add(new EventDescription("Session Attributes", sessionAttributesList));
	   
	   // Extract the list of events
	   JSONArray eventDescriptionsJSON = eventDescriptionListJSONObject.getJSONArray(Keys.EVENT_DESCRIPTIONS);
	   
	   // go through each event passed back
	   for (int i = 0; i < eventDescriptionsJSON.length(); i++) {
	      // extract the specific event
	      JSONObject eventAttributeDescription = eventDescriptionsJSON.getJSONObject(i);

	      // Pull the out the Event name and its attributes in JSONArray form
	      String eventNameString = eventAttributeDescription.getString(Keys.NAME);
	      JSONArray eventAttributesJSONArray = eventAttributeDescription.getJSONArray(Keys.EVENT_ATTRIBUTES);

	      // Add all of the attributes to a List
	      List<AttributeDescription> eventAttributeDescriptions = new ArrayList<AttributeDescription>();
	      for (int j = 0; j < eventAttributesJSONArray.length(); j++) {
	         String eventAttributeDescriptionString = eventAttributesJSONArray.getString(j);
	         eventAttributeDescriptions.add(new AttributeDescription(eventAttributeDescriptionString));
	      }

	      // Put the list of attributes in an EventDescription
	      EventDescription eventDescription = new EventDescription(eventNameString, eventAttributeDescriptions);
	      
	      // Put this EventDescription into the list of event descriptions returned
	      eventDescriptions.add(eventDescription);
	   }
	   
	   return eventDescriptions;
	}
}

/*
 * Sample output from apexrest/channel4_attributes/ -- getting all attributes
 * {"sAttrs":["AndroidIDHash__c","DataConnectionType__c","DeviceSerialHash__c","DeviceCountry__c","DeviceManufacturer__c","DeviceModel__c","DeviceType__c","EpochTime__c","LanguageLocale__c","LocaleCountryCode__c","LocalyticsApiKey__c","LocalyticsLibraryVersion__c","NetworkCarrier__c","NetworkCountryCode__c","OSVersion__c","PersistentStorageCreationTimeSeconds__c","SDKCompatibility__c","SessionLengthSeconds__c","UUID__c"],"eDescs":[{"name":"button pressed","eAttrs":["button","tr.button"]},{"name":"record name and age","eAttrs":["age","name"]}]}
 * {
    "sAttrs": ["AndroidIDHash__c", "DataConnectionType__c", "DeviceSerialHash__c", "DeviceCountry__c", "DeviceManufacturer__c", "DeviceModel__c", "DeviceType__c", "EpochTime__c", "LanguageLocale__c", "LocaleCountryCode__c", "LocalyticsApiKey__c", "LocalyticsLibraryVersion__c", "NetworkCarrier__c", "NetworkCountryCode__c", "OSVersion__c", "PersistentStorageCreationTimeSeconds__c", "SDKCompatibility__c", "SessionLengthSeconds__c", "UUID__c"],
    "eDescs": [{
        "name": "button pressed",
        "eAttrs": ["button", "tr.button"]
    }, {
        "name": "record name and age",
        "eAttrs": ["age", "name"]
    }]
}
 */



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