package Views;

import Controllers.MetricController;
import Model.Metrics.GenericMetric;

import javax.swing.*;
import java.awt.*;

public class AlternateWindow extends JFrame {
    private MetricController metricController;

    /*GUI Menu Attributes*/
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMI = new JMenu("File");
    private JMenuItem exitItem = new JMenuItem("Exit");

    private JMenu metricsMI = new JMenu("Metrics");
    private JMenuItem addItem = new JMenuItem("Add Metric");
    private JMenuItem editItem = new JMenuItem("Edit Metric");
    private JMenuItem removeItem = new JMenuItem("Remove Metric");

    /*GUI List of Metrics*/
    private JList<GenericMetric> list;
    private DefaultListModel<GenericMetric> listModel;
    private JScrollPane scrollPane;

    public AlternateWindow(MetricController metricController) {
        super("Problem Aspects");
        this.metricController = metricController;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(new Dimension(300, 350));
        setResizable(false);

        setLayout(new BorderLayout(10, 10));

        fileMI.add(exitItem);
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
        /*Action Listener for MenuItems*/
        exitItem.addActionListener(event -> this.dispose());
        addItem.addActionListener(event -> metricController.addMetric(listModel));
        editItem.addActionListener(event -> metricController.editMetric(list, listModel));
        removeItem.addActionListener(event -> metricController.removeMetric(list, listModel));
    }
}
