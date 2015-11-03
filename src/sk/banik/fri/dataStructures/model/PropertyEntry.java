package sk.banik.fri.dataStructures.model;

import java.util.LinkedList;

public class PropertyEntry{
	public Property property;
	public LinkedList<ShareholdEntry> shareholdings;
	
	public PropertyEntry() {
		shareholdings = new LinkedList<ShareholdEntry>();
	}
}
