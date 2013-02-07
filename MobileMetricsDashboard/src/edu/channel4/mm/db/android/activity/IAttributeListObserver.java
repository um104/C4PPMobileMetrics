package edu.channel4.mm.db.android.activity;

import java.util.List;
import edu.channel4.mm.db.android.model.AttribDescription;

public interface IAttributeListObserver {

	/**
	 * Updates the internal state of the object with a new List<String>
	 * 
	 * @param attribList
	 */
	public void updateAttributeList(List<AttribDescription> attribList);
}
