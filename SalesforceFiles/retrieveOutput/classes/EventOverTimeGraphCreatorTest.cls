/**
	Datetime has addDays(), addHours(), addMinutes() can use negitive time to go back
*/

@isTest
global class EventOverTimeGraphCreatorTest
{
    static testMethod void testEventOverTimeGraphCreator() 
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
        Event__c death3 = new Event__c();

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
        session2.Epochtime__c = 100;
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
        session3.Epochtime__c = 100;
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
        death1.WallTimeSeconds__c = 11;
        death1.EventTime__c = DateTime.newInstance(11);
        insert death1;
        
        death2.Session__c = session2.id;
        death2.EventName__c = 'Death';
        death2.EventUUID__c = '100';
        death2.WallTimeSeconds__c = 12;
        death2.EventTime__c = DateTime.newInstance(12);
        insert death2;
        
        death3.Session__c = session3.id;
        death3.EventName__c = 'Death';
        death3.EventUUID__c = '100';
        death3.WallTimeSeconds__c = 13;
        death3.EventTime__c = DateTime.newInstance(13);
        insert death3;
        
        levelUp1.Session__c = session1.id;
        levelUp1.EventName__c = 'Level Up';
        levelUp1.EventUUID__c = '100';
        levelUp1.WallTimeSeconds__c = 21;
        levelUp1.EventTime__c = DateTime.newInstance(21);
        insert levelUp1;
        
        levelUp2.Session__c = session2.id;
        levelUp2.EventName__c = 'Level Up';
        levelUp2.EventUUID__c = '100';
        levelUp2.WallTimeSeconds__c = 22;
        levelUp2.EventTime__c = DateTime.newInstance(22);
        insert levelUp2;
        
        levelUp3.Session__c = session3.id;
        levelUp3.EventName__c = 'Level Up';
        levelUp3.EventUUID__c = '100';
        levelUp3.WallTimeSeconds__c = 23;
        levelUp3.EventTime__c = DateTime.newInstance(23);
        insert levelUp3;
        
        EventOverTimeGraphRequest eot1 = new EventOverTimeGraphRequest();
        eot1.setEventName('Death');
        eot1.setAppLabel(app.Label__c);
        eot1.setInterval('DAY');
        
        EventOverTimeGraphRequest eot2 = new EventOverTimeGraphRequest();
        eot2.setEventName('Level Up');
        eot2.setAppLabel(app.Label__c);
        eot2.setInterval('WEEK');
        
        EventOverTimeGraphRequest eot3 = new EventOverTimeGraphRequest();
        eot3.setEventName('Death');
        eot3.setAppLabel(app.Label__c);
        eot3.setInterval('MONTH');
        
        EventOverTimeGraphCreator gc1;
        EventOverTimeGraphCreator gc2;
        EventOverTimeGraphCreator gc3;
        
        Datatable dt1, dt2, dt3;
        
        Test.startTest();
        gc1 = new EventOverTimeGraphCreator(eot1);
        gc2 = new EventOverTimeGraphCreator(eot2);
        gc3 = new EventOverTimeGraphCreator(eot3);
        
        dt1 = gc1.createJSDatatable();
        dt2 = gc2.createJSDatatable();
        dt3 = gc3.createJSDatatable();
        
		//System.debug('MINE and MINE: ' + dt2);
		System.assertEquals(24, dt1.getRowCount());
        System.assertEquals(0, dt1.getValueAt(1, 1));
        
        for(Integer i = 0; i < dt1.getRowCount(); i++)
        {
            System.assertEquals(0, dt1.getValueAt(i, 1));
        }
        
        System.assertEquals(7, dt2.getRowCount());
        
        for(Integer i = 0; i < dt2.getRowCount(); i++)
        {
            System.assertEquals(0, dt2.getValueAt(i, 1));
        }
        
        
        System.assertEquals(30, dt3.getRowCount());
        
        for(Integer i = 0; i < dt3.getRowCount(); i++)
        {
            System.assertEquals(0, dt3.getValueAt(i, 1));
        }
        
        /*
        for(Integer i = 0; i < dt2.getRowCount(); i++)
        {
            Object score = dt2.getValueAt(i, 0);
            System.assert(score instanceof String);
            if(score == '100')
            {
                System.assertEquals(dt2.getValueAt(i, 1), 1);
            }
            else if(score == '200')
            {
                System.assertEquals(dt2.getValueAt(i, 1), 1);
            }
            else
            {
                System.assert(false);
            }
        }*/
        Test.stopTest();
        
    }    
}


        /*
        System.Debug('MY SUPERN STUFF    ' + eot1.getAppLabel());
        System.Debug('MY SUPERN STUFF    ' + eot1.getEventName());
        System.Debug('MY SUPERN STUFF    ' + eot1.getStartTime());
        System.Debug('MY SUPERN STUFF    ' + eot1.getEndTime());
        
        System.assertEquals(24, dt1.getRowCount()); //2

        Object testTime = dt1.getValueAt(0, 1);
        //System.assert(testTime instanceof Long);
        System.debug(testTime);
        //System.assertEquals(11, testTime);
        
        for(Integer i = 0; i < dt1.getRowCount(); i++) {
        	System.debug('BITCHES AND WHORES:' + dt1.getValueAt(i, 1));    
        }
        */