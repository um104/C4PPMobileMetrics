@isTest
global class EventDescTest{
    static testMethod void testEventDesc() {
    	EventDesc ed = new EventDesc();
        
        ed.setName('Application1');
        ed.addAttr('one');
        ed.addAttr('two');
        ed.addAttr('three');
        List<String> attr = ed.getEAttrs();
        
        Test.startTest();
        System.assertEquals('Application1', ed.getName());
        
        System.assertEquals('one', attr.get(0));
        System.assertEquals('two', attr.get(1));
        System.assertEquals('three', attr.get(2));
        Test.stopTest();
    
    }
    
    
}