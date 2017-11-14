package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.MachineLearning;
import Model.Storage;
import Model.Features.GenericFeature;
import Model.Metrics.CartesianEuclideanMetric;
import Model.Metrics.DiscreteBinaryMetric;
import Model.Metrics.IntegerAbsoluteMetric;
import Views.AlternateWindow;
import Views.ProblemWindow;

public class ProblemWindowController implements ActionListener, ListSelectionListener{
	private ArrayList<String> storageKeys = new ArrayList<String>();
	private ProblemWindow frame;
	private MachineLearning machineLearning;
	
	/** Constructor for ProblemWindowController
	 * 
	 * @author Logan MacGillivray
	 */
	public ProblemWindowController(MachineLearning machine){
		machineLearning = machine;
		String[] keys = machine.getStorage().getLearned().keySet().toArray(new String[machine.getStorage().getLearned().size()]);
		for (int i = 0; i < keys.length; i++){
			storageKeys.add(keys[i]);
		}
	}
	
	/** Sets the frame, used by ProblemWindow
	 * 
	 * @author Logan MacGillivray
	 */
	public void setFrame(ProblemWindow frame) {
		this.frame = frame;
	}
	
	/** Returns the ProblemWindow this controller is assigned to
	 * 
	 * @author Logan MacGillivray
	 */
	public ProblemWindow getWindow(){
		return frame;
	}
	
	/** Sets disabled options to enabled
	 * 
	 * @author Logan MacGillivray
	 */
	public void valueChanged(ListSelectionEvent e) {
		frame.enableAll(true);
	}
	
	/** Performs various actions depending on which option is chosen
	 * 
	 * @author Logan MacGillivray
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Edit")){
			new AlternateWindow(new FeatureController(this,"edit",frame.getJList().getSelectedValue()));
		} else if(e.getActionCommand().equals("Learn Example")){
			new AlternateWindow(new FeatureController(this, "add",name()));
		} else if(e.getActionCommand().equals("Remove")){
			machineLearning.getStorage().remove(frame.getJList().getSelectedValue());
			frame.newScreen();
		} else if(e.getActionCommand().equals("Predict Price")){			
			new AlternateWindow(new FeatureController(this, "add",name()));
		}
		frame.newScreen();
	}
	
	/** Returns all the keys for the problem
	 * 
	 * Error in this code, known issue for next release
	 * 
	 * @author Logan MacGillivray
	 */
	public DefaultListModel<String> getProblems(){
		DefaultListModel<String> tmp = new DefaultListModel<String>();
		for(int i = 0; i < storageKeys.size(); i++){
			tmp.addElement(storageKeys.get(i));
		}
		return tmp;  
	}
	
	/** Returns a String[] of all the keys in storage
	 * 
	 * @author Logan MacGillivray
	 */
	public String[] getProblemsArray(){
		return machineLearning.getStorage().getLearned().keySet().toArray(new String[machineLearning.getStorage().getLearned().size()]);
	}
	
	/** Gets name of problem
	 * 
	 * @author Logan MacGillivray
	 */
	public String toString(){
		return machineLearning.getProblem();
	}
	
	/** Used to pass the machine learning to other functions
	 * 
	 * @author Logan MacGillivray
	 */
	public MachineLearning getMachine() {
		return machineLearning;
	}
	
	/** Gets the name for a new instance of the problem
	 * 
	 * @author Logan MacGillivray
	 */
	private String name(){
		String[] option = {"Enter"};
		JPanel addPropsPanel = new JPanel();
		JTextField nameField = new JTextField(5);
		addPropsPanel.add(new JLabel("Please enter the name of the instance.\n"));
		addPropsPanel.add(nameField);
		
		JOptionPane.showOptionDialog(null, addPropsPanel, "Problem Creation", 
				JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
				option, option[0]);
		return nameField.getText();
	}
}