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
	
	public ProblemWindowController(MachineLearning machine){
		machineLearning = machine;
		String[] keys = machine.getStorage().getLearned().keySet().toArray(new String[machine.getStorage().getLearned().size()]);
		for (int i = 0; i < keys.length; i++){
			storageKeys.add(keys[i]);
		}
	}
	
	public void setFrame(ProblemWindow frame) {
		this.frame = frame;
	}
	
	public ProblemWindow getWindow(){
		return frame;
	}
	
	public void valueChanged(ListSelectionEvent e) {
		frame.enableAll(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Edit")){
			new AlternateWindow(new FeatureController(this,"edit","H1"));
		} else if(e.getActionCommand().equals("Learn Example")){
			new AlternateWindow(new FeatureController(this, "add",name()));
		} else if(e.getActionCommand().equals("Remove")){
			machineLearning.getStorage().remove(frame.getJList().getSelectedValue());
			frame.newScreen();
		} else if(e.getActionCommand().equals("Predict Price")){
			JPanel addPropsPanel = new JPanel();
			JTextField nameField = new JTextField();
			addPropsPanel.add(new JLabel("Please enter the name of the instance.\n"));
			addPropsPanel.add(nameField);
			
			new AlternateWindow(new FeatureController(this, "add",name()));
		}
	}
	
	public DefaultListModel<String> getProblems(){
		DefaultListModel<String> tmp = new DefaultListModel<String>();
		for(int i = 0; i < storageKeys.size(); i++){
			tmp.addElement(storageKeys.get(i));
		}
		return tmp;  
	}
	
	public String[] getProblemsArray(){
		String[] arg = new String[storageKeys.size()];
		for(int i = 0; i < arg.length; i++){
			 arg[i] = storageKeys.get(i);
		}
		return arg;
	}
	
	public String toString(){
		return machineLearning.getProblem();
	}
	
	public MachineLearning getMachine() {
		return machineLearning;
	}
	
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