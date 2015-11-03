package sk.banik.fri.dataStructures;

import java.util.List;

import sk.banik.fri.dataStructures.model.CatastralArea;
import sk.banik.fri.dataStructures.model.Owner;
import sk.banik.fri.dataStructures.model.Property;
import sk.banik.fri.dataStructures.model.PropertySheet;

public class CatasterServiceNumbered {
	public CatasterService service;

	public CatasterServiceNumbered(CatasterService catasterService) {
		this.service = catasterService;
	}

	public Property operation_1(Integer propertyRegNumber,
			Integer catasterNumber) {
		return service.findProperty(propertyRegNumber,
				catasterNumber);
	}

	public Owner operation_2(String bornNumber) {
		return service.findOwner(bornNumber);
	}

	public List<Owner> operation_3(Integer catasterNumber,
			Integer propertySheetNumber, Integer propertyRegNumber) {
		return service.findOwnersByResidance(catasterNumber,
				propertySheetNumber, propertyRegNumber);
	}

	public PropertySheet operation_4(Integer sheetNumber, Integer catasterNumber) {
		return service
				.findPropertySheet(sheetNumber, catasterNumber);
	}

	public Property operation_5(Integer propertyRegNumber, String catasterName) {
		return service.findProperty(propertyRegNumber, catasterName);
	}

	public PropertySheet operation_6(Integer sheetNumber, String catasterName) {
		return service.findPropertySheet(sheetNumber, catasterName);
	}

	public List<Property> operation_7(String catasterName) {
		return service.findPropertiesSorted(catasterName);
	}

	public List<Property> operation_8(String bornNumber, Integer catasterNumber) {
		return service.findOwnerProperties(bornNumber,
				catasterNumber);
	}

	public List<Property> operation_9(String bornNumber) {
		return service.findOwnerProperties(bornNumber);
	}

	public Owner operation_10(String bornNumber, Integer newResidanceProperty,
			String catasterName) {
		return service.changeResidance(bornNumber,
				newResidanceProperty, catasterName);
	}

	public Property operation_11(Integer propertyRegNumber,
			Integer catasterNumber, String oldBornNumber, String newBornNumber) {
		return service.changeOwner(propertyRegNumber,
				catasterNumber, oldBornNumber, newBornNumber);
	}

	public PropertySheet operation_12(Integer propertySheetNumber,
			Integer catasterNumber, String bornNumber, Double sharehold) {
		return service.addShareholder(propertySheetNumber,
				catasterNumber, bornNumber, sharehold);
	}

	public PropertySheet operation_13(Integer propertySheetNumber,
			Integer catasterNumber, String bornNumber) {
		return service.removeShareholder(propertySheetNumber,
				catasterNumber, bornNumber);
	}

	public List<CatastralArea> operation_14() {
		return service.findCatastralAreasSorted();
	}

	public Owner operation_15(String firstName, String lastName,
			String bornNumber) {
		return service.addPerson(firstName, lastName, bornNumber);
	}

	public PropertySheet operation_16(Integer catasterNumber) {
		return service.addPropertySheet(catasterNumber);
	}

	public PropertySheet operation_17(Integer propertySheetNumber,
			Integer catasterNumber, Integer propertyRegNumber) {
		return service.addProperty(propertySheetNumber,
				catasterNumber, propertyRegNumber);
	}

	public Owner operation_18(String bornNumber) {
		return service.removePerson(bornNumber);
	}

	public PropertySheet operation_19(Integer propertySheetNumber,
			Integer catasterNumber, Integer newPropertySheetNumber,
			Integer newCatasterNumber) {
		return service.removePropertySheet(propertySheetNumber,
				catasterNumber, newPropertySheetNumber, newCatasterNumber);
	}

	public Property operation_20(Integer propertyRegNumber,
			Integer propertySheetNumber, Integer catasterNumber) {
		return service.removeProperty(propertyRegNumber,
				propertySheetNumber, catasterNumber);
	}

	public CatastralArea operation_21(String catasterName) {
		return service.addCatastralArea(catasterName);
	}

	public CatastralArea operation_22(Integer catasterNumber,
			Integer newCatasterNumber) {
		return service.deleteCatastralArea(catasterNumber,
				newCatasterNumber);
	}

}
