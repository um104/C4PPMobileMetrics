@RestResource(urlMapping='/channel4_apps/*')
global class AppRestResource 
{
    @HttpGet
    global static List<AppTable__c> getRelatedApps()
    {
        List<AppTable__c> appList = [select AppId__c, AppName__c from AppTable__c];        
        return appList;
    }
}