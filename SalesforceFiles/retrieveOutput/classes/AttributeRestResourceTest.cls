@isTest
public class AttributeRestResourceTest
{
    
    //APEX List class does not have a contains method, hence contains method <unsmileyface>
    public static boolean contains(List<String> itemList, String item) {
        for(String check : itemList) {
            if(check.equals(item)) {
             	return true;   
            }
        }
        
        return false;
    }
    
    static testMethod void testGetRelatedApps() 
    {
        App__c app = new App__c();
        Session__c session1 = new Session__c();
        Session__c session2 = new Session__c();
        Session__c session3 = new Session__c();
        
        Event__c levelUp1 = new Event__c();
        Event__c levelUp2 = new Event__c();
        Event__c levelUp3 = new Event__c();
        
        Event__c death1 = new Event__c();
        Event__c death2 = new Event__c();
        
        Attribute__c levelUpScore1 = new Attribute__c();
        Attribute__c levelUpName1 = new Attribute__c();
        Attribute__c levelUpScore2 = new Attribute__c();
        Attribute__c levelUpName2 = new Attribute__c();
        Attribute__c levelUpScore3 = new Attribute__c();
        Attribute__c levelUpName3 = new Attribute__c();
        Attribute__c deathScore1 = new Attribute__c();
        Attribute__c deathTime1 = new Attribute__c();
        Attribute__c deathScore2 = new Attribute__c();
        Attribute__c deathTime2 = new Attribute__c();
        
        app.Label__c = 'theApp';
        app.PackageName__c = 'theAppPackage';
        app.Version__c = '1';
        app.Name = 'name';
        insert app;
        
        session1.AndroidIDHash__c = 'abc';
        session1.App__c = app.id;
        session1.DataType__c = 'data';
        session1.DataConnectionType__c = 'good';
        session1.DeviceSerialHash__c = 'abc';
        session1.DeviceCountry__c = 'murica';
        session1.DeviceManufacturer__c = 'Samsung';
        session1.DeviceModel__c = 'Galaxy';
        session1.DeviceType__c = 'phone';
        session1.Epochtime__c = 100;
        session1.LanguageLocale__c = 'eng';
        session1.LocaleCountryCode__c = '111';
        session1.LocalyticsApiKey__c = '111';
        session1.LocalyticsLibraryVersion__c = '111';
        session1.NetworkCarrier__c = 'verizon';
        session1.NetworkCountryCode__c = '111';
        session1.OSVersion__c = '111';
        session1.PersistentStorageCreationTimeSeconds__c = 10;
        session1.SDKCompatibility__c = '111';
        session1.SessionLengthSeconds__c = 10;
        session1.UUID__c = '12345678';
        insert session1;
        
        session2.AndroidIDHash__c = 'abc';
        session2.App__c = app.id;
        session2.DataType__c = 'data';
        session2.DataConnectionType__c = 'good';
        session2.DeviceSerialHash__c = 'abc';
        session2.DeviceCountry__c = 'murica';
        session2.DeviceManufacturer__c = 'Samsung';
        session2.DeviceModel__c = 'Not Galaxy';
        session2.DeviceType__c = 'phone';
        session2.EpochTime__c = 100;
        session2.LanguageLocale__c = 'eng';
        session2.LocaleCountryCode__c = '111';
        session2.LocalyticsApiKey__c = '111';
        session2.LocalyticsLibraryVersion__c = '111';
        session2.NetworkCarrier__c = 'verizon';
        session2.NetworkCountryCode__c = '111';
        session2.OSVersion__c = '111';
        session2.PersistentStorageCreationTimeSeconds__c = 10;
        session2.SDKCompatibility__c = '111';
        session2.SessionLengthSeconds__c = 10;
        session2.UUID__c = '12345678';
        insert session2;
        
        session3.AndroidIDHash__c = 'abc';
        session3.App__c = app.id;
        session3.DataType__c = 'data';
        session3.DataConnectionType__c = 'good';
        session3.DeviceSerialHash__c = 'abc';
        session3.DeviceCountry__c = 'murica';
        session3.DeviceManufacturer__c = 'Samsung';
        session3.DeviceModel__c = 'Not Galaxy';
        session3.DeviceType__c = 'phone';
        session3.EpochTime__c = 100;
        session3.LanguageLocale__c = 'eng';
        session3.LocaleCountryCode__c = '111';
        session3.LocalyticsApiKey__c = '111';
        session3.LocalyticsLibraryVersion__c = '111';
        session3.NetworkCarrier__c = 'verizon';
        session3.NetworkCountryCode__c = '111';
        session3.OSVersion__c = '111';
        session3.PersistentStorageCreationTimeSeconds__c = 10;
        session3.SDKCompatibility__c = '111';
        session3.SessionLengthSeconds__c = 10;
        session3.UUID__c = '12345678';
        insert session3;
        
        death1.Session__c = session1.id;
        death1.EventName__c = 'Death';
        death1.EventUUID__c = '100';
        death1.WallTimeSeconds__c = 100;
        insert death1;
        
        death2.Session__c = session2.id;
        death2.EventName__c = 'Death';
        death2.EventUUID__c = '100';
        death2.WallTimeSeconds__c = 100;
        insert death2;
        
        levelUp1.Session__c = session1.id;
        levelUp1.EventName__c = 'Level Up';
        levelUp1.EventUUID__c = '100';
        levelUp1.WallTimeSeconds__c = 100;
        insert levelUp1;
        
        levelUp2.Session__c = session2.id;
        levelUp2.EventName__c = 'Level Up';
        levelUp2.EventUUID__c = '100';
        levelUp2.WallTimeSeconds__c = 100;
        insert levelUp2;
        
        levelUp3.Session__c = session3.id;
        levelUp3.EventName__c = 'Level Up';
        levelUp3.EventUUID__c = '100';
        levelUp3.WallTimeSeconds__c = 100;
        insert levelUp3;
        
        levelUpScore1.Event__c = levelUp1.id;
        levelUpScore1.Value__c = '100';
        levelUpScore1.Key__c = 'Score';
        insert levelUpScore1;
        
        levelUpName1.Event__c = levelUp1.id;
        levelUpName1.Value__c = 'Brian';
        levelUpName1.Key__c = 'Name';
        insert levelUpName1;
        
        levelUpScore2.Event__c = levelUp2.id;
        levelUpScore2.Value__c = '50';
        levelUpScore2.Key__c = 'Score';
        insert levelUpScore2;
        
        levelUpName2.Event__c = levelUp2.id;
        levelUpName2.Value__c = 'Brian';
        levelUpName2.Key__c = 'Name';
        insert levelUpName2;
        
        levelUpScore3.Event__c = levelUp3.id;
        levelUpScore3.Value__c = '2';
        levelUpScore3.Key__c = 'Score';
        insert levelUpScore3;
        
        levelUpName3.Event__c = levelUp3.id;
        levelUpName3.Value__c = 'Girum';
        levelUpName3.Key__c = 'Name';
        insert levelUpName3;
        
        deathScore1.Event__c = death1.id;
        deathScore1.Value__c = '100';
        deathScore1.Key__c = 'Score';
        insert deathScore1;
        
        deathTime1.Event__c = death1.id;
        deathTime1.Value__c = '10';
        deathTime1.Key__c = 'Time';
        insert deathTime1;
        
        deathScore2.Event__c = death2.id;
        deathScore2.Value__c = '200';
        deathScore2.Key__c = 'Score';
        insert deathScore2;
        
        deathTime2.Event__c = death2.id;
        deathTime2.Value__c = '10';
        deathTime2.Key__c = 'Time';
        insert deathTime2;
        
        /*GraphRequest gr1 = new GraphRequest();
        gr1.setGraphType(GraphRequest.GraphType.PIE);
        gr1.setFirstAttrib('Name');
        gr1.setFirstAttribEvent('Level Up');
        gr1.setAppName(app.Name);
        
        GraphRequest gr2 = new GraphRequest();
        gr2.setGraphType(GraphRequest.GraphType.PIE);
        gr2.setFirstAttrib('Score');
        gr2.setFirstAttribEvent('Death');
        gr2.setAppName(app.Name);
        
        GraphRequest gr3 = new GraphRequest();
        gr3.setGraphType(GraphRequest.GraphType.PIE);
        gr3.setFirstAttrib('DeviceModel__c');
        gr3.setFirstAttribEvent(null);
        gr3.setAppName(app.Name);
        
        PieGraphCreator gc = new PieGraphCreator();
        Datatable dt1, dt2, dt3;*/

        Test.startTest();
        AttrDescList attDecList = AttributeRestResource.getAttributes('theApp');
        
        List<String> sAttrs = attDecList.getSessionAttrList();
        List<EventDesc> eDesc = attDecList.getEventDescList(); 

        //Session Test
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'AndroidIDHash__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'DataConnectionType__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'DeviceSerialHash__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'DeviceCountry__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'DeviceManufacturer__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'DeviceModel__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'DeviceType__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'EpochTime__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'LanguageLocale__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'LocaleCountryCode__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'LocalyticsApiKey__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'LocalyticsLibraryVersion__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'NetworkCarrier__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'NetworkCountryCode__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'OSVersion__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'PersistentStorageCreationTimeSeconds__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'SDKCompatibility__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'SessionLengthSeconds__c'));
        System.assertEquals(true, AttributeRestResourceTest.contains(sAttrs, 'UUID__c'));
        
        //EventDescription Test
        /*System.assertEquals('Death', eDesc.get(0).getName());
        System.assertEquals('Time', eDesc.get(0).getEAttrs().get(0));
        System.assertEquals('Score', eDesc.get(0).getEAttrs().get(1));
        
        System.assertEquals('Level Up', eDesc.get(1).getName());
        System.assertEquals('Name', eDesc.get(1).getEAttrs().get(0));
        System.assertEquals('Score', eDesc.get(1).getEAttrs().get(1));*/
        Test.stopTest();
        
        
        /*System.assertEquals('[ {\n' + 
                            ' "attrib_name" : "Android_ID_Hash__c",\n' + 
                            ' "event_name" : ""\n' + 
                            '}, {\n' + 
                            ' "attrib_name" : "Data_Type__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Data Connection Type__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Device_Serial_Hash__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Device_Country__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Device_Manufacturer__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Device_Model__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Device_Type__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Epoch_time__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Language_Locale__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Locale_Country_Code__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Localytics_Api_Key__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Localytics_Library_Version__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Network_Carrier__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Network_Country_Code__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "OS_Version__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Persistent_Storage_Creation_Time_Seconds__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "SDK_Compatibility__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "Session_Length_Seconds__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "UUID__c",\n' + 
                            ' "event_name" : ""\n' +
                            '}, {\n' + 
                            ' "attrib_name" : "key01",\n' + 
                            ' "event_name" : "n01"\n' +
                            '} ]', AttributeRestResource.getJSONString());*/
    }
}




        /*
        System.RestContext.request = new RestRequest();
        RestContext.request.requestURI = '/feedpost/test01';
        RestContext.request.addHeader('email', 'test01@some.org');
        
        List<SessionRestResource.EventAttributeClass> eveattList = new List<SessionRestResource.EventAttributeClass>();
        List<SessionRestResource.EventClass> eveList = new List<SessionRestResource.EventClass>();
        List<SessionRestResource.SessionClass> sesList = new List<SessionRestResource.SessionClass>();
        
        // Creating 2 EventAttributeClass and add them to the list
        SessionRestResource.EventAttributeClass eveatt = new SessionRestResource.EventAttributeClass();
        eveatt.value = 'value01';
        eveatt.key = 'key01';
        eveattList.add(eveatt);
        
        // Creating 2 EventClass and add them to the list
        SessionRestResource.EventClass eve = new SessionRestResource.EventClass();
        eve.n = 'n01';
        eve.su = 'su01';
        eve.dt = 'dt01';
        eve.u = 'u01';
        eve.attrs = eveattList;
        eve.ct = 1001001;
        eveList.add(eve);
        
        // Assign value to SessionClass
        SessionRestResource.SessionClass ses = new SessionRestResource.SessionClass();
        ses.dt = 'dt01';
        ses.dc = 'dc01';
        ses.dac = 'dac01';
        ses.du = 'du01';
        ses.dmo = 'dmo01';
        ses.events = eveList;
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
		*/