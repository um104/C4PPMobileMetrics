@RestResource(urlMapping='/channel4_upload/*')
global class SessionRestResource {

    @HttpPost
    global static String uploadSession(HeaderBlob hblob) {
        
        // have this method take in a "HeaderBlob" object param, and a "List<Event>" param
        
        // pa = epoch time
        // u = UUID
        // attrs = attributes
        
        Session__c session = new Session__c();
        try {
            session.Epoch_time__c = hblob.pa;
            session.UUID__c = hblob.u;
            session.Android_ID_SHA__c = hblob.attrs.du;
            session.App_Version__c = hblob.attrs.av;
            session.Data_Connection_Type__c = hblob.attrs.dac;
            session.Device_Country__c = hblob.attrs.dc;
            session.Device_Manufacturer__c = hblob.attrs.dma;
        	session.Device_Model__c = hblob.attrs.dmo;
        	session.Device_Type__c = hblob.attrs.dp;
        	session.Language_Locale__c = hblob.attrs.dll;
        	session.Locale_Country_Code__c = hblob.attrs.dlc;
        	session.Network_Carrier__c = hblob.attrs.nca;
            session.Network_Country_Code__c = hblob.attrs.nc;
            session.OS_Version__c = hblob.attrs.dov;
            session.SDK_Compatibility__c = hblob.attrs.dsdk;
        
            insert session;
        }
        catch(Exception e)
        {
            System.Debug('damn');
        }
        
        
        return 'OKAY!';
    }
    
    global class SessionOpen {
      	public String dt;
        public Integer ct;
        public String u;
        public Integer nth;
    }
    
    global class HeaderBlob {
        public Integer seq;
        public String dt;
        public String u; //u = UUID
        public Integer pa; //pa = epoch time
        public Attributes attrs; //attrs = attributes
    }

    global class Attributes {
        public String dt; // datatype for JSON object
        public String dc; // device country --
        public String dac; // data connection type --
        public String du; // android id SHA hash --
        public String dmo; // device model --
        public String dlc; // country code of current locale settings --
        public String dma; // device manufacturer --
        public String lv; // Localytics version
        public String dms; // devices serial number
        public String dov; // OS version --
        public String av; // version name of application --
        public String au; // localytics API key
        public String nca; // network carrier --
        public String nc; // key network country --
        public String dll; // language locale --
        public String dsdk; // SDK compatability --
        public String dp; // device (will always be "android") --
    }
    
}