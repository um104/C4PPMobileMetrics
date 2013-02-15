@RestResource(urlMapping='/channel4_apps/*')

global class AppRestResource 
{
    @HttpGet
    global static String getRelatedApps()
    {
        List<AggregateResult> appList = [SELECT Label__c, PackageName__c, Version__c FROM App__c GROUP BY Label__c, PackageName__c, Version__c];
        
        JSONGenerator gen = JSON.createGenerator(true);
        
        gen.writeStartArray();
        for(AggregateResult a : appList)
        {
            gen.writeStartObject();
            gen.writeStringField('Label__c', (String)a.get('Label__c'));
            gen.writeStringField('PackageName__c', (String)a.get('PackageName__c'));
            gen.writeStringField('Version__c', (String)a.get('Version__c'));
            gen.writeEndObject();
        }
        gen.writeEndArray();
        
        return gen.getAsString();
        
        //return JSON.serializePretty(appList);
    }
}