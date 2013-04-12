package edu.channel4.mm.db.android.model.description;

import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;

public class AppDescriptionTest extends TestCase {
      
   private static final String validTestString = "[{\"attributes\":{\"type\":\"C4PPMM__App__c\",\"url\":\"/services/data/v26.0/sobjects/C4PPMM__App__c/a04E00000034dlHIAQ\"},\"C4PPMM__PackageName__c\":\"com.prototype1.analyticsprototype1\",\"Id\":\"a04E00000034dlHIAQ\",\"C4PPMM__Label__c\":\"My First App\"},{\"attributes\":{\"type\":\"C4PPMM__App__c\",\"url\":\"/services/data/v26.0/sobjects/C4PPMM__App__c/a04E00000034dkdIAA\"},\"C4PPMM__PackageName__c\":\"pn02\",\"Id\":\"a04E00000034dkdIAA\",\"C4PPMM__Label__c\":\"an02\"},{\"attributes\":{\"type\":\"C4PPMM__App__c\",\"url\":\"/services/data/v26.0/sobjects/C4PPMM__App__c/a04E00000034dkiIAA\"},\"C4PPMM__PackageName__c\":\"edu.channel4.mobilemetrics.sample.android\",\"Id\":\"a04E00000034dkiIAA\",\"C4PPMM__Label__c\":\"MobileMetricsSDKTest\"},{\"attributes\":{\"type\":\"C4PPMM__App__c\",\"url\":\"/services/data/v26.0/sobjects/C4PPMM__App__c/a04E00000036iMPIAY\"},\"C4PPMM__PackageName__c\":\"edu.channel4.buttonmash\",\"Id\":\"a04E00000036iMPIAY\",\"C4PPMM__Label__c\":\"ButtonMash\"}]\n";
   private static final String invalidTestString = "[{\"attributes\":{\"type\":\"C4PPMM__App__c\",\"url\":\"/services/data/v26.0/sobjects/C4PPMM__App__c/a04E00000034dlHIAQ\"},\"C4PPMM__PackageName__c\":\"com.prototype1.analyticsprototype1\",\"Id\":\"a04E00000034dlHIAQ\",\"C4PPMM__Label__c\":\"My First App\"";
   private static final String veryInvalidTestString = "HTTP ERROR 400 FAILURE ABORT";

   public void setUp() throws Exception {
   }

   public void tearDown() throws Exception {
   }

   public void testParseValidJSON() {
      
      try {
         List<AppDescription> result = AppDescription.parseList(validTestString);

         assertEquals("a04E00000034dlHIAQ", result.get(0).appId);
         assertEquals("My First App", result.get(0).appLabel);
         assertEquals("com.prototype1.analyticsprototype1", result.get(0).packageName);
         
         assertEquals("a04E00000034dkdIAA", result.get(1).appId);
         assertEquals("an02", result.get(1).appLabel);
         assertEquals("pn02", result.get(1).packageName);

         assertEquals("a04E00000034dkiIAA", result.get(2).appId);
         assertEquals("MobileMetricsSDKTest", result.get(2).appLabel);
         assertEquals("edu.channel4.mobilemetrics.sample.android", result.get(2).packageName);
         
         assertEquals("a04E00000036iMPIAY", result.get(3).appId);
         assertEquals("ButtonMash", result.get(3).appLabel);
         assertEquals("edu.channel4.buttonmash", result.get(3).packageName);
      }
      catch (JSONException e) {
         StackTraceElement[] arr = e.getStackTrace();
         String thing;
         for (StackTraceElement st : arr) {
            thing = st.toString();
            thing.charAt(0);
         }
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
