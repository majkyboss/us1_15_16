package sk.banik.fri.dataStructures.graphic;

import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputsPanel extends JPanel{
	public final LinkedList<JTextField> inputBoxes = new LinkedList<JTextField>();
	public JButton sendButton;
}