@RestResource(urlMapping='/channel4_apps/*')
global class AppRestResource 
{   
    @HttpGet
    global static List<App__c> getRelatedApps() {
        List<App__c> appList = [SELECT Label__c, PackageName__c FROM App__c];
    
        return appList;
    }
}