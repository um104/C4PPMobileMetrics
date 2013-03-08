@isTest
public class AppRestResourceTest
{    
    static testMethod void testGetRelatedApps() 
    {
        System.RestContext.request = new RestRequest();
        RestContext.request.requestURI = '/feedpost/test01';
        RestContext.request.addHeader('email', 'test01@some.org');
        
        List<SessionRestResource.EventAttributeClass> eveattList1 = new List<SessionRestResource.EventAttributeClass>();
        List<SessionRestResource.EventAttributeClass> eveattList2 = new List<SessionRestResource.EventAttributeClass>();
        List<SessionRestResource.EventClass> eveList1 = new List<SessionRestResource.EventClass>();
        List<SessionRestResource.EventClass> eveList2 = new List<SessionRestResource.EventClass>();
        List<SessionRestResource.SessionClass> sesList = new List<SessionRestResource.SessionClass>();
        
        // Creating 2 EventAttributeClass and add them to the list
        SessionRestResource.EventAttributeClass eveatt1 = new SessionRestResource.EventAttributeClass();
        eveatt1.value = 'value01';
        eveatt1.key = 'key01';
        eveattList1.add(eveatt1);

        SessionRestResource.EventAttributeClass eveatt2 = new SessionRestResource.EventAttributeClass();
        eveatt2.value = 'value02';
        eveatt2.key = 'key02';
        eveattList1.add(eveatt2);
        
        // Creating 2 EventClass and add them to the list
        SessionRestResource.EventClass eve1 = new SessionRestResource.EventClass();
        eve1.n = 'n01';
        eve1.su = 'su01';
        eve1.dt = 'dt01';
        eve1.u = 'u01';
        eve1.attrs = eveattList1;
        eve1.ct = 1001001;
        eveList1.add(eve1);

        SessionRestResource.EventClass eve2 = new SessionRestResource.EventClass();
        eve2.n = 'n02';
        eve2.su = 'su02';
        eve2.dt = 'dt02';
        eve2.u = 'u02';
        eve2.attrs = eveattList2;
        eve2.ct = 2001001;
        eveList1.add(eve2);
        
        // Assign value to SessionClass
        SessionRestResource.SessionClass ses = new SessionRestResource.SessionClass();
        ses.dt = 'dt01';
        ses.dc = 'dc01';
        ses.dac = 'dac01';
        ses.du = 'du01';
        ses.dmo = 'dmo01';
        ses.events = eveList1;
        ses.dlc = 'dlc01';
        ses.ctl = 1002;
        ses.dma = 'dma01';
        ses.lv = 'lv01';
        ses.dms = 'dms01';
        ses.dov = 'dov01';
        ses.ct = 1001;
        ses.av = 'av01';
        ses.au = 'au01';
        ses.nca = 'nca01';
        ses.u = 'u01';
        ses.pa = 1003;
        ses.nc = 'nc01';
        ses.an = 'an01';
        ses.pn = 'pn01';
        ses.dll = 'dll01';
        ses.dsdk = 'dsdk01';
        ses.dp = 'dp01';
        sesList.add(ses);

        Test.startTest();
        SessionRestResource.uploadSession(sesList);
        Test.stopTest();
        
        List<App__c> appList = AppRestResource.getRelatedApps();
        
        System.assertEquals(appList.get(0).Label__c, 'an01');
        System.assertEquals(appList.get(0).PackageName__c, 'pn01');
    }
}