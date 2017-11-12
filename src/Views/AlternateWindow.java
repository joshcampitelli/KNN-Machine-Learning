package Views;

import Controller.MetricController;
import Model.Metrics.GenericMetric;

import javax.swing.*;
import java.awt.*;

public class AlternateWindow extends JFrame {
    private MetricController metricController;

    /*GUI Menu Attributes*/
    private JMenuBar menuBar = new JMenuBar();
    private JMenu machineLearnMenu = new JMenu("Machine Learning");
    private JMenuItem learnItem = new JMenu("Learn Instance");
    private JMenuItem updateItem = new JMenu("Update Instance");
    private JMenuItem cancelItem = new JMenuItem("Cancel");

    private JMenu metricsMenu = new JMenu("Metrics");
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

        machineLearnMenu.add(learnItem);
        machineLearnMenu.add(updateItem);
        machineLearnMenu.add(cancelItem);

        metricsMenu.add(addItem);
        metricsMenu.add(editItem);
        metricsMenu.add(removeItem);

        /*Disable until user adds feature*/
        learnItem.setEnabled(false);
        updateItem.setEnabled(false);
        editItem.setEnabled(false);
        removeItem.setEnabled(false);

        menuBar.add(machineLearnMenu);
        menuBar.add(metricsMenu);
        setJMenuBar(menuBar);

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);

        scrollPane = new JScrollPane(list);
        add(scrollPane);

        addListeners();
        setVisible(true);
    }

    private void addListeners() {
        /*Action Listener for MenuItems*/
        learnItem.addActionListener(event -> metricController.learnInstance(listModel));
        updateItem.addActionListener(event -> metricController.updateInstance(listModel));
        cancelItem.addActionListener(event -> this.dispose());
        addItem.addActionListener(event -> {
            metricController.addMetric(listModel);
            if (listModel.size() > 0) {
                learnItem.setEnabled(true);
                updateItem.setEnabled(true);
                editItem.setEnabled(true);
                removeItem.setEnabled(true);
            }
        });

        editItem.addActionListener(event -> metricController.editMetric(list, listModel));
        removeItem.addActionListener(event -> {
            metricController.removeMetric(list, listModel);
            if (listModel.size() <= 0) {
                learnItem.setEnabled(false);
                updateItem.setEnabled(false);
                editItem.setEnabled(false);
                removeItem.setEnabled(false);
            }
        });
    }
}
