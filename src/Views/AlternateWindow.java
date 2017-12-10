package Views;

import Controller.FeatureController;
import Model.Features.GenericFeature;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the Features for any given instance of a problem.
 * @author Josh Campitelli
 */
public class AlternateWindow extends JFrame {
    private FeatureController featureController;

    /*GUI Menu Attributes*/
    private JMenuBar menuBar = new JMenuBar();
    private JMenu machineLearnMenu = new JMenu("Machine Learning");
    private JMenuItem learnItem = new JMenuItem("Learn Instance");
    private JMenuItem updateItem = new JMenuItem("Update Instance");

    private JMenuItem predictItem = new JMenuItem("Predict Value");
    private JMenuItem predictErrorItem = new JMenuItem("Predict Error");
    private JMenuItem predictLearnItem = new JMenuItem("Predict & Learn");

    private JMenuItem cancelItem = new JMenuItem("Cancel");

    private JMenu featureMenu = new JMenu("Features");
    private JMenuItem editItem = new JMenuItem("Edit Feature");

    /*GUI List of Features*/
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
        machineLearnMenu.add(predictItem);
        machineLearnMenu.add(predictErrorItem);
        machineLearnMenu.add(predictLearnItem);
        machineLearnMenu.add(cancelItem);

        featureMenu.add(editItem);

        /*Disable until user adds feature*/
        learnItem.setEnabled(false);
        updateItem.setEnabled(false);
        editItem.setEnabled(false);

        if(!featureController.predictableExists())
            predictErrorItem.setEnabled(false);

        menuBar.add(machineLearnMenu);
        menuBar.add(featureMenu);
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

    /**
     * Adds the Action listeners to the menu items on the gui.
     * @author Josh Campitelli
     */
    private void addListeners() {
        /*Action Listener for MenuItems*/
        learnItem.addActionListener(event -> {
            featureController.learnInstance(listModel);
            featureController.getPWC().getWindow().newScreen();
            this.dispose();
        });

        updateItem.addActionListener(event -> {
            featureController.updateInstance(listModel);
            this.dispose();
        });

        predictItem.addActionListener(event -> featureController.predictPrice(listModel));
        predictErrorItem.addActionListener(event -> featureController.predictError(listModel));
        predictLearnItem.addActionListener(event -> featureController.predictLearn(listModel));
        cancelItem.addActionListener(event -> this.dispose());
        editItem.addActionListener(event -> featureController.editFeature(list, listModel));
    }
}

