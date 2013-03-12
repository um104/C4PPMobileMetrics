package edu.channel4.mm.db.android.database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.channel4.mm.db.android.activity.OnAppDescriptionChangedListener;
import edu.channel4.mm.db.android.activity.OnAttributeDescriptionChangedListener;
import edu.channel4.mm.db.android.activity.OnEventDescriptionChangedListener;
import edu.channel4.mm.db.android.activity.OnEventNameDescriptionChangedListener;
import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.model.description.AttributeDescription;
import edu.channel4.mm.db.android.model.description.EventDescription;
import edu.channel4.mm.db.android.model.description.EventNameDescription;

/**
 * BS temp database. This is just a singleton containing a bunch of ArrayLists
 * of models.
 * 
 * @author girum & mark
 * 
 */
public class TempoDatabase {

	private static TempoDatabase instance = new TempoDatabase();
	
	private List<AppDescription> appDescriptions = new ArrayList<AppDescription>();
	private Set<OnAppDescriptionChangedListener> onAppDescriptionChangedListeners = new HashSet<OnAppDescriptionChangedListener>();
	
	private List<AttributeDescription> attributeDescriptions = new ArrayList<AttributeDescription>();
	private Set<OnAttributeDescriptionChangedListener> onAttributeDescriptionChangedListeners = new HashSet<OnAttributeDescriptionChangedListener>();
	
	private List<EventDescription> eventDescriptions = new ArrayList<EventDescription>();
	private Set<OnEventDescriptionChangedListener> onEventDescriptionChangedListeners =  new HashSet<OnEventDescriptionChangedListener>();
	
	private List<EventNameDescription> eventNameDescriptions = new ArrayList<EventNameDescription>();
	private Set<OnEventNameDescriptionChangedListener> onEventNameDescriptionChangedListeners =  new HashSet<OnEventNameDescriptionChangedListener>();
	
	private TempoDatabase() {
		// singleton (hide constructor)
	}

	public static TempoDatabase getInstance() {
		return instance;
	}
	
	/**
	 * EventDescriptions from here below
	 * @param eventDescriptions
	 */
	public void setEventDescriptions(List<EventDescription> eventDescriptions) {
	   this.eventDescriptions = eventDescriptions;
	   
	   for (OnEventDescriptionChangedListener listener : onEventDescriptionChangedListeners) {
	      listener.onEventDescriptionChanged(this.eventDescriptions);
	   }
	}
	
	public boolean addOnEventDescriptionChangedListener(OnEventDescriptionChangedListener listener) {
	   return onEventDescriptionChangedListeners.add(listener);
	}
	
	public boolean removeOnEventDescriptionChangedListener(OnEventDescriptionChangedListener listener) {
	   return onEventDescriptionChangedListeners.remove(listener);
	}
	
	/**
	 * EventNameDescriptions from here below
	 * @param eventNameDescriptions
	 */
   public void setEventNameDescriptions(List<EventNameDescription> eventNameDescriptions) {
      this.eventNameDescriptions = eventNameDescriptions;
      
      for (OnEventNameDescriptionChangedListener listener : onEventNameDescriptionChangedListeners) {
         listener.onEventNameDescriptionChanged(this.eventNameDescriptions);
      }
   }
   
   public boolean addOnEventNameDescriptionChangedListener(OnEventNameDescriptionChangedListener listener) {
      return onEventNameDescriptionChangedListeners.add(listener);
   }
   
   public boolean removeOnEventNameDescriptionChangedListener(OnEventNameDescriptionChangedListener listener) {
      return onEventNameDescriptionChangedListeners.remove(listener);
   }

	/**
	 * Sets the tempo DB to have a new list of AppDescriptions, notifying all
	 * listeners in the process.
	 * 
	 * @param appDescriptions
	 */
	public void setAppDescriptions(List<AppDescription> appDescriptions) {
		this.appDescriptions = appDescriptions;

		for (OnAppDescriptionChangedListener onAppDescriptionChangedListener : onAppDescriptionChangedListeners) {
			onAppDescriptionChangedListener
					.onAppDescriptionChanged(this.appDescriptions);
		}
	}

	/**
	 * @param onAppDescriptionChangedListener
	 * @return true if this set did not already contain the specified element
	 */
	public boolean addOnAppDescriptionChangedListener(
			OnAppDescriptionChangedListener onAppDescriptionChangedListener) {
		return onAppDescriptionChangedListeners
				.add(onAppDescriptionChangedListener);
	}

	/**
	 * @param onAppDescriptionChangedListener
	 * @return true if this set contained the specified element
	 */
	public boolean removeOnAppDescriptionChangedListener(
			OnAppDescriptionChangedListener onAppDescriptionChangedListener) {
		return onAppDescriptionChangedListeners
				.remove(onAppDescriptionChangedListener);
	}

	/**
	 * Sets the tempo DB to have a new list of AttributeDescriptions, notifying
	 * all listeners in the process.
	 * 
	 * @param attributeDescriptions
	 */
	public void setAttributeDescriptions(
			List<AttributeDescription> attributeDescriptions) {
		this.attributeDescriptions = attributeDescriptions;

		for (OnAttributeDescriptionChangedListener onAttributeDescriptionChangedListener : onAttributeDescriptionChangedListeners) {
			onAttributeDescriptionChangedListener
					.onAttributeDescriptionChanged(this.attributeDescriptions);
		}
	}

	/**
	 * @param onAttributeDescriptionChangedListener
	 * @return true if this set did not already contain the specified element
	 */
	public boolean addOnAttributeDescriptionChangedListener(
			OnAttributeDescriptionChangedListener onAttributeDescriptionChangedListener) {
		return onAttributeDescriptionChangedListeners
				.add(onAttributeDescriptionChangedListener);
	}

	/**
	 * @param onAttributeDescriptionChangedListener
	 * @return true if this set contained the specified element
	 */
	public boolean removeOnAttributeDescriptionChangedListener(
			OnAttributeDescriptionChangedListener onAttributeDescriptionChangedListener) {
		return onAttributeDescriptionChangedListeners
				.remove(onAttributeDescriptionChangedListener);
	}
}
