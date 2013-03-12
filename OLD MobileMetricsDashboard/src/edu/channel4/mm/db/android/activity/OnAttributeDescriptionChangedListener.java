package edu.channel4.mm.db.android.activity;

import java.util.List;

import edu.channel4.mm.db.android.model.description.AttributeDescription;

public interface OnAttributeDescriptionChangedListener {

	/**
	 * Called when an Attribute Description in the database is changed.
	 */
	public void onAttributeDescriptionChanged(List<AttributeDescription> newAttributeDescriptions);
}
