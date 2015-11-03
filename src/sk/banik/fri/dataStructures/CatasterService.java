package sk.banik.fri.dataStructures;

import java.awt.Component;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import sk.banik.fri.dataStructures.CatasterService.KeyValuePair;
import sk.banik.fri.dataStructures.model.CatastralArea;
import sk.banik.fri.dataStructures.model.Owner;
import sk.banik.fri.dataStructures.model.Property;
import sk.banik.fri.dataStructures.model.PropertySheet;

public interface CatasterService {
	Property findProperty(Integer propertyRegNumber, Integer catasterNumber);

	Owner findOwner(String bornNumber);

	List<Owner> findOwnersByResidance(Integer catasterNumber, Integer propertySheetNumber, Integer propertyRegNumber);

	PropertySheet findPropertySheet(Integer sheetNumber, Integer catasterNumber);

	Property findProperty(Integer propertyRegNumber, String catasterName);

	PropertySheet findPropertySheet(Integer sheetNumber, String catasterName);

	List<Property> findPropertiesSorted(String catasterName);

	List<Property> findOwnerProperties(String bornNumber, Integer catasterNumber);

	List<Property> findOwnerProperties(String bornNumber);

	Owner changeResidance(String bornNumber, Integer newResidanceProperty, String catasterName);

	Property changeOwner(Integer propertyRegNumber, Integer catasterNumber, String oldBornNumber, String newBornNumber);

	// add or edit
	PropertySheet addShareholder(Integer propertySheetNumber, Integer catasterNumber, String bornNumber, Double shareholding);

	PropertySheet removeShareholder(Integer propertySheetNumber, Integer catasterNumber, String bornNumber);

	List<CatastralArea> findCatastralAreasSorted();

	Owner addPerson(String firstName, String lastName, String bornNumber);

	PropertySheet addPropertySheet(Integer catasterNumber);

	PropertySheet addProperty(Integer propertySheetNumber, Integer catasterNumber, Integer propertyRegNumber);

	// remove also in sharehodlings, residents
	Owner removePerson(String bornNumber);

	PropertySheet removePropertySheet(Integer propertySheetNumber, Integer catasterNumber, Integer newPropertySheetNumber, Integer newCatasterNumber);

	Property removeProperty(Integer propertyRegNumber, Integer propertySheetNumber, Integer catasterNumber);

	CatastralArea addCatastralArea(String catasterName);

	CatastralArea deleteCatastralArea(Integer catasterNumber, Integer newCatasterNumber);

	void importDB(List<KeyValuePair> items);

	void deleteDB();
	
	List<KeyValuePair> exportDB();
	
	public class KeyValuePair {
		public Object key;
		public Object value;
	}

	void setView(Component mainWin);
}
