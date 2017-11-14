package Views;

import Controller.FeatureController;
import Model.Features.GenericFeature;

import javax.swing.*;
import java.awt.*;

public class AlternateWindow extends JFrame {
    private FeatureController featureController;

    /*GUI Menu Attributes*/
    private JMenuBar menuBar = new JMenuBar();
    private JMenu machineLearnMenu = new JMenu("Machine Learning");
    private JMenuItem learnItem = new JMenuItem("Learn Instance");
    private JMenuItem updateItem = new JMenuItem("Update Instance");
    private JMenuItem predictPriceItem = new JMenuItem("Predict Price");
    private JMenuItem predictErrorItem = new JMenuItem("Predict Error");


    private JMenuItem cancelItem = new JMenuItem("Cancel");

    private JMenu metricsMenu = new JMenu("Metrics");
    private JMenuItem editItem = new JMenuItem("Edit Metric");

    /*GUI List of Metrics*/
    private JList<GenericFeature> list;
    private DefaultListModel<GenericFeature> listModel;
    private JScrollPane scrollPane;

    public AlternateWindow(FeatureController featureController) {
        super("Problem Aspects");
        this.featureController = featureController;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(new Dimension(300, 350));
        setResizable(false);

        setLayout(new BorderLayout(10, 10));

        machineLearnMenu.add(learnItem);
        machineLearnMenu.add(updateItem);
        machineLearnMenu.add(predictPriceItem);
        machineLearnMenu.add(predictErrorItem);
        machineLearnMenu.add(cancelItem);

        metricsMenu.add(editItem);

        /*Disable until user adds feature*/
        learnItem.setEnabled(false);
        updateItem.setEnabled(false);
        editItem.setEnabled(false);

        if(!featureController.priceExists())
            predictErrorItem.setEnabled(false);

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

        if (featureController.getState() == FeatureController.State.addProblem) {
            learnItem.setEnabled(true);
            editItem.setEnabled(true);
        } else {
            updateItem.setEnabled(true);
            editItem.setEnabled(true);
        }

        //Might Work Not Tested
        featureController.initialize(listModel);
    }

    private void addListeners() {
        /*Action Listener for MenuItems*/
        learnItem.addActionListener(event -> {
            featureController.learnInstance(listModel);
            this.dispose();
        });

        updateItem.addActionListener(event -> {
            featureController.updateInstance(listModel);
            this.dispose();
        });

        predictPriceItem.addActionListener(event -> featureController.predictPrice(listModel));
        predictErrorItem.addActionListener(event -> featureController.predictError(listModel));
        cancelItem.addActionListener(event -> this.dispose());
        editItem.addActionListener(event -> featureController.editFeature(list, listModel));
    }
}
