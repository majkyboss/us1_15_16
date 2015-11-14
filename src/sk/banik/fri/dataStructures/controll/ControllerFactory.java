package sk.banik.fri.dataStructures.controll;

import sk.banik.fri.dataStructures.BasicMapCollection;
import sk.banik.fri.dataStructures.CatasterService;
import sk.banik.fri.dataStructures.model.*;

import java.util.LinkedList;
import java.util.List;

public class ControllerFactory {

	public static CatasterService createController() {
		// TODO implement controller factory method
		return new CatasterController();
	}

	public static class CatasterController implements CatasterService {
		private CatastralOffice cataster = new CatastralOffice();

		@Override
		public Property findProperty(Integer propertyRegNumber, Integer catasterNumber) {
			CatastralArea area = cataster.getAreasById().find(catasterNumber);
			Property property = area.getProperties().find(propertyRegNumber);
			return property;
		}

		@Override
		public Owner findOwner(String bornNumber) {
			Owner owner = cataster.getOwners().find(bornNumber);
			return owner;
		}

		@Override
		public List<Owner> findOwnersByResidance(Integer catasterNumber, Integer propertySheetNumber, Integer propertyRegNumber) {
			Property property = findProperty(propertyRegNumber, catasterNumber);
			return new LinkedList<>(property.getResidents());
		}

		@Override
		public PropertySheet findPropertySheet(Integer sheetNumber, Integer catasterNumber) {
			CatastralArea area = cataster.getAreasById().find(catasterNumber);
			PropertySheet sheet = area.getPropertySheets().find(sheetNumber);
			return sheet;
		}

		@Override
		public Property findProperty(Integer propertyRegNumber, String catasterName) {
			CatastralArea area = cataster.getAreasByName().find(catasterName);
			Property property = area.getProperties().find(propertyRegNumber);
			return property;
		}

		@Override
		public PropertySheet findPropertySheet(Integer sheetNumber, String catasterName) {
			CatastralArea area = cataster.getAreasByName().find(catasterName);
			PropertySheet sheet = area.getPropertySheets().find(sheetNumber);
			return sheet;
		}

		@Override
		public List<Property> findPropertiesSorted(String catasterName) {
			CatastralArea area = cataster.getAreasByName().find(catasterName);
			List<Property> properties = area.getProperties().getSortedList();
			return properties;
		}

		@Override
		public List<Property> findOwnerProperties(String bornNumber, Integer catasterNumber) {
			Owner owner = cataster.getOwners().find(bornNumber);
			LinkedList<Property> list = new LinkedList<Property>();
			for (Property property : owner.getOwnerOf()) {
				if (property.getCatastralArea().getId() == catasterNumber)
					list.add(property);
			}
			return list;
		}

		@Override
		public List<Property> findOwnerProperties(String bornNumber) {
			Owner owner = cataster.getOwners().find(bornNumber);
			return new LinkedList<>(owner.getOwnerOf());
		}

		@Override
		public Owner changeResidance(String bornNumber, Integer newResidanceProperty, String catasterName) {
			Owner owner = cataster.getOwners().find(bornNumber);
			Property oldResidance = owner.getResidance();
			oldResidance.getResidents().remove(owner);
			Property newResidence = cataster.getAreasByName().find(catasterName).getProperties().find(newResidanceProperty);
			owner.setResidance(newResidence);
			newResidence.getResidents().add(owner);
			return owner;
		}

		@Override
		public Property changeOwner(Integer propertyRegNumber, Integer catasterNumber, String oldBornNumber, String newBornNumber) {
			Owner newOwner = cataster.getOwners().find(newBornNumber);
			Owner oldOwner = cataster.getOwners().find(oldBornNumber);

			CatastralArea area = cataster.getAreasById().find(catasterNumber);
			Property property = area.getProperties().find(propertyRegNumber);
			PropertySheet sheet = property.getPropertySheet();
			// REFACTOR for different shareholdings
			// this change all properties in sheet
			for (PropertyEntry pEntry : sheet.getEntries()){
				for (ShareholdEntry shEntry : pEntry.shareholdings){
					if (shEntry.owner.equals(oldOwner))
						shEntry.owner = newOwner;
				}
			}
			return property;
		}

		@Override
		public PropertySheet addShareholder(Integer propertySheetNumber, Integer catasterNumber, String bornNumber, Double shareholding) {
			Owner newOwner = cataster.getOwners().find(bornNumber);
			PropertySheet sheet = cataster.getAreasById().find(catasterNumber).getPropertySheets().find(propertySheetNumber);
			sheet.addOwner(newOwner, shareholding);
			return sheet;
		}

