package edu.channel4.mm.db.android.callback;

import edu.channel4.mm.db.android.model.graph.Graph;

public interface GraphLoadCallback {

	/**
	 * Called when the Graph is done loading.
	 */
	public void onGraphLoaded(Graph graph);
}
