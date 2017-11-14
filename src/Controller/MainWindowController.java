package Controller;
import java.util.ArrayList;
import Views.AlternateWindow;
import Model.MachineLearning;
import Model.Metrics.*;
import Views.MainWindow;
import Views.ProblemWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.*;

public class MainWindowController implements ActionListener, ListSelectionListener {
	private ArrayList<MachineLearning> machineLearningArray = new ArrayList<MachineLearning>();
	private MainWindow frame;
	
	/** Constructor
	 * 
	 * @author Logan MacGillivray
	 */
	public MainWindowController(MainWindow frame){
		this.frame = frame;
	}

	/** Looks for a value to be selected from the JList
	 * 
	 * @author Logan MacGillivray
	 */
	public void valueChanged(ListSelectionEvent e) {
		frame.enableAll(true);
	}
	
	/** Performs various actions based on the selected option
	 * 
	 * @author Logan MacGillivray
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("View")){
			new ProblemWindow(new ProblemWindowController(machineLearningArray.get(frame.getJList().getSelectedIndex())));
		} else if(e.getActionCommand().equals("Add Problem")){
			// Create the createPanel properties
			JTextField nameField = new JTextField(5);
			JTextField propertyField = new JTextField(5);
			String[] optionForPanel = {"Next"};
			JPanel createPanel = new JPanel();
			
			// Add features to the createPanel
			createPanel.add(new JLabel("Name:"));
			createPanel.add(nameField);
			createPanel.add(Box.createHorizontalStrut(15));
			createPanel.add(new JLabel("Number of features (in addition to Price):"));
			createPanel.add(propertyField);
			
			// Send JOptionPane to user
			JOptionPane.showOptionDialog(null, createPanel, "Problem Creation", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionForPanel, optionForPanel[0]);
			
			// If the propertyField equals a number
			if(propertyField.getText().matches("[-+]?\\d*\\.?\\d+")){
				if(nameField.getText().equals("")){
					nameField.setText("Unknown Problem");
				}
				// Set up addPropsPanel properties
				optionForPanel[0] = "Enter";
				JPanel addPropsPanel = new JPanel();
				JTextField textFields[] = new JTextField[Integer.parseInt(propertyField.getText())];
				JComboBox comboBoxes[] = new JComboBox[Integer.parseInt(propertyField.getText())];
				addPropsPanel.add(new JLabel("Please enter each property name.\n"));

				// Add a JTextField for every property required for the problem
				for(int i = 0; i < Integer.parseInt(propertyField.getText()); i++){
					textFields[i] = new JTextField(5);
					addPropsPanel.add(textFields[i]);

					// Magic Value, get rid of in next release
					String[] arrayOfMetrics = {"CartesianEuclideanMetric", "DiscreteBinaryMetric", "IntegerAbsoluteMetric"};
					comboBoxes[i] = new JComboBox(arrayOfMetrics);
					addPropsPanel.add(comboBoxes[i]);
					addPropsPanel.add(Box.createHorizontalStrut(15));
				}

				// Send JOptionPane to user
				JOptionPane.showOptionDialog(null, addPropsPanel, 
						"Please Features and Select a Metric", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
						null, new String[] {"Enter"}, "default");

				machineLearningArray.add(new MachineLearning(nameField.getText()));
			    
				// MachineLearning newML = new MachineLearning(nameField.getText());
				int unknownFeature = 1;
				for(int j = 0; j < Integer.parseInt(propertyField.getText()); j++){
					/*add metrics to tmp*/
					if(textFields[j].getText().equals("")){
						textFields[j].setText("UnkownFeature " + unknownFeature);
						unknownFeature++;
					}
					MachineLearning tmp = machineLearningArray.get(machineLearningArray.size() - 1);
					if(comboBoxes[j].getSelectedItem().equals("CartesianEuclideanMetric")){
						tmp.addFeatureLayout(new CartesianEuclideanMetric(textFields[j].getText(), tmp.getStorage()));
					} else if(comboBoxes[j].getSelectedItem().equals("DiscreteBinaryMetric")){
						// Create a JOptionPane to get the number of Discrete Values for this 
						boolean haveANumber = false;
						JTextField numOfDiscrete = new JTextField(5);
						JPanel discreteNumPanel = new JPanel();
						discreteNumPanel.add(new JLabel("Number of discrete values:"));
						discreteNumPanel.add(numOfDiscrete);
						discreteNumPanel.add(Box.createHorizontalStrut(15));
						optionForPanel[0] = "Next";
						while(!haveANumber){
							JOptionPane.showOptionDialog(null, discreteNumPanel, "Problem Creation", 
									JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
									optionForPanel, optionForPanel[0]);
							
							if(numOfDiscrete.getText().matches("[-+]?\\d*\\.?\\d+")){
								haveANumber = true;
							}
						}						
						
						JTextField[] discreteField = new JTextField[Integer.parseInt(numOfDiscrete.getText())];
						JPanel discretePanel = new JPanel();
						for(int k = 0; k < Integer.parseInt(numOfDiscrete.getText()); k++){
							discreteField[k] = new JTextField(5);
							discretePanel.add(discreteField[k]);
							discretePanel.add(Box.createHorizontalStrut(15));
						}
						optionForPanel[0] = "Enter";
						
						JOptionPane.showOptionDialog(null, discretePanel, "Problem Creation", 
								JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
								optionForPanel, optionForPanel[0]);
						
						String[] discreteVals = new String[Integer.parseInt(numOfDiscrete.getText())];
						for(int k = 0; k < Integer.parseInt(numOfDiscrete.getText()); k++){
							discreteVals[k] = discreteField[k].getText();
						}
						tmp.addFeatureLayout(new DiscreteBinaryMetric(textFields[j].getText(), tmp.getStorage(), discreteVals));
						//tmp.getFeatureLayout(j).setDiscreteValues(discreteVals);
					} else if(comboBoxes[j].getSelectedItem().equals("IntegerAbsoluteMetric")){
						tmp.addFeatureLayout(new IntegerAbsoluteMetric(textFields[j].getText(), tmp.getStorage()));
					}
				}
				//machineLearningArray.get(machineLearningArray.size() - 1).addFeatureLayout(new IntegerAbsoluteMetric("Price", machineLearningArray.get(machineLearningArray.size() - 1).getStorage()));
				
				frame.newScreen();
				new ProblemWindow(new ProblemWindowController(machineLearningArray.get(machineLearningArray.size() - 1)));
		    } else {
		    	optionForPanel[0] = "Okay";
		    	JPanel addPropsPanel = new JPanel();
		    	addPropsPanel.add(new JLabel("Please enter a name and value."));
		    	JOptionPane.showOptionDialog(null, addPropsPanel, 
		    		"Error!", JOptionPane.NO_OPTION, JOptionPane.ERROR_MESSAGE, 
		    		null, new String[] {"Okay"}, "default");
			}
		}
	}
	
	/** Gets all the Strings for the JList
	 * 
	 * @author Logan MacGillivray
	 */
	public DefaultListModel<String> getProblems(){
		DefaultListModel<String> tmp = new DefaultListModel<String>();
		for(int i = 0; i < machineLearningArray.size(); i++){
			tmp.addElement(machineLearningArray.get(i).getProblem());
		}
		return tmp;
	}
	
	/** Gets all the Strings for setting the JList
	 * 
	 * @author Logan MacGillivray
	 */
	public String[] getProblemsArray(){
		String[] arg = new String[machineLearningArray.size()];
		for(int i = 0; i < arg.length; i++){
			 arg[i] = machineLearningArray.get(i).getProblem();
		}
		return arg;
	}
}
