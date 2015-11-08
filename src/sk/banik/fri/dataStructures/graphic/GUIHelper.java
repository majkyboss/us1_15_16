package sk.banik.fri.dataStructures.graphic;

import sk.banik.fri.dataStructures.Labels;
import sk.banik.fri.dataStructures.Operations;
import sk.banik.fri.dataStructures.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class GUIHelper {

	private MainWin mainWin;

	public GUIHelper(MainWin mainWin) {
		this.mainWin = mainWin;
	}

	public InputsPanel generateInputsJPanel(Operations operation) {
		InputsPanel generatedJPanel = new InputsPanel();

		// grid layout
		generatedJPanel.setLayout(new GridLayout(operation.inputNames.size() + 1, 2, 5, 5));
		for (int i = 0; i < operation.inputNames.size(); i++) {
			String name = operation.inputNames.get(i);
			generatedJPanel.add(new JLabel(name));
			JTextField textField = new JTextField();
			generatedJPanel.add(textField);
			generatedJPanel.inputBoxes.add(textField);
		}
		for (int i = 0; i < ((GridLayout)generatedJPanel.getLayout()).getColumns() - 1; i++) {
			generatedJPanel.add(new JLabel());
		}

		JButton submitBtn = new JButton(Labels.submitButton);
		generatedJPanel.add(submitBtn);
		generatedJPanel.sendButton = submitBtn;

		return generatedJPanel;
	}

	public InputsPanel generatePropertyView(Property property) {
		InputsPanel generatedJPanel = new InputsPanel();

		// box layout
		generatedJPanel.setLayout(new BoxLayout(generatedJPanel, BoxLayout.Y_AXIS));

		// property info
		JPanel p = new JPanel();
		generatedJPanel.add(p);
		p.setLayout(new GridLayout(3, 2, 5, 5));
		Component regNumberLabel = new JLabel(Labels.propertyRegNumber);
		p.add(regNumberLabel);
		JTextField regNumberInput = new JTextField(property.getRegisterNumber() + "");
		p.add(regNumberInput);
		regNumberInput.setEditable(false);
		Component addressLabel = new JLabel(Labels.address);
		p.add(addressLabel);
		JTextField addressInput = new JTextField(property.getAddress() + "");
		p.add(addressInput);
		addressInput.setEditable(false);
		JButton edit = new JButton(Labels.edit);
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (edit.getText().equals(Labels.edit)) {
					// go to edit mode
//					regNumberInput.setEditable(true);
					addressInput.setEditable(true);
					edit.setText(Labels.save);
				} else {
					// save and go to show mode
					// TODO check types
					property.setRegisterNumber(Integer.parseInt(regNumberInput.getText()));
//					regNumberInput.setEditable(false);
//					regNumberInput.setText(property.getRegisterNumber() + "");
					property.setAddress(addressInput.getText());
					addressInput.setEditable(false);
					addressInput.setText(property.getAddress() + "");
					edit.setText(Labels.edit);
				}
			}
		});
		p.add(edit);

		// additional info
		JButton btn = new JButton(Labels.residentsLabel);
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LinkedList<Owner> people = new LinkedList<>();
				PropertySheet sheet = property.getPropertySheet();
				if (sheet != null) {
					for (PropertyEntry entry : sheet.getEntries()){
						for (ShareholdEntry sh : entry.shareholdings){
							people.add(sh.owner);
						}
					}
					mainWin.showPanel(generatePeopleView(people));
				}
			}
		});
		generatedJPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		generatedJPanel.add(btn);
		btn = new JButton(Labels.propertySheetLabel);
		btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertySheet sheet = property.getPropertySheet();
				if (sheet != null) {
					mainWin.showPanel(generatePropertySheetView(sheet));
				}
			}
		});
		generatedJPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		generatedJPanel.add(btn);

		generatedJPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel(Labels.ownersLabel), BorderLayout.CENTER);
		p.setAlignmentX(Component.CENTER_ALIGNMENT);
		generatedJPanel.add(p);

		LinkedList<ShareholdEntry> shareholdersList = new LinkedList<>();
		// TODO delete - for testing
