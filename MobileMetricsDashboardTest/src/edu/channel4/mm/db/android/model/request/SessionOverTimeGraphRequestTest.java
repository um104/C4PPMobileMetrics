package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import junit.framework.TestCase;

public class SessionOverTimeGraphRequestTest extends TestCase {

    SessionOverTimeGraphRequest sotgr;
    String requestType;
    String timeInterval;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        sotgr = new SessionOverTimeGraphRequest();
        requestType = "SESSION_OVER_TIME";
        timeInterval = "12";
        
        sotgr.setTimeInterval(timeInterval);
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
   
    public void testConstructGraphRequestIntent() {
        fail("Not yet implemented");
    }
   
    public void testGetAdditionalUriParameters() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("requestType", "SESSION_OVER_TIME"));
        params.add(new BasicNameValuePair("timeInterval", "12"));
        
        assertEquals(params, sotgr.getAdditionalUriParameters());
    }
}
