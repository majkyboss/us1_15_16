package sk.banik.fri.dataStructures.model;

import sk.banik.fri.dataStructures.BasicMapCollection;

public class CatastralArea {
	private static int idCounter = 0;
	
	private int id;
	private String name;
	private BasicMapCollection<Integer, Property> properties;
	private BasicMapCollection<Integer, PropertySheet> propertySheets;
	
	public CatastralArea() {
		id = idCounter++;
		properties = new TreeFactory<Integer, Property>().getTreeInstance();
		propertySheets = new TreeFactory<Integer, PropertySheet>().getTreeInstance();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BasicMapCollection<Integer, Property> getProperties() {
		return properties;
	}
	public void setProperties(BasicMapCollection<Integer, Property> properties) {
		this.properties = properties;
	}
	public BasicMapCollection<Integer, PropertySheet> getPropertySheets() {
		return propertySheets;
	}
	public void setPropertySheets(
			BasicMapCollection<Integer, PropertySheet> propertySheets) {
		this.propertySheets = propertySheets;
	}

	public void addAllProperties(BasicMapCollection<Integer, Property> properties) {
		for (Property prop : properties.getValues()) {
			this.properties.insert(prop.getRegisterNumber(), prop);
			prop.setCatastralArea(this);
		}
	}

	public void addAllSheets(BasicMapCollection<Integer, PropertySheet> propertySheets) {
		for (PropertySheet sheet : propertySheets.getValues()) {
			this.propertySheets.insert(sheet.getNumber(), sheet);
		}
	}
}
