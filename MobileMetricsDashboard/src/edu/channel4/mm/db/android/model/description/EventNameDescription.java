package edu.channel4.mm.db.android.model.description;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.channel4.mm.db.android.util.Keys;

/**
 * This class represents an event as just its name.
 * Use this when you want to store or pass around just an event name.
 * @author mlerner
 *
 */
public class EventNameDescription {

   private String name;
   
   public EventNameDescription(String name) {
      this.name = name;
   }
   
   @Override
   public String toString() {
      return name;
   }
   
   public String getName() {
      return name;
   }
   
   /**
    * This method parses a properly formatted JSON string that contains events listed as their names and nothing more.
    * This expects input in the same format that calling "/apexrest/channel4_events/" would give.
    * 
    * Sample input:
    * [{"attributes":{"type":"C4PPMM__Event__c"},"C4PPMM__EventName__c":"button pressed"},{"attributes":{"type":"C4PPMM__Event__c"},"C4PPMM__EventName__c":"record name and age"}]
    * 
    * @param eventNameDescriptionListString
    * @return
    * @throws JSONException
    */
   public static List<EventNameDescription> parseList(String eventNameDescriptionListString) throws JSONException {
      
      // Make list to return
      List<EventNameDescription> eventNameDescriptions = new ArrayList<EventNameDescription>();
      
      // transform string parameter into JSON array for easier parsing
      JSONArray eventNameDescriptionListJSONArray = new JSONArray(eventNameDescriptionListString);
      
      // go through the returned JSON array
      for (int i = 0; i < eventNameDescriptionListJSONArray.length(); i++) {
         // get each JSON object
         JSONObject eventNameDescription = eventNameDescriptionListJSONArray.getJSONObject(i);
         
         // get the name of the event
         String eventName = eventNameDescription.getString(Keys.C4PPMM_EVENT_NAME);
         
         // create a new EventNameDescription with that name
         EventNameDescription event = new EventNameDescription(eventName);
         
         // add that EventNameDescription to the return list
         eventNameDescriptions.add(event);
      }
      
      return eventNameDescriptions;
   }
}
/*
* Sample output from apexrest/channel4_events/ -- getting event names
* [{"attributes":{"type":"C4PPMM__Event__c"},"C4PPMM__EventName__c":"button pressed"},{"attributes":{"type":"C4PPMM__Event__c"},"C4PPMM__EventName__c":"record name and age"}]
* 
[{
   "attributes": {
       "type": "C4PPMM__Event__c"
   },
   "C4PPMM__EventName__c": "button pressed"
}, {
   "attributes": {
       "type": "C4PPMM__Event__c"
   },
   "C4PPMM__EventName__c": "record name and age"
}]
*/