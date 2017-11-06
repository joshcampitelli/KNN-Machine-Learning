package Views;

import javax.swing.*;
import java.awt.*;

public class AlternateWindow extends JFrame {
    /*GUI Menu Attributes*/
    private JMenuBar menuBar = new JMenuBar();

    private JMenu fileMI = new JMenu("File");
    private JMenuItem saveExitItem = new JMenuItem("Save & Exit");
    private JMenuItem exitItem = new JMenuItem("Exit");

    private JMenu metricsMI = new JMenu("Model/Metrics");
    //Possible Design for adding metrics.
    private JMenuItem addIntItem = new JMenuItem("Add Integer");
    private JMenuItem addCartItem = new JMenuItem("Add Cartesian");
    private JMenuItem addEnumItem = new JMenuItem("Add Enumerated Type");

    public AlternateWindow() {
        super("Problem Aspects");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(300, 350));
        setResizable(false);

        setLayout(new BorderLayout(10, 10));

        fileMI.add(saveExitItem);
        fileMI.add(exitItem);

        metricsMI.add(addIntItem);
        metricsMI.add(addCartItem);
        metricsMI.add(addEnumItem);

        menuBar.add(fileMI);
        menuBar.add(metricsMI);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        AlternateWindow alternateWindow = new AlternateWindow();
        alternateWindow.setVisible(true);
    }

}
