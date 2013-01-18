@isTest
public class SessionRestResourceTest {
	
    static testMethod void myTest() {
        // Setup the POST request (from 
        System.RestContext.request = new RestRequest();
        RestContext.request.requestURI = '/feedpost/whatever';
        RestContext.request.addHeader('email', 'blahblah@some.org');

        // Setup some test data to pass into the POST request
        SessionRestResource.Attributes myAtt = new SessionRestResource.Attributes();
        SessionRestResource.HeaderBlob someHeaderBlob = new SessionRestResource.HeaderBlob();
        
        myAtt.dt = 'nigga';
        myAtt.dc = 'nigga';
        myAtt.dac = 'nigga';
        myAtt.du = 'nigga';
        myAtt.dmo = 'nigga';
        myAtt.dlc = 'nigga';
        myAtt.dma = 'nigga';
        myAtt.lv = 'nigga';
        myAtt.dms = 'nigga';
        myAtt.dov = 'nigga';
        myAtt.av = 'nigga';
        myAtt.au = 'nigga';
        myAtt.nca = 'nigga';
        myAtt.nc = 'nigga';
        myAtt.dll = 'nigga';
        myAtt.dsdk = 'nigga';
        myAtt.dp = 'nigga';
        
        someHeaderBlob.seq = 42;
        someHeaderBlob.dt = 'nigga';
        someHeaderBlob.u = 'nigga';
        someHeaderBlob.pa = 42;
        someHeaderBlob.attrs = myAtt;
        
        /*
        someHeaderBlob.attrs.dt = 'nigga';
        someHeaderBlob.attrs.dc = 'nigga';
        someHeaderBlob.attrs.dac = 'nigga';
        someHeaderBlob.attrs.du = 'nigga';
        someHeaderBlob.attrs.dmo = 'nigga';
        someHeaderBlob.attrs.dlc = 'nigga';
        someHeaderBlob.attrs.dma = 'nigga';
        someHeaderBlob.attrs.lv = 'nigga';
        someHeaderBlob.attrs.dms = 'nigga';
        someHeaderBlob.attrs.dov = 'nigga';
        someHeaderBlob.attrs.av = 'nigga';
        someHeaderBlob.attrs.au = 'nigga';
        someHeaderBlob.attrs.nca = 'nigga';
        someHeaderBlob.attrs.nc = 'nigga';
        someHeaderBlob.attrs.dll = 'nigga';
        someHeaderBlob.attrs.dsdk = 'nigga';
        someHeaderBlob.attrs.dp = 'nigga';
        */
        
        SessionRestResource.SessionOpen mySesh = new SessionRestResource.SessionOpen();
        
        
        //SessionRestResource.uploadSession(someHeaderBlob);
        
        Test.startTest();
        
        // Invoke the method
        SessionRestResource.uploadSession(someHeaderBlob);
        
        Test.stopTest();
        
        
		System.assertEquals(someHeaderBlob.seq,42);
        System.assertEquals(someHeaderBlob.dt,'nigga');
        System.assertEquals(someHeaderBlob.u,'nigga');
        System.assertEquals(someHeaderBlob.pa,42);
        /*
        System.assertEquals(someHeaderBlob.attrs.dt,'nigga');
        System.assertEquals(someHeaderBlob.attrs.dc,'nigga');
        System.assertEquals(someHeaderBlob.attrs.dac,'nigga');
        System.assertEquals(someHeaderBlob.attrs.du,'nigga');
        System.assertEquals(someHeaderBlob.attrs.dmo,'nigga');
        System.assertEquals(someHeaderBlob.attrs.dlc,'nigga');
        System.assertEquals(someHeaderBlob.attrs.dma,'nigga');
        System.assertEquals(someHeaderBlob.attrs.lv,'nigga');
        System.assertEquals(someHeaderBlob.attrs.dms,'nigga');
        System.assertEquals(someHeaderBlob.attrs.dov,'nigga');
        System.assertEquals(someHeaderBlob.attrs.av,'nigga');
        System.assertEquals(someHeaderBlob.attrs.au,'nigga');
        System.assertEquals(someHeaderBlob.attrs.nca,'nigga');
        System.assertEquals(someHeaderBlob.attrs.nc,'nigga');
        System.assertEquals(someHeaderBlob.attrs.dll,'nigga');
        System.assertEquals(someHeaderBlob.attrs.dsdk,'nigga');
        System.assertEquals(someHeaderBlob.attrs.dp,'nigga');
        */
    }
}