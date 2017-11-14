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
import Controller.ProblemWindowController;

public class ProblemWindow extends JFrame {
	private ProblemWindowController problemControl;
	private JList<String> listOfObjects;
	private JMenuItem editMenuItem;
	private JMenuItem removeMenuItem;
	private JMenuItem predictMenuItem;
	
	public ProblemWindow(ProblemWindowController control){
		super(control.toString());
		problemControl = control;
		problemControl.setFrame(this);
		
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(dimension.width/2, dimension.height/2);
		setLocation(dimension.width/2 - this.getWidth()/2, dimension.height/2 - this.getHeight()/2);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		createMenu();
		
		listOfObjects = new JList<String>(problemControl.getProblems());
		listOfObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOfObjects.addListSelectionListener(problemControl);
		add(listOfObjects, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	private void createMenu(){
		// Create JMenuBar with a JMenu called File
	
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenu editMenu = new JMenu("Edit");
		menuBar.add(editMenu);
		
		JMenuItem addMenuItem = new JMenuItem("Learn Example");
		addMenuItem.addActionListener(problemControl);
		fileMenu.add(addMenuItem);
		
		// Add an Edit JMenuItem to the Edit JMenu
		editMenuItem = new JMenuItem("Edit");
		editMenuItem.setEnabled(false);
		editMenuItem.addActionListener(problemControl);
		editMenu.add(editMenuItem);
		removeMenuItem = new JMenuItem("Remove");
		removeMenuItem.setEnabled(false);
		removeMenuItem.addActionListener(problemControl);
		editMenu.add(removeMenuItem);
		predictMenuItem = new JMenuItem("Predict Price");
		predictMenuItem.setEnabled(false);
		predictMenuItem.addActionListener(problemControl);
		editMenu.add(predictMenuItem);
		
		// Add the JMenuBar to the NORTH section of the BorderLayout
		getContentPane().add(menuBar, BorderLayout.NORTH);
	}
	
	public void newScreen(){
		listOfObjects.setListData(problemControl.getProblemsArray());
		this.repaint();
	}
	
	public JList<String> getJList(){
		return listOfObjects;
	}
	
	public void enableAll(boolean val){
		removeMenuItem.setEnabled(val);
		editMenuItem.setEnabled(val);
	}
	
}
