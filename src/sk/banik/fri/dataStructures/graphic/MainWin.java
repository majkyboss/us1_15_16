package sk.banik.fri.dataStructures.graphic;

import sk.banik.fri.dataStructures.CatasterService;
import sk.banik.fri.dataStructures.CatasterService.KeyValuePair;
import sk.banik.fri.dataStructures.CatasterServiceNumbered;
import sk.banik.fri.dataStructures.Labels;
import sk.banik.fri.dataStructures.Operations;
import sk.banik.fri.dataStructures.controll.CSVSupport;
import sk.banik.fri.dataStructures.controll.ControllerFactory;
import sk.banik.fri.dataStructures.model.CatastralArea;
import sk.banik.fri.dataStructures.model.Owner;
import sk.banik.fri.dataStructures.model.Property;
import sk.banik.fri.dataStructures.model.PropertySheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWin extends JFrame {
	private static final long serialVersionUID = 4968949784361287814L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainWin app = new MainWin();
				app.setController(ControllerFactory.createController());
				app.setVisible(true);
			}
		});
	}

	private CatasterService controller;
	private JFileChooser fileChooser = new JFileChooser();
	private Logger LOG = Logger.getLogger(MainWin.class.getName());
	protected GUIHelper guiHelper;

	public MainWin() throws HeadlessException {
		super();
		
		initUI();
		
		testAction();
	}

	private void initUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setSize(300, 200);
		
		guiHelper = new GUIHelper(this);
		createMenuBar();
	}

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setToolTipText("Exit application");
		exitItem.setMnemonic(KeyEvent.VK_E);
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		file.add(exitItem);
		menuBar.add(file);
		
		JMenu importMenu = new JMenu("Import");
		importMenu.setMnemonic(KeyEvent.VK_I);
		
		JMenuItem deleteDBItem = new JMenuItem("Delete DB");
		deleteDBItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deleteDB();
			}
		});
		importMenu.add(deleteDBItem);
		JMenuItem importItem = new JMenuItem("Import");
		importItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				importAction();
			}
		});
		importMenu.add(importItem);
		JMenuItem exportItem = new JMenuItem("Export");
		exportItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exportAction();
			}
		});
		importMenu.add(exportItem);
		menuBar.add(importMenu);
		
		initNumberedItems(menuBar);
		// TODO if will be enough time
