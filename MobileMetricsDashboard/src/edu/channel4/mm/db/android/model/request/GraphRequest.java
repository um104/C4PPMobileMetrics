package edu.channel4.mm.db.android.model.request;

import java.util.Map;

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
}
