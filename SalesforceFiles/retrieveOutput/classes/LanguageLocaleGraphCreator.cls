public class LanguageLocaleGraphCreator extends GraphCreator {
	private LanguageLocaleGraphRequest request;
    
    public LanguageLocaleGraphCreator(LanguageLocaleGraphRequest request) {
        this.request = request;
    }
    
    public override Datatable createJSDataTable() {
        CustomGraphRequest cgr = new CustomGraphRequest();
        cgr.setAppLabel(request.getAppLabel());
        cgr.setEventName1('');
        cgr.setAttribName1('LanguageLocale__c');
        
        CustomGraphCreator cgc = new CustomGraphCreator(cgr);
        
        return cgc.createJSDataTable();
    }
}