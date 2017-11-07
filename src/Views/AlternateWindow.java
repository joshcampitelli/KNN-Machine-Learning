package Views;

import Controllers.MetricController;
import Model.Metrics.GenericMetric;
import Model.Storage;

import javax.swing.*;
import java.awt.*;

public class AlternateWindow extends JFrame {
    MetricController metricController;

    /*GUI Menu Attributes*/
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMI = new JMenu("File");
    private JMenuItem saveExitItem = new JMenuItem("Save & Exit");
    private JMenuItem cancelItem = new JMenuItem("Cancel");

    private JMenu metricsMI = new JMenu("Metrics");
    private JMenuItem addItem = new JMenuItem("Add Metric");
    private JMenuItem editItem = new JMenuItem("Edit Metric");
    private JMenuItem removeItem = new JMenuItem("Remove Metric");

    //todo: create metric MI then in window select which type.
    /*GUI List of Metrics*/
    private JList<GenericMetric> list;
    private DefaultListModel<GenericMetric> listModel;
    private JScrollPane scrollPane;

    public AlternateWindow(String key, Storage storage) {
        super("Problem Aspects");
        metricController = new MetricController(key, storage);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(new Dimension(300, 350));
        setResizable(false);

        setLayout(new BorderLayout(10, 10));

        fileMI.add(saveExitItem);
        fileMI.add(cancelItem);

        metricsMI.add(addItem);
        metricsMI.add(editItem);
        metricsMI.add(removeItem);

        menuBar.add(fileMI);
        menuBar.add(metricsMI);
        setJMenuBar(menuBar);

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);

        scrollPane = new JScrollPane(list);
        add(scrollPane);

        addListeners();
    }

    private void addListeners() {
        /*Action Listener fo Add Metric MI*/
        addItem.addActionListener(event -> metricController.addMetric(listModel));

        editItem.addActionListener(event -> metricController.editMetric(list, listModel));
    }

    public static void main(String[] args) {
        AlternateWindow alternateWindow = new AlternateWindow("", new Storage());
        alternateWindow.setVisible(true);
    }

}
