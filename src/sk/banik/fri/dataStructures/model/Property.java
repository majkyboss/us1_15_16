package sk.banik.fri.dataStructures.model;

import java.util.HashSet;

public class Property {
	private static int regNumCounter = 0;
	
	private int registerNumber;
	private String address;
	private PropertySheet propertySheet;
	private HashSet<Owner> residents;
	private CatastralArea catastralArea;
	
	public Property() {
		registerNumber = regNumCounter++;
		residents = new HashSet<>();
	}
	
	public int getRegisterNumber() {
		return registerNumber;
	}
	public void setRegisterNumber(int registerNumber) {
		this.registerNumber = registerNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public PropertySheet getPropertySheet() {
		return propertySheet;
	}
	public void setPropertySheet(PropertySheet propertySheet) {
		this.propertySheet = propertySheet;
	}
	public HashSet<Owner> getResidents() {
		return residents;
	}
	public void setResidents(HashSet<Owner> residents) {
		this.residents = residents;
	}
	public CatastralArea getCatastralArea() {
		return catastralArea;
	}
	public void setCatastralArea(CatastralArea catastralArea) {
		this.catastralArea = catastralArea;
	}
	
	
}
