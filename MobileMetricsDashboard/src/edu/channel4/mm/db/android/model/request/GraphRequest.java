package edu.channel4.mm.db.android.model.request;

import java.util.Map;

import edu.channel4.mm.db.android.activity.EventPickerActivity;
import edu.channel4.mm.db.android.activity.GraphViewerActivity;

import android.content.Context;
import android.content.Intent;

public interface GraphRequest {

	@Override
	public String toString();

	/**
	 * Read-only getter for the fields in this {@link GraphRequest}
	 */
	public Map<String, String> getFields();

	/**
	 * Write-only setter used to add a new field to this {@link GraphRequest}
	 * 
	 * @param key
	 * @param value
	 * @return the value of any previous mapping with the specified key or null
	 *         if there was no mapping.
	 */
	public String addField(String key, String value);

	/**
	 * Constructs the correct Intent for a given GraphRequest. The Intent will
	 * open the correct next Activity (e.g. Number of Sessions over Time goes
	 * straight to {@link GraphViewerActivity} whereas Events over Time requires
	 * you to choose the Event first in {@link EventPickerActivity}.
	 * 
	 * The Intent will add fields to the GraphRequest as it is passed along the
	 * Activities.
	 * 
	 * @param context
	 * @param graphRequest
	 * @return
	 */
	public Intent constructGraphRequestIntent(Context context);
}
