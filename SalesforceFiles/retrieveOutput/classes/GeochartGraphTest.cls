@isTest
public class GeochartGraphTest
{
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
        session1.DeviceCountry__c = 'Murica';
        session1.DeviceManufacturer__c = 'Samsung';
        session1.DeviceModel__c = 'Galaxy';
        session1.DeviceType__c = 'phone1';
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
        session2.DeviceCountry__c = 'Murica';
        session2.DeviceManufacturer__c = 'Samsung';
        session2.DeviceModel__c = 'Not Galaxy';
        session2.DeviceType__c = 'phone1';
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
        session3.DeviceCountry__c = 'Italy';
        session3.DeviceManufacturer__c = 'Samsung';
        session3.DeviceModel__c = 'Not Galaxy';
        session3.DeviceType__c = 'phone2';
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
        
        GeochartGraphRequest gr1 = new GeochartGraphRequest();
        gr1.setAppLabel(app.Label__c);
        
        GeochartGraphCreator gc1;
        DataTable dt1;
        
        Test.startTest();
        gc1 = new GeochartGraphCreator(gr1);
        dt1 = gc1.createJSDatatable();
        
        System.assertEquals(dt1.getRowCount(), 2);
        System.assertEquals(dt1.getValueAt(0, 0), 'Italy');
        System.assertEquals(dt1.getValueAt(0, 1), 1);
        System.assertEquals(dt1.getValueAt(1, 0), 'Murica');
        System.assertEquals(dt1.getValueAt(1, 1), 2);
        
        Test.stopTest();
    }
}