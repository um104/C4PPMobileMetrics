package edu.channel4.mm.db.android.model.callback;

import java.util.List;

import edu.channel4.mm.db.android.model.description.AttributeDescription;

public interface AttributeDescriptionCallback {

	/**
	 * Called when an Attribute Description in the database is changed.
	 */
	public void onAttributeDescriptionChanged(List<AttributeDescription> newAttributeDescriptions);
}
