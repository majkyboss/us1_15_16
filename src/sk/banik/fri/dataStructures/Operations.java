package sk.banik.fri.dataStructures;

import java.util.LinkedList;
import java.util.List;

public enum Operations {
	OP1("operation_1", Labels.propertyRegNumber, Labels.catasterNumber),
	OP2("operation_2", Labels.bornNumber),
	OP3("operation_3", Labels.catasterNumber, Labels.propertySheetNumber, Labels.propertyRegNumber),
	OP4("operation_4", Labels.propertySheetNumber, Labels.catasterNumber),
	OP5("operation_5", Labels.propertyRegNumber, Labels.catasterName),
	OP6("operation_6", Labels.propertySheetNumber, Labels.catasterName),
	OP7("operation_7", Labels.catasterName),
	OP8("operation_8", Labels.bornNumber, Labels.catasterNumber),
	OP9("operation_9", Labels.bornNumber),
	OP10("operation_10", Labels.bornNumber, Labels.newResidanceProperty, Labels.catasterName),
	OP11("operation_11", Labels.propertyRegNumber, Labels.catasterNumber, Labels.oldBornNumber, Labels.newBornNumber),
	OP12("operation_12", Labels.propertySheetNumber, Labels.catasterNumber, Labels.bornNumber, Labels.shareholding),
	OP13("operation_13", Labels.propertySheetNumber, Labels.catasterNumber, Labels.bornNumber),
	OP14("operation_14"),
	OP15("operation_15", Labels.firstName, Labels.lastName, Labels.bornNumber),
	OP16("operation_16", Labels.catasterNumber),
	OP17("operation_17", Labels.propertySheetNumber, Labels.catasterNumber, Labels.address),
	OP18("operation_18", Labels.bornNumber),
	OP19("operation_19", Labels.propertySheetNumber, Labels.catasterNumber, Labels.newPropertySheetNumber, Labels.newCatasterNumber),
	OP20("operation_20", Labels.propertyRegNumber, Labels.propertySheetNumber, Labels.catasterNumber),
	OP21("operation_21", Labels.catasterName),
	OP22("operation_22", Labels.catasterNumber, Labels.newCatasterNumber);

	public List<String> inputNames = new LinkedList<String>();
	public String operationName = "";
	
	Operations(String operationName, String... inputNames) {
		this.operationName = operationName;
		for (int i = 0; i < inputNames.length; i++) {
			this.inputNames.add(inputNames[i]);
		}
	}
}
