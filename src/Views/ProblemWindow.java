package Views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.ListSelectionModel;
import Controller.ProblemWindowController;

/** The ProblemWindow deals with showing a specific instance of MachineLearning
 * 
 * @author Logan MacGillivray
 */
@SuppressWarnings("serial")
public class ProblemWindow extends JFrame {
	private ProblemWindowController problemControl;
	private JList<String> listOfObjects;
	private JMenuItem editMenuItem, removeMenuItem;
	
	/** Construct a new JFrame to be used for the ProblemWindow
	 * 
	 * @author Logan MacGillivray
	 */
	public ProblemWindow(ProblemWindowController control){
		super(control.toString());
		problemControl = control;
		problemControl.setFrame(this);
		
		// Set the window to be in the middle of the screen
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dimension.width/2, dimension.height/2);
		setLocation(dimension.width/2 - this.getWidth()/2, dimension.height/2 - this.getHeight()/2);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		// This creates a JMenu with all JMenuItems initiated 
		// Just like in MainWindow, this function call is just of ease of reading
		createMenu();
		
		// Add a list of learned examples to the JFrame
		listOfObjects = new JList<String>(problemControl.getLearnedExamples());
		listOfObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOfObjects.addListSelectionListener(problemControl);
		add(listOfObjects, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	/** Creates a JMenu for the MainWindow.  Removed from
	 *  constructor for ease of reading.
	 * 
	 * @author Logan MacGillivray
	 */
	private void createMenu(){
		// Create JMenuBar with a JMenu called File
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		// Create a JMenuItem for saving, add it to the file JMenu
		JMenuItem saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(problemControl);
		fileMenu.add(saveMenuItem);
		// Create a JMenuItem for adding, add it to the file JMenu
		JMenuItem addMenuItem = new JMenuItem("Learn Example");
		addMenuItem.addActionListener(problemControl);
		fileMenu.add(addMenuItem);
		
		// Create a JMenuItem for editing, add it to the edit JMenu
		editMenuItem = new JMenuItem("Edit");
		editMenuItem.setEnabled(false);
		editMenuItem.addActionListener(problemControl);
		editMenu.add(editMenuItem);
		
		// Create a JMenuItem for removing, add it to the edit JMenu
		removeMenuItem = new JMenuItem("Remove");
		removeMenuItem.setEnabled(false);
		removeMenuItem.addActionListener(problemControl);
		editMenu.add(removeMenuItem);
		
		
		// Add the JMenuBar to the NORTH section of the BorderLayout
		getContentPane().add(menuBar, BorderLayout.NORTH);
	}
	
	/** Refreshes the listOfProblems variable so it displays 
	 *  an up-to-date list of elements.
	 * 
	 * @author Logan MacGillivray
	 */
	public void newScreen(){
		listOfObjects.setListData(problemControl.getLearnedArray());
	}
	
	/** Public accessor for the lisOfProblems variable
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @return listOfProblems
	 */
	public JList<String> getJList(){
		return listOfObjects;
	}
	
	/** Enable all JMenuItems when a problem (instance of
	 *  MachineLearning) is selected.  Disables JMenuItems
	 *  if a delete function was just used.
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @param val - true if the JMenuItem can be used.  
	 */
	public void enableAll(boolean val){
		removeMenuItem.setEnabled(val);
		editMenuItem.setEnabled(val);
	}
}
