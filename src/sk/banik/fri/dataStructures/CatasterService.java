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
	/**
	 * Vyhľadanie nehnuteľnosti podľa súpisného čísla a čísla katastrálneho územia.
	 * Po nájdení nehnuteľnosti je potrebné zobraziť všetky evidované údaje vrátane všetkých
	 * údajov z listu vlastníctva na ktorom je nehnuteľnosť zapísaná.
	 * @param propertyRegNumber
	 * @param catasterNumber
     * @return
     */
	Property findProperty(Integer propertyRegNumber, Integer catasterNumber);

	/**
	 * Vyhľadanie obyvateľa podľa rodného čísla a výpis jeho trvalého pobytu (vypíšu sa
	 * všetky informácie o nehnuteľnosti, ktorú obýva)
	 * @param bornNumber
	 * @return
     */
	Owner findOwner(String bornNumber);

	/**
	 * Výpis všetkých osôb, ktoré majú trvalý pobyt v zadanej nehnuteľnosti (zadá sa číslo
	 * katastrálneho územia, číslo listu vlastníctva a súpisné číslo)
	 * @param catasterNumber
	 * @param propertySheetNumber
	 * @param propertyRegNumber
     * @return
     */
	List<Owner> findOwnersByResidance(Integer catasterNumber, Integer propertySheetNumber, Integer propertyRegNumber);

	/**
	 * Vyhľadanie listu vlastníctva podľa jeho čísla a čísla katastrálneho územia. Po nájdení
	 * listu vlastníctva je potrebné zobraziť všetky evidované údaje vrátane všetkých údajov
	 * o majiteľoch nehnuteľností zapísaných na liste vlastníctva (mená, priezviská, majetkové
	 * podiely...).
	 * @param sheetNumber
	 * @param catasterNumber
     * @return
     */
	PropertySheet findPropertySheet(Integer sheetNumber, Integer catasterNumber);

	/**
	 * Vyhľadanie nehnuteľnosti podľa súpisného čísla a názvu katastrálneho územia.
	 * Po nájdení nehnuteľnosti je potrebné zobraziť všetky evidované údaje vrátane všetkých
	 * údajov z listu vlastníctva na ktorom je nehnuteľnosť zapísaná.
	 * @param propertyRegNumber
	 * @param catasterName
     * @return
     */
	Property findProperty(Integer propertyRegNumber, String catasterName);

	/**
	 * Vyhľadanie listu vlastníctva podľa jeho čísla a názvu katastrálneho územia. Po nájdení
	 * listu vlastníctva je potrebné zobraziť všetky evidované údaje vrátane všetkých údajov
	 * o majiteľoch nehnuteľností zapísaných na liste vlastníctva (mená, priezviská, majetkové
	 * podiely...).
	 * @param sheetNumber
	 * @param catasterName
     * @return
     */
	PropertySheet findPropertySheet(Integer sheetNumber, String catasterName);

	/**
	 * Výpis nehnuteľností v zadanom katastrálnom území (definované názvom) utriedených
	 * podľa ich súpisných čísel aj s ich popisom.
	 * @param catasterName
	 * @return
     */
	List<Property> findPropertiesSorted(String catasterName);

	/**
	 * Výpis všetkých nehnuteľností majiteľa (definovaný rodným číslom) v zadanom
	 * katastrálnom území (definované jeho číslom).
	 * @param bornNumber
	 * @param catasterNumber
     * @return
     */
	List<Property> findOwnerProperties(String bornNumber, Integer catasterNumber);

	/**
	 * Výpis všetkých nehnuteľností majiteľa (definovaný rodným číslom).
	 * @param bornNumber
	 * @return
     */
	List<Property> findOwnerProperties(String bornNumber);

	/**
	 * Zmena trvalého pobytu obyvateľa (definovaný rodným číslom) do nehnuteľnosti
	 * (definovaná súpisným číslom) v zadanom katastrálnom území (definované jeho
	 * názvom).
	 * @param bornNumber
	 * @param newResidanceProperty
	 * @param catasterName
     * @return
     */
	Owner changeResidance(String bornNumber, Integer newResidanceProperty, String catasterName);

	/**
	 * Zmena majiteľa (definovaný rodným číslom) nehnuteľnosti (definovaná súpisným
	 * číslom) v zadanom katastrálnom území (definované jeho číslom). Nový majiteľ je
	 * definovaný rodným číslom.
	 * @param propertyRegNumber
	 * @param catasterNumber
	 * @param oldBornNumber
	 * @param newBornNumber
     * @return
     */
	Property changeOwner(Integer propertyRegNumber, Integer catasterNumber, String oldBornNumber, String newBornNumber);

	// add or edit

	/**
	 * Zapísanie majetkového podielu majiteľa (definovaný rodným číslom) na list vlastníctva
	 * (definovaný číslom) v zadanom katastrálnom území (definované jeho číslom)
	 * @param propertySheetNumber
	 * @param catasterNumber
	 * @param bornNumber
	 * @param shareholding
     * @return
     */
	PropertySheet addShareholder(Integer propertySheetNumber, Integer catasterNumber, String bornNumber, Double shareholding);

	/**
	 * Odstránenie majetkového podielu majiteľa (definovaný rodným číslom) z listu
	 vlastníctva (definovaný číslom) v zadanom katastrálnom území (definované jeho
	 číslom).
	 * @param propertySheetNumber
	 * @param catasterNumber
	 * @param bornNumber
     * @return
     */
	PropertySheet removeShareholder(Integer propertySheetNumber, Integer catasterNumber, String bornNumber);

	/**
	 * Výpis všetkých katastrálnych území utriedených podľa ich názvov.
	 * @return
     */
	List<CatastralArea> findCatastralAreasSorted();

	/**
	 * Pridanie občana.
	 * @param firstName
	 * @param lastName
	 * @param bornNumber
     * @return
     */
	Owner addPerson(String firstName, String lastName, String bornNumber);

	/**
	 * Pridanie listu vlastníctva.
	 * @param catasterNumber
	 * @return
     */
	PropertySheet addPropertySheet(Integer catasterNumber);

	/**
	 * Pridanie nehnuteľnosti na list vlastníctva (definovaný číslom) v zadanom katastrálnom
	 území (definované jeho číslom).
	 * @param propertySheetNumber
	 * @param catasterNumber
	 * @param propertyRegNumber
     * @return
     */
	PropertySheet addProperty(Integer propertySheetNumber, Integer catasterNumber, Integer propertyRegNumber);

	/**
	 * Odstránenie občana (definovaný rodným číslom).
	 * @param bornNumber
	 * @return
     */
	// remove also in sharehodlings, residents
	Owner removePerson(String bornNumber);

	/**
	 * Odstránenie listu vlastníctva (definovaný číslom) v zadanom katastrálnom území
	 (definované jeho číslom). Nehnuteľnosti sa presunú na iný list vlastníctva.
	 * @param propertySheetNumber
	 * @param catasterNumber
	 * @param newPropertySheetNumber
	 * @param newCatasterNumber
     * @return
     */
	PropertySheet removePropertySheet(Integer propertySheetNumber, Integer catasterNumber, Integer newPropertySheetNumber, Integer newCatasterNumber);

	/**
	 * Odstránenie nehnuteľnosti (definovaná popisným číslom) z listu vlastníctva (definovaný
	 číslom) v zadanom katastrálnom území (definované jeho číslom).
	 * @param propertyRegNumber
	 * @param propertySheetNumber
	 * @param catasterNumber
     * @return
     */
	Property removeProperty(Integer propertyRegNumber, Integer propertySheetNumber, Integer catasterNumber);

	/**
	 * Pridanie katastrálneho územia.
	 * @param catasterName
	 * @return
     */
	CatastralArea addCatastralArea(String catasterName);

	/**
	 * Odstránenie katastrálneho územia (definované jeho číslom). Agenda sa presunie do
	 iného katastrálneho územia.
	 * @param catasterNumber
	 * @param newCatasterNumber
     * @return
     */
	CatastralArea deleteCatastralArea(Integer catasterNumber, Integer newCatasterNumber);

	/**
	 *
	 * @param items
     */
	void importDB(List<KeyValuePair> items);

	/**
	 *
	 */
	void deleteDB();

	/**
	 *
	 * @return
     */
	List<KeyValuePair> exportDB();

	public class KeyValuePair {
		public Object key;
		public Object value;
	}

//	void setView(Component mainWin);
}
