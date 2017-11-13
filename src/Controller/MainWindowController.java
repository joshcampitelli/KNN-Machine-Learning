package Controller;
import java.util.ArrayList;
import Views.AlternateWindow;
import Model.MachineLearning;
import Model.Features.*;
import Views.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.*;

public class MainWindowController implements ActionListener, ListSelectionListener {
	private ArrayList<MachineLearning> machineLearningArray = new ArrayList<MachineLearning>();
	private MainWindow frame;
	
	public MainWindowController(MainWindow frame){
		this.frame = frame;
	}

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Edit")){
			//System.out.println("Hello World");
			new AlternateWindow(new FeatureController(machineLearningArray.get(machineLearningArray.size())));
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
		    createPanel.add(new JLabel("Number of features:"));
		    createPanel.add(propertyField);
		    
		    // Send JOptionPane to user
		    JOptionPane.showOptionDialog(null, createPanel, "Problem Creation", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionForPanel, optionForPanel[0]);
		    
		    // If the propertyField equals a number
		    if((propertyField.getText().matches("[-+]?\\d*\\.?\\d+")) && (!nameField.equals(""))){
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
			    	
			    	if(comboBoxes[j].getSelectedItem().equals("CartesianEuclideanMetric")){
			    		machineLearningArray.get(machineLearningArray.size() - 1).addFeatureLayout(textFields[j].getText(),"CartesianFeature");
			    	} else if(comboBoxes[j].getSelectedItem().equals("DiscreteBinaryMetric")){
			    		machineLearningArray.get(machineLearningArray.size() - 1).addFeatureLayout(textFields[j].getText(),"EnumFeature");
			    	} else if(comboBoxes[j].getSelectedItem().equals("IntegerAbsoluteMetric")){
			    		machineLearningArray.get(machineLearningArray.size() - 1).addFeatureLayout(textFields[j].getText(),"IntegerFeature");
			    	}
			    }
			    
			    new AlternateWindow(new FeatureController(machineLearningArray.get(machineLearningArray.size() - 1)));
			    frame.newScreen();
			    
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
	
	public DefaultListModel<String> getProblems(){
		DefaultListModel<String> tmp = new DefaultListModel<String>();
		for(int i = 0; i < machineLearningArray.size(); i++){
			tmp.addElement(machineLearningArray.get(i).getProblem());
		}
		return tmp;
	}
	
	public String[] getProblemsArray(){
		String[] arg = new String[machineLearningArray.size()];
		for(int i = 0; i < arg.length; i++){
			 arg[i] = machineLearningArray.get(i).getProblem();
		}
		return arg;
	}

}
