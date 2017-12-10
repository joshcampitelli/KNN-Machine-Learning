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

import Controller.MainWindowController;

@SuppressWarnings("serial")
public class MainWindow extends JFrame{
	private MainWindowController mainControl;		// The main controller for the JFrame
	private JList<String> listOfProblems;			// List of MachineLearning instances, or types of problems
	private JMenuItem saveMenuItem, viewMenuItem;	// JMenuItems that need to be accessible by mainControl
	
	/** Construct a new JFrame to be used for the MainWindow
	 * 
	 * @author Logan MacGillivray
	 */
	public MainWindow(){
		super("Machine Learning");
		mainControl = new MainWindowController(this);
				
		// Set the window to be in the middle of the screen
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dimension.width/2, dimension.height/2);
		setLocation(dimension.width/2 - this.getWidth()/2, dimension.height/2 - this.getHeight()/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		createMenu();
		
		// Add a list of problems to the JFrame
		listOfProblems = new JList<String>(mainControl.getProblems());
		listOfProblems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOfProblems.addListSelectionListener(mainControl);
		add(listOfProblems, BorderLayout.CENTER);
		
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
		
		// Add an Save JMenuItem to the File JMenu
		// Save can only be used if a problem is selected
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setEnabled(false);
		saveMenuItem.addActionListener(mainControl);
		fileMenu.add(saveMenuItem);
		
		// Add an Load JMenuItem to the File JMenu
		JMenuItem loadMenuItem = new JMenuItem("Load");
		loadMenuItem.addActionListener(mainControl);
		fileMenu.add(loadMenuItem);
		
		// Add an Add Problem JMenuItem to the File JMenu
		JMenuItem addProblemMenuItem = new JMenuItem("Add Problem");
		addProblemMenuItem.addActionListener(mainControl);
		fileMenu.add(addProblemMenuItem);
		
		// Add an View JMenuItem to the Edit JMenu
		// View can only be used if a problem is selected
		viewMenuItem = new JMenuItem("View");
		viewMenuItem.setEnabled(false);
		viewMenuItem.addActionListener(mainControl);
		editMenu.add(viewMenuItem);
		
		// Add the JMenuBar to the NORTH section of the BorderLayout
		getContentPane().add(menuBar, BorderLayout.NORTH);
	}
	
	/** Refreshes the listOfProblems variable so it displays 
	 *  an up-to-date list of elements.
	 * 
	 * @author Logan MacGillivray
	 */
	public void newScreen(){
		listOfProblems.setListData(mainControl.getProblemsArray());
	}
	
	/** Public accessor for the lisOfProblems variable
	 * 
	 * @author Logan MacGillivray
	 * 
	 * @return listOfProblems
	 */
	public JList<String> getJList(){
		return listOfProblems;
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
		viewMenuItem.setEnabled(val);
		saveMenuItem.setEnabled(val);
	}
}
