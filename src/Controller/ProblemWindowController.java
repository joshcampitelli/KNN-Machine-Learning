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
	public void valueChanged(ListSelectionEvent e) {
		frame.enableAll(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Edit")){
			new AlternateWindow(new FeatureController(machineLearning,"edit"));
		} else if(e.getActionCommand().equals("Add")){
			new AlternateWindow(new FeatureController(machineLearning, "add"));
		} else if(e.getActionCommand().equals("Remove")){
			
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
	
}