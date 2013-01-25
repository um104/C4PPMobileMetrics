public class AppRestResource 
{
    public String getRelatedApps()
    {
        list<AppTable__c> appList = [select AppId__c, AppName__c from AppTable__c];
        
        String returnString = '{[';
        for (AppTable__c instance: appList)
        {
            returnString += '{Id:' + instance.AppId__c + ';name:' + instance.AppName__c + ';},';
        }
        returnString = (returnString.substring(0, returnString.length() - 1) + ']}');
        
        return returnString;
    }
}