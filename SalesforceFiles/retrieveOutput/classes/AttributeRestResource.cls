@RestResource(urlMapping='/channel4_attributes/*')

global class AttributeRestResource
{    
    @HttpPost
    global static AttrDescList getAttributes(String appLabel)
    {
        AttrDescList attrList = new AttrDescList();
        
        //JSONGenerator gen = JSON.createGenerator(false);
        
        attrList.addSessionAttr('AndroidIDHash__c');
        //attrList.addSessionAttr('DataType__c'); weird table inconsistency
        attrList.addSessionAttr('DataConnectionType__c');
        attrList.addSessionAttr('DeviceSerialHash__c');
        attrList.addSessionAttr('DeviceCountry__c');
        attrList.addSessionAttr('DeviceManufacturer__c');
        attrList.addSessionAttr('DeviceModel__c');
        attrList.addSessionAttr('DeviceType__c');
        attrList.addSessionAttr('EpochTime__c');
        attrList.addSessionAttr('LanguageLocale__c');
        attrList.addSessionAttr('LocaleCountryCode__c');
        attrList.addSessionAttr('LocalyticsApiKey__c');
        attrList.addSessionAttr('LocalyticsLibraryVersion__c');
        attrList.addSessionAttr('NetworkCarrier__c');
        attrList.addSessionAttr('NetworkCountryCode__c');
        attrList.addSessionAttr('OSVersion__c');
        attrList.addSessionAttr('PersistentStorageCreationTimeSeconds__c');
        attrList.addSessionAttr('SDKCompatibility__c');
        attrList.addSessionAttr('SessionLengthSeconds__c');
        attrList.addSessionAttr('UUID__c');
        
        List<AggregateResult> results = [SELECT Event__r.EventName__c eventname, Key__c key 
                                         FROM Attribute__c 
                                         WHERE Event__r.Session__r.App__r.Label__c = :appLabel
                                         GROUP BY Event__r.EventName__c, Key__c];
        
        
        for (AggregateResult result : results)
        {    
            attrList.addAttrToEvent((String)result.get('eventname'), (String)result.get('key'));
        }
        
        return attrList;
        
        /*gen.writeStartArray();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','AndroidIDHash__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','DataType__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','DataConnectionType__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','DeviceSerialHash__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','DeviceCountry__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','DeviceManufacturer__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','DeviceModel__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','DeviceType__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','EpochTime__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','LanguageLocale__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','LocaleCountryCode__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','LocalyticsApiKey__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','LocalyticsLibraryVersion__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','NetworkCarrier__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name',attrList.addSessionAttr('LocaleCountryCode__c'););
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','OSVersion__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','PersistentStorageCreationTimeSeconds__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','SDKCompatibility__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','SessionLengthSeconds__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        gen.writeStartObject();
        gen.writeStringField('attrib_name','UUID__c');
        gen.writeStringField('event_name', '');
        gen.writeEndObject();
        
        List<AggregateResult> results = [SELECT Event__r.EventName__c eventname, Key__c key 
                                         FROM Attribute__c 
                                         GROUP BY Event__r.EventName__c, Key__c];
        
        for (AggregateResult result : results)
        {
            gen.writeStartObject();
            gen.writeStringField('attrib_name', (String)result.get('key'));
            gen.writeStringField('event_name', (String)result.get('eventname'));
            gen.writeEndObject();
        }
        
        gen.writeEndArray();
        */
        //RestContext.response.addHeader('Content-Type', 'application/json');
        //RestContext.response.responseBody = Blob.valueOf(gen.getAsString());
        
    }
}