package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import edu.channel4.mm.db.android.model.request.GraphRequest.TimeScope;

import junit.framework.TestCase;

public class EventOverTimeGraphRequestTest extends TestCase {

    EventOverTimeGraphRequest eotgr;
    String requestType;
    TimeScope timeScope;
    String eventName;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        eotgr = new EventOverTimeGraphRequest();
        
        requestType = "EVENT_OVER_TIME";
        timeScope = TimeScope.DAY;
        eventName = "Level up";
        
        eotgr.setTimeScope(timeScope);
        eotgr.setEventName(eventName);
    }
   
    public void testConstructGraphRequestIntent() {
        fail("Not yet implemented");
    }
   
    public void testGetAdditionalUriParameters() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("requestType", "EVENT_OVER_TIME"));
        params.add(new BasicNameValuePair("timeScope", "DAY"));
        params.add(new BasicNameValuePair("eventName", "Level up"));
        
        assertEquals(params, eotgr.getAdditionalUriParameters());
    }
}