//		initLogicalSortedItems(menuBar);
		
		// TODO delete - for testing only
		JMenu testMenu = new JMenu("test");
		JMenuItem item = new JMenuItem("show output view");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				testAction();
			}
		});
		testMenu.add(item);
		menuBar.add(testMenu);
		
		setJMenuBar(menuBar);
	}

	protected void testAction() {
		Property p = new Property();
		p.setAddress("address 1");
		p.setRegisterNumber(5555);

//		showPanel(guiHelper.generatePropertyView(p));
//		LinkedList<Property> props = new LinkedList<>();
//		props.add(p);
//		showPanel(guiHelper.generatePropertiesView(props));

		PropertySheet sheet = new PropertySheet();
		sheet.addProperty(p);
		Owner o = new Owner();
		o.setFirstName("Dindi");
		o.setLastname("Konak");
		o.setBornNumber("1234568888");
		o.setResidance(p);
		sheet.addOwner(o, 1d);
//		showPanel(guiHelper.generatePropertySheetView(sheet));

		List<PropertySheet> sheets = new LinkedList<>();
		sheets.add(sheet);
		sheet = new PropertySheet();
		p = new Property();
		p.setAddress("address 2");
		p.setRegisterNumber(8888);
		sheet.addProperty(p);
		o = new Owner();
		o.setFirstName("Tantal");
		o.setLastname("Sui");
		o.setBornNumber("6543218888");
		sheet.addOwner(o, 1d);
		sheets.add(sheet);
//		showPanel(guiHelper.generatePropertySheetsView(sheets));

		LinkedList<CatastralArea> areas = new LinkedList<>();
		CatastralArea area = new CatastralArea();
		area.setName("area 1");
		areas.add(area);
		area = new CatastralArea();
		area.setName("area 2");
		areas.add(area);
		showPanel(guiHelper.generateCatastralAreasView(areas));
	}

	protected void exportAction() {
		// load value from DB - prepare for exporting
		List<KeyValuePair> exportItems = controller.exportDB();
		// ask user for file
		fileChooser.setMultiSelectionEnabled(false);
		int returnValue = fileChooser.showOpenDialog(MainWin.this);
		if (returnValue != JFileChooser.APPROVE_OPTION) {
			LOG.log(Level.INFO, "No export file was selected");
			return;
		} else {
			File file = fileChooser.getSelectedFile();
			LOG.log(Level.INFO, "Export file selected");
			CSVSupport.saveToFile(file, exportItems);
			LOG.log(Level.INFO, "DB export finished");
		}
	}

	protected void importAction() {
		// ask user for input file
		fileChooser.setMultiSelectionEnabled(false);
		int returnValue = fileChooser.showOpenDialog(MainWin.this);
		if (returnValue != JFileChooser.APPROVE_OPTION) {
			LOG.log(Level.INFO, "No import file was selected");
			return;
		} else {
			File file = fileChooser.getSelectedFile();
			LOG.log(Level.INFO, "Import file selected");
			// load file via CSVSupport class
			// give the loaded values to controller
			controller.importDB(CSVSupport.loadFile(file));
			LOG.log(Level.INFO, "DB import finished");
		}
	}
	
	private void showWrongInputsDialog(){
		JOptionPane.showMessageDialog(this,
			    Labels.wrongInputsMsg,
			    Labels.wrongInputsTitle,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	private void initNumberedItems(JMenuBar menuBar) {
		JMenu numberedOperationsMenu = new JMenu("Numbered");
		numberedOperationsMenu.setMnemonic(KeyEvent.VK_N);
		
		CatasterServiceNumbered numberedController = new CatasterServiceNumbered(controller);
		
		JMenuItem op1 = new JMenuItem("Operation 1");
		op1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// show input panel
				// - get input elements - from Operation 1
				// - generate GUI elements via GUIHelper
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP1);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							
							numberedController.operation_1(
									p0,
									p1);
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(op1);
		
		JMenuItem opItem = new JMenuItem("Operation 2");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP2);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						numberedController.operation_2(
								panel.inputBoxes.get(0).getText());
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 3");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP3);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							int p2 = Integer.parseInt(panel.inputBoxes.get(2).getText());
							
							numberedController.operation_3(
								p0,
								p1,
								p2);
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 4");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP4);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							
							numberedController.operation_4(
								p0,
								p1);
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 5");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP5);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							
							numberedController.operation_5(
								p0,
								panel.inputBoxes.get(1).getText());
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 6");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP6);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							
							numberedController.operation_6(
								p0,
								panel.inputBoxes.get(1).getText());
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 7");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP7);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						numberedController.operation_7(
								panel.inputBoxes.get(0).getText());
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 8");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP8);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							
							numberedController.operation_8(
								panel.inputBoxes.get(0).getText(),
								p1);
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 9");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP9);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						numberedController.operation_9(
								panel.inputBoxes.get(0).getText());
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 10");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP10);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							
							numberedController.operation_10(
								panel.inputBoxes.get(0).getText(),
								p1,
								panel.inputBoxes.get(2).getText());
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 11");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP11);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							
							numberedController.operation_11(
								p0,
								p1,
								panel.inputBoxes.get(2).getText(),
								panel.inputBoxes.get(3).getText());
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 12");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP12);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							double p3 = Double.parseDouble(panel.inputBoxes.get(3).getText());
							
							numberedController.operation_12(
								p0,
								p1,
								panel.inputBoxes.get(2).getText(),
								p3);
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 13");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP13);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							
							numberedController.operation_13(
								p0,
								p1,
								panel.inputBoxes.get(2).getText());
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 14");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP14);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						numberedController.operation_14();
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 15");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP15);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						numberedController.operation_15(
								panel.inputBoxes.get(0).getText(),
								panel.inputBoxes.get(1).getText(),
								panel.inputBoxes.get(2).getText());
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 16");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP16);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							
							numberedController.operation_16(
								p0);
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 17");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP17);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							int p2 = Integer.parseInt(panel.inputBoxes.get(2).getText());
							
							numberedController.operation_17(
								p0,
								p1,
								p2);
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 18");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP18);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						numberedController.operation_18(
								panel.inputBoxes.get(0).getText());
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 19");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP19);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							int p2 = Integer.parseInt(panel.inputBoxes.get(2).getText());
							int p3 = Integer.parseInt(panel.inputBoxes.get(3).getText());
							
							numberedController.operation_19(
								p0,
								p1,
								p2,
								p3);
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 20");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP20);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							int p2 = Integer.parseInt(panel.inputBoxes.get(2).getText());
							
							numberedController.operation_20(
								p0,
								p1,
								p2);
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 21");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP21);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						numberedController.operation_21(
								panel.inputBoxes.get(0).getText());
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		opItem = new JMenuItem("Operation 22");
		opItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputsPanel panel = guiHelper.generateInputsJPanel(Operations.OP22);
				panel.sendButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							int p0 = Integer.parseInt(panel.inputBoxes.get(0).getText());
							int p1 = Integer.parseInt(panel.inputBoxes.get(1).getText());
							
							numberedController.operation_22(
								p0,
								p1);
						} catch (NumberFormatException ex) {
							// wrong input
							showWrongInputsDialog();
							return;
						}
					}
				});
				showPanel(panel);
			}
		});
		numberedOperationsMenu.add(opItem);

		menuBar.add(numberedOperationsMenu);
	}

	public void showPanel(JPanel panel) {
		Container mainContent = getContentPane();
		mainContent.removeAll();
		GroupLayout gl = new GroupLayout(mainContent);
		mainContent.setLayout(gl);
		
		gl.setAutoCreateContainerGaps(true);
		gl.setHorizontalGroup(gl.createSequentialGroup().addComponent(panel));
		gl.setVerticalGroup(gl.createSequentialGroup().addComponent(panel));
		
		revalidate();
		pack();
	}

	public void setController(CatasterService catasterService) {
		controller = catasterService;
//		if (controller != null ) controller.setView(this);
	}
}
