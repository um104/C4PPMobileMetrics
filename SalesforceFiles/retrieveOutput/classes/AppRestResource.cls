@RestResource(urlMapping='/channel4_apps/*')
global class AppRestResource 
{   
    @HttpGet
    global static List<App__c> getRelatedApps() {
        List<App__c> appList = [SELECT Label__c, PackageName__c, Version__c, Name FROM App__c];
    
        return appList;
    }
}