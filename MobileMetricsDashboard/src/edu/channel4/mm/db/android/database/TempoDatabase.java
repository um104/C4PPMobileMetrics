package edu.channel4.mm.db.android.database;

import java.util.ArrayList;
import java.util.List;

import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.model.description.AttributeDescription;
import edu.channel4.mm.db.android.model.description.EventDescription;
import edu.channel4.mm.db.android.model.description.EventNameDescription;
import edu.channel4.mm.db.android.model.graph.Graph;

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
	private List<EventDescription> eventDescriptions = new ArrayList<EventDescription>();
	private List<EventNameDescription> eventNameDescriptions = new ArrayList<EventNameDescription>();
	
	private Graph graph;
	
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
	
	public void setEventDescriptions(List<EventDescription> eventDescriptions) {
	   this.eventDescriptions = eventDescriptions;
	}
	
	public void setEventNameDescriptions(List<EventNameDescription> eventNameDescriptions) {
	   this.eventNameDescriptions = eventNameDescriptions;
	}

	/**
	 * @param onAttributeDescriptionChangedListener
	 * @return true if this set did not already contain the specified element
	 */
	public void setGraph(Graph graph) {
		this.graph = graph;
	}
}
