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

public class MainWindow extends JFrame{
	private MainWindowController mainControl = new MainWindowController(this);
	private JList<String> listOfProblems;
	private JMenuItem viewMenuItem = new JMenuItem("View");
	private JMenuItem saveMenuItem = new JMenuItem("Save");
	
	/** Constructor
	 * 
	 * @author Logan MacGillivray
	 */
	public MainWindow(){
		super("Machine Learning");
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dimension.width/2, dimension.height/2);
		setLocation(dimension.width/2 - this.getWidth()/2, dimension.height/2 - this.getHeight()/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		createMenu();
		
		listOfProblems = new JList<String>(mainControl.getProblems());
		listOfProblems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOfProblems.addListSelectionListener(mainControl);
		add(listOfProblems, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	/** Creates JMenu for the JFrame
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
		
		// Add an Edit JMenuItem to the Edit JMenu
		saveMenuItem.setEnabled(false);
		saveMenuItem.addActionListener(mainControl);
		fileMenu.add(saveMenuItem);
		
		// Add an Edit JMenuItem to the Edit JMenu
		JMenuItem loadMenuItem = new JMenuItem("Load");
		loadMenuItem.addActionListener(mainControl);
		fileMenu.add(loadMenuItem);
				
		JMenuItem addProblemMenuItem = new JMenuItem("Add Problem");
		addProblemMenuItem.addActionListener(mainControl);
		fileMenu.add(addProblemMenuItem);
		
		// Add an Edit JMenuItem to the Edit JMenu
		viewMenuItem.setEnabled(false);
		viewMenuItem.addActionListener(mainControl);
		editMenu.add(viewMenuItem);
		
		// Add the JMenuBar to the NORTH section of the BorderLayout
		getContentPane().add(menuBar, BorderLayout.NORTH);
	}
	
	/** Refreshes the JFrame
	 * 
	 * @author Logan MacGillivray
	 */
	public void newScreen(){
		listOfProblems.setListData(mainControl.getProblemsArray());
	}
	
	/** Returns JList for getting selected value
	 * 
	 * @author Logan MacGillivray
	 */
	public JList<String> getJList(){
		return listOfProblems;
	}
	
	/** Sets disabled options to enabled
	 * 
	 * @author Logan MacGillivray
	 */
	public void enableAll(boolean val){
		viewMenuItem.setEnabled(val);
		saveMenuItem.setEnabled(val);
	}
}
