package edu.channel4.mm.db.android.model.graph;

public enum GraphType {
	PIE("Pie"),
	BAR("Bar"),
	HISTOGRAM("Histogram"),
	LINE("Line");
	
	private String description;
	
	private GraphType(String description) {
	   this.description = description;
	}
	
	@Override
	public String toString() {
	   return description;
	}
}
