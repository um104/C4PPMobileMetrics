package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import edu.channel4.mm.db.android.model.request.GraphRequest.TimeScope;

public class SessionOverTimeGraphRequestTest extends TestCase {

    SessionOverTimeGraphRequest sotgr;
    String requestType;
    TimeScope timeScope;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        sotgr = new SessionOverTimeGraphRequest();
        requestType = "SESSION_OVER_TIME";
        timeScope = TimeScope.DAY;
        
        sotgr.setTimeScope(timeScope);
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
        params.add(new BasicNameValuePair("timeScope", "DAY"));
        
        assertEquals(params, sotgr.getAdditionalUriParameters());
    }
}
