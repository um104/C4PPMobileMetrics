package edu.channel4.mm.db.android.model.request;

/**
 * This interface describes a GraphRequest that has a parameter that needs to be
 * set by the user before it can be sent. In this case, the parameter is an
 * Attribute. {@link EditGraphRequestActivity} will use reflection to detect
 * what parameters a certain GraphRequest has.
 * 
 * @author mlerner
 */
public interface HasAttributeParameter {

   public void setAttributeName(String attribName);

   public String getAttributeName();
   
   public void setEventName(String eventName);
   
   public String getEventName();

}
