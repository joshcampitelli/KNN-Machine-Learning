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
	private JMenuItem editMenuItem = new JMenuItem("Edit");
	private JMenuItem addMenuItem = new JMenuItem("Add");
	
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
	
	private void createMenu(){
		// Create JMenuBar with a JMenu called File
	
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		JMenuItem addProblemMenuItem = new JMenuItem("Add Problem");
		addProblemMenuItem.addActionListener(mainControl);
		fileMenu.add(addProblemMenuItem);
		
		// Add an Edit JMenuItem to the Edit JMenu
		
		addMenuItem.setEnabled(false);
		addMenuItem.addActionListener(mainControl);
		editMenu.add(addMenuItem);
		editMenuItem.setEnabled(false);
		editMenuItem.addActionListener(mainControl);
		editMenu.add(editMenuItem);
		
		// Add the JMenuBar to the NORTH section of the BorderLayout
		getContentPane().add(menuBar, BorderLayout.NORTH);
	}
	
	public void newScreen(){
		listOfProblems.setListData(mainControl.getProblemsArray());
	}
	
	public JList<String> getJList(){
		return listOfProblems;
	}
	
	public void enableAll(boolean val){
		addMenuItem.setEnabled(val);
		editMenuItem.setEnabled(val);
	}
	
}
