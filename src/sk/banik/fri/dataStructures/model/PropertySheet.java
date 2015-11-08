package sk.banik.fri.dataStructures.model;

import java.util.LinkedList;
import java.util.List;

public class PropertySheet {
	private static int numberCounter = 0;
	
	private int number;
	LinkedList<PropertyEntry> entries;

	public PropertySheet() {
		number = numberCounter++;
		entries = new LinkedList<>();
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
	
	public void addProperty(Property property){
		PropertyEntry pEntry = new PropertyEntry();
		pEntry.property = property;
		if (!entries.isEmpty()){
			for (ShareholdEntry shEntry :
				 entries.get(0).shareholdings) {
				// REFACTOR - later when the shareholdings may be different for single owner
				// for single property, do not use ref of shareHoldingEntry in
				// all propertyEntries
				pEntry.shareholdings.add(shEntry);
			}
		}
		entries.add(pEntry);
		property.setPropertySheet(this);
	}

	public void removeProperty(Property property){
		for (int i = 0; i < entries.size(); i++){
			if (entries.get(i).property.equals(property)){
				entries.remove(i);
				break;
			}
		}
	}

	public void addOwner(Owner owner, Double shareholding){
		// REFACTOR - later when the shareholdings may be different for single owner
		// for single property, do not use ref of shareHoldingEntry in
		// all propertyEntries
		ShareholdEntry shEntry = new ShareholdEntry();
		shEntry.owner = owner;
		shEntry.shareholding = shareholding;

		for (PropertyEntry pEntry :
				entries) {
			pEntry.shareholdings.add(shEntry);
			owner.getOwnerOf().add(pEntry.property);
		}
	}

	public void removeOwner(Owner owner){
		for (PropertyEntry pEntry :
				entries) {
			for (int i = 0; i < pEntry.shareholdings.size(); i++){
				ShareholdEntry shEntry = pEntry.shareholdings.get(i);
				if (shEntry.owner.equals(owner)) {
					pEntry.shareholdings.remove(i);
					owner.getOwnerOf().remove(pEntry.property);
					i--;
				}
			}
		}
	}

	public List<Property> getPropertiesList() {
		LinkedList<Property> list = new LinkedList<>();
		for (PropertyEntry pEntry :
				entries) {
			list.add(pEntry.property);
		}
		return list;
	}

	// REFACTOR - later when the shareholdings may be different for single owner
	// for single property, do not use ref of shareHoldingEntry in
	// all propertyEntries
	public LinkedList<ShareholdEntry> getShareholdersList() {

		if (!entries.isEmpty())
			return entries.get(0).shareholdings;

		return null;
	}
}
