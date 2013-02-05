@RestResource(urlMapping='/channel4_apps/*')
global class AppRestResource 
{
    @HttpGet
    global static List<AppTable__c> getRelatedApps()
    {
        List<AppTable__c> appList = [select AppLabel__c, PackageName__c, Version__c from AppTable__c];        
        return appList;
    }
}