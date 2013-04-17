package edu.channel4.mm.db.android.model.description;

import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;

public class EventNameDescriptionTest extends TestCase {
   
   private static final String validTestString = "[{\"attributes\":{\"type\":\"C4PPMM__Event__c\"},\"C4PPMM__EventName__c\":\"button pressed\"},{\"attributes\":{\"type\":\"C4PPMM__Event__c\"},\"C4PPMM__EventName__c\":\"record name and age\"}]"; 
   private static final String invalidTestString =  "[{\"attributes\":{\"type\":\"C4PPMM__Event__c\"},\"C4PPMM__EventName__c\":\"button pressed\"},{\"attrib";
   private static final String veryInvalidTestString = "HTTP ERROR 400 FAILURE ABORT";

   public void setUp() throws Exception {
   }

   public void tearDown() throws Exception {
   }

   public void testParseValidJSON() {
      try {
         List<EventNameDescription> result = EventNameDescription.parseList(validTestString);
         
         // CAUTION: assuming order of elements in list. Make it a @post of .parseList()
         
         // check "button pressed" event description
         assertEquals("button pressed", result.get(0).getName());
                  
         // check "record name and age" event description
         assertEquals("record name and age", result.get(1).getName());
      }
      catch (JSONException e) {
         fail(e.toString());
      }
   }

   
   public void testParseInvalidJSON() {
      try {
         AppDescription.parseList(invalidTestString);
         fail();
      }
      catch (JSONException e) {
      }
   }
   
   public void testParseVeryInvalidJSON() {
      try {
         AppDescription.parseList(veryInvalidTestString);
         fail();
      }
      catch (JSONException e) {
      }
   }
}
