package edu.channel4.mm.db.android.model.description;

import java.util.List;

public class EventDescription extends AttributeDescription {

	private List<EventAttributeDescription> eventAttributes;

	public EventDescription(String name,
			List<EventAttributeDescription> eventAttributes) {
		super(name);
		this.eventAttributes = eventAttributes;
	}

}
