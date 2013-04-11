package edu.channel4.mm.db.android.model.description;


public class AttributeDescription {

	private String name;

	public AttributeDescription(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;    
	}

}
