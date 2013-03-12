package edu.channel4.mm.db.android.database;

import java.util.ArrayList;
import java.util.List;

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
	 * Sets the tempo DB to have a new list of AppDescriptions, notifying all
	 * listeners in the process.
	 * 
	 * @param appDescriptions
	 */
	public void setAppDescriptions(List<AppDescription> appDescriptions) {
		this.appDescriptions = appDescriptions;
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
	}

	/**
	 * @param onAttributeDescriptionChangedListener
	 * @return true if this set did not already contain the specified element
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
}
