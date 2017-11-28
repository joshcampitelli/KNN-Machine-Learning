package Controller;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DiscreteFeatureSettings extends JOptionPane{
	private static final long serialVersionUID = 1L;
	private String[] discreteVals;
	
	public DiscreteFeatureSettings(){
		// Get the number of options for a DiscreteFeature
		JTextField numOfDiscrete = new JTextField("",5);
		JPanel discreteNumPanel = new JPanel();
		discreteNumPanel = addToPanel(discreteNumPanel, new JComponent[] {new JLabel("Number of discrete values:"), numOfDiscrete, (JComponent) Box.createHorizontalStrut(15)});
		while(!numOfDiscrete.getText().matches("[-+]?\\d*\\.?\\d+")){
			JOptionPane.showOptionDialog(null, discreteNumPanel, "Problem Creation", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"Next"}, "Next");
		}	// End of while-loop				
		
		// Get the names of all the DiscreteFeatures
		JTextField[] discreteField = new JTextField[Integer.parseInt(numOfDiscrete.getText())];
		JPanel discretePanel = new JPanel();
		discretePanel = addToPanel(discretePanel, new JComponent[] {new JLabel("Please enter all the values of your ")});
		for(int k = 0; k < Integer.parseInt(numOfDiscrete.getText()); k++){
			discreteField[k] = new JTextField("", 5);
			discretePanel = addToPanel(discretePanel, new JComponent[] {discreteField[k], (JComponent) Box.createHorizontalStrut(15)});
		}	// End of for-loop
		while(!allDiscretesHaveVals(discreteField)){
			JOptionPane.showOptionDialog(null, discretePanel, "Problem Creation", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"Enter"}, "Enter");
		}
		
		discreteVals = new String[Integer.parseInt(numOfDiscrete.getText())];
		for(int k = 0; k < Integer.parseInt(numOfDiscrete.getText()); k++){
			discreteVals[k] = discreteField[k].getText();
		}	// End of for-loop
	}
	
	private JPanel addToPanel(JPanel panel, JComponent[] component) {
		for(int i = 0; i < component.length; i++) {
			panel.add(component[i]);
		}	// End of for-loop
		
		return panel;
	}	// End of addToPanel
	
	public String[] getVals() {
		return discreteVals;
	} 	// End of getVals()

	private boolean allDiscretesHaveVals(JTextField[] texts) { 
		boolean valuesFilled = true;
		for(int i = 0; i < texts.length; i++){
			if(texts[i].getText().equals("")){
				valuesFilled = false;
			}
		}
		return valuesFilled;
	}
}
