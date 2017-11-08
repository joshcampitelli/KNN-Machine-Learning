package Controller;
import java.util.ArrayList;
import Views.AlternateWindow;
import Model.MachineLearning;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.DefaultListModel;
//import Views.EditWindow;
import javax.swing.JOptionPane;

public class MainWindowController implements ActionListener, ListSelectionListener {
	private ArrayList<MachineLearning> machineLearningArray = new ArrayList<MachineLearning>();

	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Edit")){
			//System.out.println("Hello World");
			new AlternateWindow(new MetricController(machineLearningArray.get(machineLearningArray.size())));
		} else if(e.getActionCommand().equals("Add Problem")){
			JOptionPane option = new JOptionPane();
			@SuppressWarnings("static-access")
			String problemName = option.showInputDialog(null,"Please enter the name of your problem:","Start New Problem",JOptionPane.QUESTION_MESSAGE);
			machineLearningArray.add(new MachineLearning(problemName));
			new AlternateWindow(new MetricController(machineLearningArray.get(machineLearningArray.size()-1)));
			
		}
	}
	
	public DefaultListModel<String> getProblems(){
		DefaultListModel<String> tmp = new DefaultListModel<String>();
		for(int i = 0; i < machineLearningArray.size(); i++){
			tmp.addElement(machineLearningArray.get(i).getProblem());
		}
		return tmp;
	}

}