//		ShareholdEntry shE = new ShareholdEntry();
//		shE.owner = new Owner();
//		shE.owner.setFirstName("Arthur");
//		shE.owner.setLastname("Normal");
//		shE.shareholding = 20.5;
//		ownersList.add(shE);
//		shE = new ShareholdEntry();
//		shE.owner = new Owner();
//		shE.owner.setFirstName("Jane");
//		shE.owner.setLastname("Static");
//		shE.shareholding = 20.5;
//		ownersList.add(shE);

		PropertySheet sheet = property.getPropertySheet();
		if (sheet != null){
			for (PropertyEntry propertyEntry : sheet.getEntries()){
				if (propertyEntry.property.equals(property)) {
					shareholdersList = propertyEntry.shareholdings;
					break;
				}
			}
		}

		// add owners
		generatedJPanel.add(new JScrollPane(generateOwnersTable(shareholdersList)));

		return generatedJPanel;
	}

	public JPanel generatePropertiesView(List<Property> properties) {
		InputsPanel generatedJPanel = new InputsPanel();
        generatedJPanel.setLayout(new BoxLayout(generatedJPanel, BoxLayout.Y_AXIS));

		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel(Labels.propertiesLabel), BorderLayout.CENTER);
		generatedJPanel.add(p);

        generatedJPanel.add(new JScrollPane(generatePropertiesTable(properties)));

        return generatedJPanel;
	}

	public JPanel generateOwnerView(Owner o) {
		InputsPanel generatedJPanel = new InputsPanel();
		generatedJPanel.setLayout(new BoxLayout(generatedJPanel, BoxLayout.Y_AXIS));

		// add owner's basic info
		JPanel p = new JPanel();
		generatedJPanel.add(p);
		p.setLayout(new SpringLayout());

		JLabel label = new JLabel(Labels.firstName + ":");
		p.add(label);
		JTextField firstNameValue = new JTextField(o.getFirstName());
		firstNameValue.setEditable(false);
		p.add(firstNameValue);
		label = new JLabel(Labels.lastName + ":");
		p.add(label);
		JTextField lastNameValue = new JTextField(o.getLastname());
		lastNameValue.setEditable(false);
		p.add(lastNameValue);
		label = new JLabel(Labels.bornNumber);
		p.add(label);
		JTextField bornNumberValue = new JTextField(o.getBornNumber());
		bornNumberValue.setEditable(false);
		p.add(bornNumberValue);
		JButton edit = new JButton(Labels.edit);
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean editable = false;
				if (edit.getText().equals(Labels.edit)) {
					// go to edit mode
					editable = true;

					edit.setText(Labels.save);
				} else {
					// save and go to show mode
					o.setFirstName(firstNameValue.getText());
					firstNameValue.setText(o.getFirstName());
					o.setLastname(lastNameValue.getText());
					lastNameValue.setText(o.getLastname());
//					o.setBornNumber(bornNumberValue.getText());

					editable = false;
					edit.setText(Labels.edit);
				}

				firstNameValue.setEditable(editable);
				lastNameValue.setEditable(editable);
//				bornNumberValue.setEditable(editable);
			}
		});
		p.add(edit);
		p.add(new JLabel());
		p.add(new JLabel());

		SpringUtilities.makeCompactGrid(
				p,
				4, 2, //rows, cols
				6, 6, //initX, initY
				6, 6);

		// add residence
		p = new JPanel(new BorderLayout());
		p.add(new JLabel(Labels.residenceLabel), BorderLayout.CENTER);
		generatedJPanel.add(p);

		LinkedList<Property> residanceList = new LinkedList<>();
		if (o.getResidance() != null){
			residanceList.add(o.getResidance());
		}
		generatedJPanel.add(new JScrollPane(generatePropertiesTable(residanceList)));

		// add owners of
		p = new JPanel(new BorderLayout());
		p.add(new JLabel(Labels.ownersOfLabel), BorderLayout.CENTER);
		generatedJPanel.add(p);
		generatedJPanel.add(new JScrollPane(generatePropertiesTable(new LinkedList<>(o.getOwnerOf()))));

		return generatedJPanel;
	}

	public JPanel generatePeopleView(LinkedList<Owner> people) {
		InputsPanel generatedJPanel = new InputsPanel();
		generatedJPanel.setLayout(new BoxLayout(generatedJPanel, BoxLayout.Y_AXIS));

		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel(Labels.ownersLabel), BorderLayout.CENTER);
		generatedJPanel.add(p);

		generatedJPanel.add(new JScrollPane(generateOwnersTable(people)));

		return generatedJPanel;
	}

	public JPanel generatePropertySheetView(PropertySheet sheet) {
        InputsPanel generatedJPanel = new InputsPanel();
        generatedJPanel.setLayout(new BoxLayout(generatedJPanel, BoxLayout.Y_AXIS));

		// add sheet number
        JPanel p = new JPanel();
        generatedJPanel.add(p);

        p.setLayout(new SpringLayout());
        JLabel label = new JLabel(Labels.propertySheetNumber + ":");
        p.add(label);
        JLabel labelValue = new JLabel(sheet.getNumber() + "");
        p.add(labelValue);
		SpringUtilities.makeCompactGrid(
				p,
				1, 2, //rows, cols
				6, 6, //initX, initY
				6, 6);

		// ------ example of SpringLayout -------
//		p = new JPanel();
//		p.setLayout(new SpringLayout());
//		generatedJPanel.add(p);
//
//		for (PropertyEntry pEntry :
//				sheet.getEntries()) {
//			pEntry.property.getRegisterNumber();
//			pEntry.property.getAddress();
//
//			label = new JLabel(pEntry.property.getRegisterNumber() + ": ");
//			label.setBorder(new LineBorder(Color.black, 2));
//			label.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					if (e.getClickCount() == 2)
//						JOptionPane.showConfirmDialog(generatedJPanel, "label clicked");
//				}
//			});
//			p.add(label);
//			labelValue = new JLabel(pEntry.property.getAddress());
//			p.add(labelValue);
//			JLabel labelDetail = new JLabel("details");
//			p.add(labelDetail);
//		}
//
//        //Lay out the panel.
//        SpringUtilities.makeCompactGrid(
//                p,
//                sheet.getEntries().size(), 3, //rows, cols
//                6, 6, //initX, initY
//                6, 6);

		// add properties table
		p = new JPanel(new BorderLayout());
		p.add(new JLabel(Labels.propertiesLabel), BorderLayout.CENTER);
		generatedJPanel.add(p);

		generatedJPanel.add(new JScrollPane(generatePropertiesTable(sheet.getPropertiesList())));

		// add owners table
		p = new JPanel(new BorderLayout());
		p.add(new JLabel(Labels.ownersLabel), BorderLayout.CENTER);
		generatedJPanel.add(p);

		generatedJPanel.add(new JScrollPane(generateOwnersTable(sheet.getShareholdersList())));

        return generatedJPanel;
	}

	private JTable generateOwnersTable(List shareholdersList) {
		if (shareholdersList.isEmpty())
			return null;

		Object item = shareholdersList.get(0);
		if (item instanceof ShareholdEntry) {
			OwnersTableModel model = new OwnersTableModel((LinkedList<ShareholdEntry>) shareholdersList);
			JTable ownersTable = new JTable(model);
			ownersTable.setPreferredScrollableViewportSize(new Dimension(400, 150));
			ownersTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						// open selected owner
						Owner o = ((OwnersTableModel) ownersTable.getModel()).getOwner(ownersTable.getSelectedRow());
						mainWin.showPanel(generateOwnerView(o));
					}
				}
			});
			return ownersTable;
		} else if (item instanceof Owner){
			OwnersTableModel model = new OwnersTableModel((List<Owner>)shareholdersList);
			JTable peopleTable = new JTable(model);
			peopleTable.setPreferredScrollableViewportSize(new Dimension(400, 150));
			peopleTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2){
						// open selected owner
						Owner o = ((OwnersTableModel) peopleTable.getModel()).getOwner(peopleTable.getSelectedRow());
						mainWin.showPanel(generateOwnerView(o));
					}
				}
			});
			return peopleTable;
		}

		return null;
	}

	private JTable generatePropertiesTable(List<Property> properties){
		PropertiesTableModel model = new PropertiesTableModel(properties);
		JTable propertiesTable = new JTable(model);
		propertiesTable.setPreferredScrollableViewportSize(new Dimension(400, 150));
		propertiesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
//					System.out.println(ownerstable.getSelectedRow() + " : " + ownerstable.getSelectedColumn());
					// open selected owner
					Property p = ((PropertiesTableModel)propertiesTable.getModel()).getProperty(propertiesTable.getSelectedRow());
					mainWin.showPanel(generatePropertyView(p));
				}
			}
		});
		return propertiesTable;
	}

	public JPanel generatePropertySheetsView(List<PropertySheet> sheets) {
		InputsPanel generatedJPanel = new InputsPanel();
		generatedJPanel.setLayout(new BoxLayout(generatedJPanel, BoxLayout.Y_AXIS));

		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel(Labels.propertySheetsLabel), BorderLayout.CENTER);
		generatedJPanel.add(p);

		generatedJPanel.add(new JScrollPane(generateSheetsTable(sheets)));

		return generatedJPanel;
	}

	private JTable generateSheetsTable(List<PropertySheet> sheets) {
		SheetsTableModel model = new SheetsTableModel(sheets);
		JTable sheetsTable = new JTable(model);
		sheetsTable.setPreferredScrollableViewportSize(new Dimension(400, 150));
		sheetsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
					PropertySheet selectedSheet = ((SheetsTableModel)sheetsTable.getModel()).getSheet(sheetsTable.getSelectedRow());
					mainWin.showPanel(generatePropertySheetView(selectedSheet));
				}
			}
		});

		return sheetsTable;
	}

	public JPanel generateCatastralAreasView(LinkedList<CatastralArea> areas) {
		InputsPanel generatedJPanel = new InputsPanel();
		generatedJPanel.setLayout(new BoxLayout(generatedJPanel, BoxLayout.Y_AXIS));

		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel(Labels.catastersLabel), BorderLayout.CENTER);
		generatedJPanel.add(p);

		generatedJPanel.add(new JScrollPane(generateCatastersTable(areas)));

		return generatedJPanel;
	}

	private JTable generateCatastersTable(List<CatastralArea> areas) {
		CatasterTableModel model = new CatasterTableModel(areas);
		JTable propertiesTable = new JTable(model);
		propertiesTable.setPreferredScrollableViewportSize(new Dimension(400, 150));

		return propertiesTable;
	}
}
