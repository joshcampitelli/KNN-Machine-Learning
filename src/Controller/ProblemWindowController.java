package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.MachineLearning;
import Views.AlternateWindow;
import Views.ProblemWindow;

/** A controller for ProblemWindow, this class handles all events 
 *  the JFrame has to deal with.
 * 
 * @author Logan MacGillivray
 */
public class ProblemWindowController implements ActionListener, ListSelectionListener{
	private ArrayList<String> storageKeys;		// ArrayList of the Keys in machineLearning's storage
	private ProblemWindow frame;				// JFrame this controller controls
	private MachineLearning machineLearning;	// The instance of MachineLearning the JFrame orients around
	
	/** Constructs a controller for the ProblemWindow.  This
	 *  will contain the Action and ListSelection Listeners
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @param frame - the MachineLearning the window is oriented around
	 */
	public ProblemWindowController(MachineLearning machine){
		storageKeys = new ArrayList<String>();
		machineLearning = machine;
		String[] keys = machine.getStorage().getLearned().keySet().toArray(new String[machine.getStorage().getLearned().size()]);
		for (int i = 0; i < keys.length; i++){
			storageKeys.add(keys[i]);
		}
	}
	
	/** Sets the JFrame for the controller
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @param frame - the ProblemWindow this window controls
	 */
	public void setFrame(ProblemWindow frame) {
		this.frame = frame;
	}
	
	/** Public accessor to the JFrame this controller controls
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @return ProblemWindow
	 */
	public ProblemWindow getWindow(){
		return frame;
	}
	
	/** ListSelectionListener for the ProblemWindow, checks if an 
	 *  item has been selected from the JList
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @param e - event which triggers this function
	 */
	public void valueChanged(ListSelectionEvent e) {
		frame.enableAll(true);
	}
	
	/** ActionListener for ProblemWindow.  Runs the command associated 
	 *  with each JMenuItem
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @param e - event which triggers this method
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Edit")){
			new AlternateWindow(new FeatureController(this,"edit",frame.getJList().getSelectedValue().split(":",2)[0]));
		} else if(e.getActionCommand().equals("Save")){
			// Save the file to the selected file location
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new java.io.File("."));
			fileChooser.setDialogTitle("Select a file");
			fileChooser.showSaveDialog(new JButton());
				
			machineLearning.serialSave(fileChooser.getSelectedFile().getAbsolutePath() + MainWindowController.getFiletype());
		} else if(e.getActionCommand().equals("Learn Example")){
			new AlternateWindow(new FeatureController(this, "add",name()));
		} else if(e.getActionCommand().equals("Remove")){
			// Get the name of selected values and remove it from machineLearning
			machineLearning.getStorage().remove(frame.getJList().getSelectedValue().split(":",2)[0]);
			frame.newScreen();
		} else if(e.getActionCommand().equals("Predict Price")){			
			new AlternateWindow(new FeatureController(this, "add",name()));
		}
		frame.newScreen();
	}
	
	/** Creates a ListModel (for a JList) of all the learned examples
	 *  in the instance of MachineLearning
	 * 
	 * @author Logan MacGillivray
	 * 
	 * return ListModel of learned examples and their features
	 */
	public DefaultListModel<String> getLearnedExamples(){
		DefaultListModel<String> tmp = new DefaultListModel<String>();
		for(String key : storageKeys) {
			tmp.addElement(key + ":    " + machineLearning.getStorage().getFeatureString(key));
		}
		return tmp;  
	}
	
	/** Creates an array (for a JList) of all the learned examples
	 *  in the instance of MachineLearning
	 * 
	 * @author Logan MacGillivray
	 * 
	 * return Array of learned examples and their features
	 */
	public String[] getLearnedArray(){
		String[] tmp = new String[machineLearning.getStorage().getSize()];
		String[] keys = machineLearning.getStorage().getLearned().keySet().toArray(new String[machineLearning.getStorage().getLearned().size()]);
		
		for(int i = 0; i < machineLearning.getStorage().getSize(); i++){
			tmp[i] = keys[i] + ":    " + machineLearning.getStorage().getFeatureString(keys[i]);
		}
		return tmp;
	}
	
	/** Public accessor to the name of the problem being worked on
	 * 
	 * @author Logan MacGillivray
	 * 
	 * return machineLearning's problem (name)
	 */
	public String toString(){
		return machineLearning.getProblem();
	}
	
	/** Public accessor to machineLearning
	 * 
	 * @author Logan MacGillivray
	 * 
	 * return machineLearning
	 */
	public MachineLearning getMachine() {
		return machineLearning;
	}
	
	/** When creating a new problem, this function gets the name
	 *  of said problem
	 * 
	 * @author Logan MacGillivray
	 * 
	 * return name String
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
		
		if(nameField.getText().equals("")){
			return "Unknown Instance";
		} else {
			return nameField.getText();
		}
	}
}