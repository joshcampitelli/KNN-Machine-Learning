package Controller;
import java.util.ArrayList;
import Model.MachineLearning;
import Model.Metrics.*;
import Views.MainWindow;
import Views.ProblemWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.*;

/** A controller for MainWindow, this class handles all events 
 *  the JFrame has to deal with.
 * 
 * @author Logan MacGillivray
 */
public class MainWindowController implements ActionListener, ListSelectionListener {
	private ArrayList<MachineLearning> machineLearningArray;	/* List of MachineLearning instances,
																 * 		different problems the user is working on */
	private MainWindow frame;									// The MainWindow this controls 
	
	/** Constructs a controller for the MainWindow.  This
	 *  will contain the Action and ListSelection Listeners
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @param frame - the MainWindow that the controller controls
	 */
	public MainWindowController(MainWindow frame){
		this.frame = frame;
		this.machineLearningArray = new ArrayList<MachineLearning>();
	}

	/** ListSelectionListener for the MainWindow, checks if an 
	 *  item has been selected from the JList
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @param e - event which triggers this function
	 */
	public void valueChanged(ListSelectionEvent e) {
		frame.enableAll(true);
	}
	
	/** ActionListener for MainWindow.  Runs the command associated 
	 *  with each JMenuItem
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @param e - event which triggers this method
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void actionPerformed(ActionEvent e) {
		// View the selected instance of MachineLearning (problem)
		if(e.getActionCommand().equals("View")){
			new ProblemWindow(new ProblemWindowController(machineLearningArray.get(frame.getJList().getSelectedIndex())));
		// Save the selected problem
		} else if(e.getActionCommand().equals("Save")){
			machineLearningArray.get(frame.getJList().getSelectedIndex()).serialSave(makeFileChooser("Save").getSelectedFile().getAbsolutePath() + ".bin");
		// Load in a previously saved problem
		} else if(e.getActionCommand().equals("Load")){
			machineLearningArray.add(new MachineLearning("tmp").serialOpen(makeFileChooser("Open").getSelectedFile().getAbsolutePath()));
			frame.newScreen();
		// Add a new problem to the session
		} else if(e.getActionCommand().equals("Add Problem")){
			// Create a JPanel to get basic information on the problem
			// the user wants to make.
			JTextField nameField = new JTextField(5);
			JTextField propertyField = new JTextField(5);
			JPanel createPanel = new JPanel();
			createPanel = addToPanel(createPanel, new JComponent[] {new JLabel("Name"), nameField,
					(JComponent) Box.createHorizontalStrut(15), new JLabel("Number of Features:"), propertyField});
			
			// Send JOptionPane to user
			JOptionPane.showOptionDialog(null, createPanel, "Problem Creation", 
					JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
					new String[] {"Next"}, "Next");
			
			// If the propertyField equals a number
			if(propertyField.getText().matches("[-+]?\\d*\\.?\\d+")){
				if(nameField.getText().equals("")){
					nameField.setText("Unknown Problem");
				}
				// Set up addPropsPanel properties
				JPanel addPropsPanel = new JPanel();
				JTextField textFields[] = new JTextField[Integer.parseInt(propertyField.getText())];
				JComboBox comboBoxes[] = new JComboBox[Integer.parseInt(propertyField.getText())];
				addPropsPanel.add(new JLabel("Please enter each property name.\n"));

				// Add a JTextField for every property required for the problem
				for(int i = 0; i < Integer.parseInt(propertyField.getText()); i++){
					textFields[i] = new JTextField(5);
					String[] arrayOfMetrics = {"CartesianEuclideanMetric", "DiscreteBinaryMetric", "IntegerAbsoluteMetric", "DoubleAbsoluteMetric", "PolarMetric"};
					comboBoxes[i] = new JComboBox(arrayOfMetrics);
					addPropsPanel = addToPanel(addPropsPanel, new JComponent[] {textFields[i], comboBoxes[i],(JComponent) Box.createHorizontalStrut(15)});
				}

				// Send JOptionPane to user
				JOptionPane.showOptionDialog(null, addPropsPanel, 
						"Please Features and Select a Metric", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
						null, new String[] {"Enter"}, "default");

				// Create new instance of MachineLearning, set it to a local variable for ease of reading
				machineLearningArray.add(new MachineLearning(nameField.getText()));
				MachineLearning newML = machineLearningArray.get(machineLearningArray.size() - 1);
			    
				int unknownFeature = 1;
				for(int j = 0; j < Integer.parseInt(propertyField.getText()); j++){
					// Set empty textFields to be an unknown feature
					if(textFields[j].getText().equals("")){
						textFields[j].setText("UnkownFeature " + unknownFeature);
						unknownFeature++;
					}
					
					// Add the Metric (and its associated feature) to the MachineLearning instance
					if(comboBoxes[j].getSelectedItem().equals("CartesianEuclideanMetric")){
						newML.addRequiredFeature(new CartesianEuclideanMetric(textFields[j].getText(), newML.getStorage()));
					} else if(comboBoxes[j].getSelectedItem().equals("DiscreteBinaryMetric")){
						DiscreteFeatureSettings dfs = new DiscreteFeatureSettings();
						newML.addRequiredFeature(new DiscreteBinaryMetric(textFields[j].getText(), newML.getStorage(), dfs.getVals()));
					} else if(comboBoxes[j].getSelectedItem().equals("IntegerAbsoluteMetric")){
						newML.addRequiredFeature(new IntegerAbsoluteMetric(textFields[j].getText(), newML.getStorage()));
					} else if(comboBoxes[j].getSelectedItem().equals("DoubleAbsoluteMetric")){
						newML.addRequiredFeature(new DoubleAbsoluteMetric(textFields[j].getText(), newML.getStorage()));
					} else if(comboBoxes[j].getSelectedItem().equals("PolarMetric")){
						newML.addRequiredFeature(new PolarMetric(textFields[j].getText(), newML.getStorage()));
					}	// End of if
				}	// End of for-loop
				
				// Find what feature is to be predictable
				newML.setPredictable(new FindPredictable(textFields).getStr());
				frame.newScreen();
				// Pop up a window for the problem
				new ProblemWindow(new ProblemWindowController(machineLearningArray.get(machineLearningArray.size() - 1)));
			// If propertyField is not a number, send error
			} else {
		    	JPanel addPropsPanel = new JPanel();
		    	addPropsPanel.add(new JLabel("Please enter a name and value."));
		    	JOptionPane.showOptionDialog(null, addPropsPanel, 
		    		"Error!", JOptionPane.NO_OPTION, JOptionPane.ERROR_MESSAGE, 
		    		null, new String[] {"Okay"}, "default");
			}	// End of if, checks for proper input of numbers
		}	// End of if, checks for calling command
	}
	
	/** This function creates a JFileChooser for the save/load
	 *  feature of the actionPerformed function.
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @param saveOrOpen - "Save" for saveDialogOption, anything else for openDialogOption
	 * @return A JFileChooser to save or open a file
	 */
	private JFileChooser makeFileChooser(String saveOrOpen) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));
		fileChooser.setDialogTitle("Select a file");
		if (saveOrOpen.equals("Save")) {
			fileChooser.showSaveDialog(new JButton());
		} else {
			fileChooser.showOpenDialog(new JButton());
		}
		return fileChooser;
	}

	/** Adds elements to a JPanel, used for condensing lines of code
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @param panel - the JPanel which will receive new JComponents
	 * @param component - an array of JComponents to be added to the JPanel
	 * @return a new JPanel
	 */
	private JPanel addToPanel(JPanel panel, JComponent[] component) { 
		for(int i = 0; i < component.length; i++) {
			panel.add(component[i]);
		}
		return panel;
	}
	
	/** Creates a ListModel of MachineLearning problems.  Used for
	 *  making the JList.
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @return A ListModel of Strings containing the MachineLearning problems
	 */
	public DefaultListModel<String> getProblems(){
		DefaultListModel<String> tmp = new DefaultListModel<String>();
		for(int i = 0; i < machineLearningArray.size(); i++){
			tmp.addElement(machineLearningArray.get(i).getProblem());
		}
		return tmp;
	}
	
	/** Creates an array of MachineLearning problems
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @return An array of Strings containing the MachineLearning problems
	 */
	public String[] getProblemsArray(){
		String[] arg = new String[machineLearningArray.size()];
		for(int i = 0; i < arg.length; i++){
			 arg[i] = machineLearningArray.get(i).getProblem();
		}
		return arg;
	}
}
