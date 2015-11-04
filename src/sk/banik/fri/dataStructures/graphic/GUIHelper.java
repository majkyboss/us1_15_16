package sk.banik.fri.dataStructures.graphic;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import sk.banik.fri.dataStructures.Labels;
import sk.banik.fri.dataStructures.Operations;
import sk.banik.fri.dataStructures.model.CatastralArea;
import sk.banik.fri.dataStructures.model.Owner;
import sk.banik.fri.dataStructures.model.Property;
import sk.banik.fri.dataStructures.model.PropertyEntry;
import sk.banik.fri.dataStructures.model.PropertySheet;
import sk.banik.fri.dataStructures.model.ShareholdEntry;

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
		regNumberInput.setEnabled(false);
		Component addressLabel = new JLabel(Labels.address);;
		p.add(addressLabel);
		JTextField addressInput = new JTextField(property.getAddress() + "");
		p.add(addressInput);
		addressInput.setEnabled(false);
		JButton edit = new JButton(Labels.edit);
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (edit.getText().equals(Labels.edit)) {
					// go to edit mode
					regNumberInput.setEnabled(true);
					addressInput.setEnabled(true);
					edit.setText(Labels.save);
				} else {
					// save and go to show mode
					// TODO check types
					property.setRegisterNumber(Integer.parseInt(regNumberInput.getText()));
					regNumberInput.setEnabled(false);
					regNumberInput.setText(property.getRegisterNumber() + "");
					property.setAddress(addressInput.getText());
					addressInput.setEnabled(false);
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
				LinkedList<Owner> people = new LinkedList<Owner>();
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

		LinkedList<ShareholdEntry> ownersList = new LinkedList<ShareholdEntry>();
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
					ownersList = propertyEntry.shareholdings;
					break;
				}
			}
		}

		OwnersTableModel model = new OwnersTableModel(ownersList);
		JTable ownersTable = new JTable(model);
		ownersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2){
//					System.out.println(ownerstable.getSelectedRow() + " : " + ownerstable.getSelectedColumn());
					// open selected owner
					Owner o = ((OwnersTableModel) ownersTable.getModel()).getOwner(ownersTable.getSelectedRow());
					mainWin.showPanel(generateOwnerView(o));
				}
			}
		});
		generatedJPanel.add(new JScrollPane(ownersTable));

		return generatedJPanel;
	}

	public JPanel generatePropertiesView(List<Property> properties) {
		InputsPanel generatedJPanel = new InputsPanel();
        generatedJPanel.setLayout(new BoxLayout(generatedJPanel, BoxLayout.Y_AXIS));

		JPanel p = new JPanel(new BorderLayout());
		p.add(new JLabel(Labels.propertiesLabel), BorderLayout.CENTER);
		generatedJPanel.add(p);

        PropertiesTableModel model = new PropertiesTableModel(properties);
        JTable propertiesTable = new JTable(model);
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
        generatedJPanel.add(new JScrollPane(propertiesTable));

        return generatedJPanel;
	}

	public JPanel generateOwnerView(Owner o) {
		// TODO Auto-generated method stub
		return null;
	}

	public JPanel generatePeopleView(LinkedList<Owner> people) {
		// TODO Auto-generated method stub
		return null;
	}

	public JPanel generatePropertySheetView(PropertySheet sheet) {
		// TODO Auto-generated method stub
        InputsPanel generatedJPanel = new InputsPanel();
        generatedJPanel.setLayout(new BoxLayout(generatedJPanel, BoxLayout.Y_AXIS));

        JPanel p = new JPanel();
        generatedJPanel.add(p);

        SpringLayout springLayout = new SpringLayout();
        p.setLayout(springLayout);
        JLabel label = new JLabel(Labels.propertySheetNumber + ":");
        p.add(label);
        JLabel labelValue = new JLabel("");
        p.add(labelValue);
        label = new JLabel("Label: ");
        p.add(label);
        labelValue = new JLabel("numberValue");
        p.add(labelValue);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(
                p,
                2, 2, //rows, cols
                6, 6, //initX, initY
                6, 6);

        return null;
	}

	public JPanel generatePropertySheetsView(List<PropertySheet> sheet) {
		// TODO Auto-generated method stub
		return null;
	}

	public JPanel generateCatastralAreasView(LinkedList<CatastralArea> areas) {
		// TODO Auto-generated method stub
		return null;
	}
}
