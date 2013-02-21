global class AttrDescList {
	private List<String> sAttrs;
    private List<EventDesc> eDescs;
    
    public AttrDescList() {
    	sAttrs = new List<String>();
        eDescs = new List<EventDesc>();
    }
    
    public void addSessionAttr(String sAttr) {
        this.sAttrs.add(sAttr);
    }
    
    public List<String> getSessionAttrList() {
    	return sAttrs;   
    }
    
    public void addEventDesc(EventDesc eDesc) {
     	this.eDescs.add(eDesc);   
    }
    
    public List<EventDesc> getEventDescList() {
        return eDescs;
    }
    
    public void addAttrToEvent(String evtName, String attrName)
    {
        EventDesc foundEvt;
        for(EventDesc evt : this.eDescs)
        {
            if(evt.getName() == evtName)
            {
                foundEvt = evt;
            }
        }
        
        if(foundEvt == null)
        {
            foundEvt = new EventDesc();
            foundEvt.setName(evtName);
            this.eDescs.add(foundEvt);
        }
        
        foundEvt.addAttr(attrName);   
    }
}