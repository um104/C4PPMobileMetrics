@RestResource(urlMapping='/channel4_upload/*')

global class SessionRestResource 
{
    @HttpPost
    global static String uploadSession(List<SessionClass> sessions)
    {        
        try 
        {
            for(SessionClass s : sessions) 
            {
                App__c app = new App__c();
                
                List<App__c> appList = [SELECT Id FROM App__c WHERE Label__c = :s.an AND PackageName__c = :s.pn AND Version__c = :s.av];
                if(appList.size() == 0)
                {
                    app.Label__c = s.an;
                    app.PackageName__c = s.pn;
                    app.Version__c = s.av;
                
                    insert app;
                }
                else
                {
                    app = appList[0];
                }
                
                Session__c session = new Session__c();
                session.App__c = app.id;
                session.AndroidIDHash__c = s.du;
                session.DataType__c = s.dt;
                session.DataConnectionType__c = s.dac;
                session.DeviceSerialHash__c = s.dms;
                session.DeviceCountry__c = s.dc;
                session.DeviceManufacturer__c = s.dma;
                session.DeviceModel__c = s.dmo;
                session.DeviceType__c = s.dp;
                session.EpochTime__c = s.ct;
                session.LanguageLocale__c = s.dll;
                session.LocaleCountryCode__c = s.dlc;
                session.LocalyticsApiKey__c = s.au;
                session.LocalyticsLibraryVersion__c = s.lv;
                session.NetworkCarrier__c = s.nca;
                session.NetworkCountryCode__c = s.nc;
                session.OSVersion__c = s.dov;
                session.PersistentStorageCreationTimeSeconds__c = s.pa;
                session.SDKCompatibility__c = s.dsdk;
                session.SessionLengthSeconds__c = s.ctl;
                session.UUID__c = s.u;
                            
                insert session;
            
                for(EventClass e : s.events)
                {
                    Event__c event = new Event__c();
                    event.Session__c = session.id;
                    event.EventName__c = e.n;
                    event.EventUUID__c = e.u;
                    event.WallTimeSeconds__c = e.ct;
                
                    insert event;
            
                    for(EventAttributeClass a : e.attrs)
                    {
                        Attribute__c attribute = new Attribute__c();
                        attribute.Event__c = event.id;
                        attribute.Key__c = a.key.subString(4, a.key.length());
                        attribute.Value__c = a.value;
                        attribute.Type__c = a.key.subString(0, 3);
                       
                        insert attribute;
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.Debug(e);
        }
    
	    return 'DONE!';
    }
      
    global class SessionClass
    {
        public String dt;
        public String dc;
        public String dac;
        public String du;
        public String dmo;
        public List<EventClass> events;
        public String dlc;
        public Integer ctl;
        public String dma;
        public String lv;
        public String dms;
        public String dov;
        public Integer ct;
        public String av;
        public String au;
        public String nca;
        public String u;
        public Integer pa;
        public String nc;
        public String an;
        public String pn;
        public String dll;
        public String dsdk;
        public String dp;
    }
    
    global class EventClass
    {
        public String n;
        public String su;
        public String dt;
        public String u;
        public List<EventAttributeClass> attrs;
        public Integer ct;
    }
    
    global class EventAttributeClass
    {
        public String value;
        public String key;
    }
}