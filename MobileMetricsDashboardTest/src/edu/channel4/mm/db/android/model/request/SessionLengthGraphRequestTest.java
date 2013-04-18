package edu.channel4.mm.db.android.model.request;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import junit.framework.TestCase;

public class SessionLengthGraphRequestTest extends TestCase {

    SessionLengthGraphRequest slgr;
    String requestType;
    
    protected void setUp() throws Exception {
        super.setUp();
        
        slgr = new SessionLengthGraphRequest();
        requestType = "SESSION_LENGTH";
    }
   
    public void testConstructGraphRequestIntent() {
        fail("Not yet implemented");
    }
   
    public void testGetAdditionalUriParameters() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        
        params.add(new BasicNameValuePair("requestType", "SESSION_LENGTH"));
        
        assertEquals(params, slgr.getAdditionalUriParameters());
    }
}
