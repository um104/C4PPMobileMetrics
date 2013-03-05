@isTest
public class CustomGraphCreatorTest{
    static testMethod void testCreateJSDatatable() 
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
        
        CustomGraphRequest cr1 = new CustomGraphRequest();
        cr1.setAttribName1('Name');
        cr1.setEventName1('Level Up');
        cr1.setAppLabel(app.Label__c);
        
        CustomGraphRequest cr2 = new CustomGraphRequest();
        cr2.setAttribName1('Score');
        cr2.setEventName1('Death');
        cr2.setAppLabel(app.Label__c);
        
        CustomGraphRequest cr3 = new CustomGraphRequest();
        cr3.setAttribName1('DeviceModel__c');
        cr3.setEventName1(null);
        cr3.setAppLabel(app.Label__c);
        
        CustomGraphCreator gc1;
        CustomGraphCreator gc2;
        CustomGraphCreator gc3;
        
        Datatable dt1, dt2, dt3;
        
        Test.startTest();
        gc1 = new CustomGraphCreator(cr1);
        gc2 = new CustomGraphCreator(cr2);
        gc3 = new CustomGraphCreator(cr3);
        
        
        dt1 = gc1.createJSDatatable();
        dt2 = gc2.createJSDatatable();
        dt3 = gc3.createJSDatatable();
        
        System.assertEquals(2, dt1.getRowCount());
        for(Integer i = 0; i < dt1.getRowCount(); i++)
        {
            Object name = dt1.getValueAt(i, 0);
            System.assert(name instanceof String);
            if(name == 'Brian')
            {
                System.assertEquals(dt1.getValueAt(i, 1), 2);
            }
            else if (name == 'Girum')
            {
                System.assertEquals(dt1.getValueAt(i, 1), 1);
            }
            else 
            {
                //this means there is an invalid row in the table
                System.assert(false);
            }
        }
        
        System.assertEquals(2, dt2.getRowCount());
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
        }
        
        System.assertEquals(2, dt3.getRowCount());
        for(Integer i = 0; i < dt3.getRowCount(); i++)
        {
            Object deviceModel = dt3.getValueAt(i, 0);
            System.assert(deviceModel instanceof String);
            if(deviceModel == 'Galaxy')
            {
                System.assertEquals(dt3.getValueAt(i, 1), 1);
            }
            else if(deviceModel == 'Not Galaxy')
            {
                System.assertEquals(dt3.getValueAt(i, 1), 2);
            }
            else
            {
                System.assert(false);
            }
        }
        Test.stopTest();
    }
}