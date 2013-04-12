package edu.channel4.mm.db.android.model.description;

import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;

public class AppDescriptionTest extends TestCase {
      
   private static final String validTestString = "[{\"attributes\":{\"type\":\"C4PPMM__App__c\",\"url\":\"/services/data/v26.0/sobjects/C4PPMM__App__c/a04E00000034dlHIAQ\"},\"C4PPMM__PackageName__c\":\"com.prototype1.analyticsprototype1\",\"Id\":\"a04E00000034dlHIAQ\",\"C4PPMM__Label__c\":\"My First App\"},{\"attributes\":{\"type\":\"C4PPMM__App__c\",\"url\":\"/services/data/v26.0/sobjects/C4PPMM__App__c/a04E00000034dkdIAA\"},\"C4PPMM__PackageName__c\":\"pn02\",\"Id\":\"a04E00000034dkdIAA\",\"C4PPMM__Label__c\":\"an02\"},{\"attributes\":{\"type\":\"C4PPMM__App__c\",\"url\":\"/services/data/v26.0/sobjects/C4PPMM__App__c/a04E00000034dkiIAA\"},\"C4PPMM__PackageName__c\":\"edu.channel4.mobilemetrics.sample.android\",\"Id\":\"a04E00000034dkiIAA\",\"C4PPMM__Label__c\":\"MobileMetricsSDKTest\"},{\"attributes\":{\"type\":\"C4PPMM__App__c\",\"url\":\"/services/data/v26.0/sobjects/C4PPMM__App__c/a04E00000036iMPIAY\"},\"C4PPMM__PackageName__c\":\"edu.channel4.buttonmash\",\"Id\":\"a04E00000036iMPIAY\",\"C4PPMM__Label__c\":\"ButtonMash\"},]\n";
   private static final String invalidTestString = "[{\"attributes\":{\"type\":\"C4PPMM__App__c\",\"url\":\"/services/data/v26.0/sobjects/C4PPMM__App__c/a04E00000034dlHIAQ\"},\"C4PPMM__PackageName__c\":\"com.prototype1.analyticsprototype1\",\"Id\":\"a04E00000034dlHIAQ\",\"C4PPMM__Label__c\":\"My First App\"";
   private static final String veryInvalidTestString = "HTTP ERROR 400 FAILURE ABORT";

   public void setUp() throws Exception {
   }

   public void tearDown() throws Exception {
   }

   public void testParseValidJSON() {
      
      try {
         List<AppDescription> result = AppDescription.parseList(validTestString);
         
         assertEquals(result.get(0).appId, "a04E00000034dlHIAQ");
         assertEquals(result.get(0).appLabel, "My First App");
         assertEquals(result.get(0).packageName, "com.prototype1.analyticsprototype1");
         
         assertEquals(result.get(1).appId, "a04E00000034dkdIAA");
         assertEquals(result.get(1).appLabel, "an02");
         assertEquals(result.get(1).packageName, "pn02");

         assertEquals(result.get(2).appId, "a04E00000034dkiIAA");
         assertEquals(result.get(2).appLabel, "MobileMetricsSDKTest");
         assertEquals(result.get(2).packageName, "edu.channel4.mobilemetrics.sample.android");
         
         assertEquals(result.get(3).appId, "a04E00000036iMPIAY");
         assertEquals(result.get(3).appLabel, "ButtonMash");
         assertEquals(result.get(3).packageName, "edu.channel4.buttonmash");
      }
      catch (JSONException e) {
         fail();
      }
   }
   
   public void testParseInvalidJSON() {
      try {
         List<AppDescription> result = AppDescription.parseList(invalidTestString);
         fail();
      }
      catch (JSONException e) {
      }
   }
   
   public void testParseVeryInvalidJSON() {
      try {
         List<AppDescription> result = AppDescription.parseList(veryInvalidTestString);
         fail();
      }
      catch (JSONException e) {
      }
   }
}
