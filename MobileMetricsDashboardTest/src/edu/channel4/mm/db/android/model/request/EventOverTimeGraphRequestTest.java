package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import junit.framework.TestCase;

public class EventOverTimeGraphRequestTest extends TestCase {

    EventOverTimeGraphRequest eotgr;
    String requestType;
    String timeInterval;
    String eventName;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        eotgr = new EventOverTimeGraphRequest();
        
        requestType = "EVENT_OVER_TIME";
        timeInterval = "12";
        eventName = "Level up";
        
        eotgr.setTimeInterval(timeInterval);
        eotgr.setEventName(eventName);
    }
   
    public void testConstructGraphRequestIntent() {
        fail("Not yet implemented");
    }
   
    public void testGetAdditionalUriParameters() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("requestType", "EVENT_OVER_TIME"));
        params.add(new BasicNameValuePair("timeInterval", "12"));
        params.add(new BasicNameValuePair("eventName", "Level up"));
        
        assertEquals(params, eotgr.getAdditionalUriParameters());
    }
}
