package Views;
import java.awt.*;

import javax.swing.*;
public class MainWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	
	public MainWindow(){
		super("Machine Learning",/* Layout */);
		
		this.setSize((int) screen.getWidth()/2,(int) screen.getHeight()/2);
		this.setLocation((int) screen.getWidth()/4,(int) screen.getHeight()/4);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUpMenu();
		this.setVisible(true);
	}
	
	private void setUpMenu(){
		JMenuBar mainMenu = new JMenuBar();
		this.setJMenuBar(mainMenu);
		
		JMenu fileMenu = new JMenu("File");
		mainMenu.add(fileMenu);
		
		JMenuItem myItems = new JMenuItem("Add");
		fileMenu.add(myItems);
	}
}
