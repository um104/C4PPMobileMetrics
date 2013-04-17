package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import edu.channel4.mm.db.android.R;

public class CustomGraphRequestTest extends TestCase {
    
    CustomGraphRequest cgr;
    String requestType;
    String eventName1;
    String attribName1;
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
        
        eventName1 = "";
        attribName1 = "DeviceType__c";
        requestType = "CUSTOM";
        cgr = new CustomGraphRequest("Device Type Distribution", eventName1,
                attribName1, R.drawable.device_type_dist);
    }
    
   public void testConstructGraphRequestIntent() {
       fail("Not yet implemented");
   }

   public void testGetAdditionalUriParameters() {
       List<NameValuePair> params = new ArrayList<NameValuePair>();

       params.add(new BasicNameValuePair("requestType", requestType));
       params.add(new BasicNameValuePair("attribName1", attribName1));
       params.add(new BasicNameValuePair("eventName1", eventName1));
       
       assertEquals(params, cgr.getAdditionalUriParameters());
   }
}
