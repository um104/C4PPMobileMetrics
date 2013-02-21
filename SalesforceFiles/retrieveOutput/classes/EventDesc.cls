public class EventDesc
{
    private String name;
    private List<String> eAttrs;
   
    public EventDesc()
    {
        this.eAttrs = new List<String>();
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void addAttr(String attrName)
    {
        this.eAttrs.add(attrName);
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public List<String> getEAttrs()
    {
     	return eAttrs;   
    }
}