		@Override
		public PropertySheet removeShareholder(Integer propertySheetNumber, Integer catasterNumber, String bornNumber) {
			Owner owner = cataster.getOwners().find(bornNumber);
			PropertySheet sheet = cataster.getAreasById().find(catasterNumber).getPropertySheets().find(propertySheetNumber);
			sheet.removeOwner(owner);
			return sheet;
		}

		@Override
		public List<CatastralArea> findCatastralAreasSorted() {
			return cataster.getAreasByName().getSortedList();
		}

		@Override
		public Owner addPerson(String firstName, String lastName, String bornNumber) {
			Owner owner = new Owner();
			owner.setFirstName(firstName);
			owner.setLastname(lastName);
			owner.setBornNumber(bornNumber);
			cataster.getOwners().insert(owner.getBornNumber(), owner);
			return owner;
		}

		@Override
		public PropertySheet addPropertySheet(Integer catasterNumber) {
			PropertySheet sheet = new PropertySheet();
			CatastralArea area = cataster.getAreasById().find(catasterNumber);
			area.getPropertySheets().insert(sheet.getNumber(), sheet);
			return sheet;
		}

		@Override
		public PropertySheet addProperty(Integer propertySheetNumber, Integer catasterNumber, Integer propertyRegNumber) {
			CatastralArea area = cataster.getAreasById().find(catasterNumber);
			PropertySheet sheet = area.getPropertySheets().find(propertySheetNumber);
			Property property = area.getProperties().find(propertyRegNumber);
			sheet.addProperty(property);
			return sheet;
		}

		@Override
		public Owner removePerson(String bornNumber) {
			Owner owner = cataster.getOwners().delete(bornNumber);
			Property residance = owner.getResidance();
			if (residance != null)
				residance.getResidents().remove(owner);

			for (Property property : owner.getOwnerOf()){
				PropertySheet sheet = property.getPropertySheet();
				// TODO delete person from sheet
				for (PropertyEntry pEntry : sheet.getEntries()){
					for (int i = 0; i < pEntry.shareholdings.size(); i++) {
						if (pEntry.shareholdings.get(i).owner.equals(owner)){
							pEntry.shareholdings.remove(i);
//							i--;
						}
					}
				}
			}
			return owner;
		}

		@Override
		public PropertySheet removePropertySheet(Integer propertySheetNumber, Integer catasterNumber, Integer newPropertySheetNumber, Integer newCatasterNumber) {
			BasicMapCollection<Integer, PropertySheet> sheets = cataster.getAreasById().find(catasterNumber).getPropertySheets();
			PropertySheet sheet = sheets.find(propertySheetNumber);
			PropertySheet newSheet = cataster.getAreasById().find(newCatasterNumber).getPropertySheets().find(newPropertySheetNumber);
			for (PropertyEntry pEntry : sheet.getEntries()) {
				newSheet.getEntries().add(pEntry);
				// add users from removed sheet to new sheet
				for (ShareholdEntry shEntry : sheet.getShareholdersList()){
					newSheet.addOwner(shEntry.owner, 0.0);
					// do not forget to modify shareholding values
				}

				// add properties from removed sheet to users to new sheet
				// - add users from newSheet to properties from deleted sheet
				for (ShareholdEntry shEntry: newSheet.getShareholdersList()) {
					// in new sheet shareholding will not be assigned - need to add
					shEntry.shareholding = 0.0;
					pEntry.shareholdings.add(shEntry);
				}
			}
			return sheets.delete(sheet.getNumber());
		}

		@Override
		public Property removeProperty(Integer propertyRegNumber, Integer propertySheetNumber, Integer catasterNumber) {
			PropertySheet sheet = cataster.getAreasById().find(catasterNumber).getPropertySheets().find(propertySheetNumber);
			for (Property p : sheet.getPropertiesList()) {
				if (p.getRegisterNumber() == propertyRegNumber) {
					sheet.removeProperty(p);
					return p;
				}
			}

			return null;
		}

		@Override
		public CatastralArea addCatastralArea(String catasterName) {
			CatastralArea area = new CatastralArea();
			area.setName(catasterName);
			cataster.getAreasById().insert(area.getId(), area);
			cataster.getAreasByName().insert(area.getName(), area);
			return area;
		}

		@Override
		public CatastralArea deleteCatastralArea(Integer catasterNumber, Integer newCatasterNumber) {
			CatastralArea area = cataster.getAreasById().find(catasterNumber);
			CatastralArea newArea = cataster.getAreasById().find(newCatasterNumber);
			newArea.addAllProperties(area.getProperties());
			newArea.addAllSheets(area.getPropertySheets());
			cataster.getAreasById().delete(area.getId());
			cataster.getAreasByName().delete(area.getName());
			return area;
		}

		@Override
		public void importDB(List<KeyValuePair> items) {

		}

		@Override
		public void deleteDB() {

		}

		@Override
		public List<KeyValuePair> exportDB() {
			return null;
		}
	}
}
