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
                Session__c session = new Session__c();
                session.Android_ID_Hash__c = s.du;
                session.App_Label__c = s.an;
                session.App_Package_Name__c = s.pn;
                session.App_Version__c = s.av;
                session.Data_Type__c = s.dt;
                session.Data_Connection_Type__c = s.dac;
                session.Device_Serial_Hash__c = s.dms;
                session.Device_Country__c = s.dc;
                session.Device_Manufacturer__c = s.dma;
                session.Device_Model__c = s.dmo;
                session.Device_Type__c = s.dp;
                session.Epoch_time__c = s.ct;
                session.Language_Locale__c = s.dll;
                session.Locale_Country_Code__c = s.dlc;
                session.Localytics_Api_Key__c = s.au;
                session.Localytics_Library_Version__c = s.lv;
                session.Network_Carrier__c = s.nca;
                session.Network_Country_Code__c = s.nc;
                session.OS_Version__c = s.dov;
                session.Persistent_Storage_Creation_Time_Seconds__c = s.pa;
                session.SDK_Compatibility__c = s.dsdk;
                session.Session_Length_Seconds__c = s.ctl;
                session.UUID__c = s.u;
                            
                insert session;
                
                AppTable__c appTable = new AppTable__c();
                appTable.AppLabel__c = s.an;
                appTable.PackageName__c = s.pn;
                appTable.Version__c = s.av;
                
                insert appTable;
            
                for(EventClass e : s.events)
                {
                    Event__c event = new Event__c();
                    event.Session__c = session.id;
                    event.Data_Type__c = e.dt;
                    event.Event_Name__c = e.n;
                    event.Event_UUID__c = e.u;
                    event.Session_UUID__c = e.su;
                    event.Wall_Time_Seconds__c = e.ct;
                
                    insert event;
            
                    for(EventAttributeClass a : e.attrs)
                    {
                        Attribute__c attribute = new Attribute__c();
                        attribute.Event__c = event.id;
                        attribute.Value__c = a.value;
                        attribute.Key__c = a.key;
                       
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