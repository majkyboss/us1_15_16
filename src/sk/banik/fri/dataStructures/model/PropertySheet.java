package sk.banik.fri.dataStructures.model;

import java.util.LinkedList;

public class PropertySheet {
	private static int numberCounter = 0;
	
	private int number;
	LinkedList<PropertyEntry> entries;

	public PropertySheet() {
		number = numberCounter++;
		entries = new LinkedList<PropertyEntry>();
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public LinkedList<PropertyEntry> getEntries() {
		return entries;
	}

	public void setEntries(LinkedList<PropertyEntry> entries) {
		this.entries = entries;
	}
	
	
}
