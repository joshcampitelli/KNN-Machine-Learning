package Controller;

import Model.MachineLearning;
import Model.Metrics.CartesianMetric;
import Model.Metrics.EnumMetric;
import Model.Metrics.GenericMetric;
import Model.Metrics.IntegerMetric;
import Model.Storage;

import javax.swing.*;
import java.util.ArrayList;

public class MetricController {
    private String key; //Problem Key in HashMap
    private MachineLearning machineLearning;
    private Storage storage; //HashMap Reference
    //todo: only update gui for now. Once the user clicks save/learn/updateinstance (depending on construct param)
    //todo: is when you update storage and dispose the frame.
    /**
     * todo: may need another parameter specifying the case in which the window is being opened (edit/addnew)
     * todo: which will then enable/disable MenuItems accordingly.
     * @param machineLearning Reference to Model
     */
    public MetricController(MachineLearning machineLearning) {
        key = machineLearning.getProblem();
        this.machineLearning = machineLearning;
        storage = machineLearning.getStorage();
    }

    /**
     * Method will be called by the AlternateWindow Class when constructed, this method will
     * get all the user input for the features supplied by MachineLearning. Will loop through
     * all the supplied features opening their respective JOptionPanes (instanceof).
     */
    public void instantiateFeatures() {
        //for (ArrayList<GenericMetric> features : machineLearning.getProblem()) {

        //}
    }

    /**
     * AddMetric Method should just add locally to the ListModel until the user clicks learn (maybe save modified)
     * which will then use the MachineLearning class' learn method to insert into Storage.
     * @param listModel gui list
     */
    public void addMetric(DefaultListModel<GenericMetric> listModel) {
        String[] metricTypes = {"Cartesian Metric", "Enum Metric", "Integer Metric"};
        String input = (String) JOptionPane.showInputDialog(null, "Choose Metric Type:",
            "Metric Types:", JOptionPane.QUESTION_MESSAGE, null, metricTypes, metricTypes[0]);
        GenericMetric metric = null;
        if (input.equals(metricTypes[0])) {
            metric = cartesianMetricWindow();
        } else if (input.equals(metricTypes[1])) {
            metric = enumMetricWindow();
        } else if (input.equals(metricTypes[2])) {
            metric = integerMetricWindow();
        }
        if (metric != null) {
            listModel.addElement(metric);
        }
    }

    /**
     * editMetric gets the selected metric from the JList and swaps it with the user's updated metric
     * @param list
     * @param listModel
     */
    public void editMetric(JList list, DefaultListModel<GenericMetric> listModel) {
        int index = list.getSelectedIndex();
        GenericMetric metric = listModel.getElementAt(index);
        if (metric instanceof CartesianMetric) {
            metric = cartesianMetricWindow();
        } else if (metric instanceof EnumMetric) {
            metric = enumMetricWindow();
        } else if (metric instanceof IntegerMetric) {
            metric = integerMetricWindow();
        }

        if (metric != null) {
            /*Replace the current metric with updated*/
            listModel.removeElementAt(index);
            listModel.add(index, metric); //Replace in gui
        }
    }

    /**
     * removeMetric removes a metric from the gui's DefaultListModel, this is only for the gui it does
     * not modify the actual storage until the window is closed using learn/update.
     * @param list JList on gui, used to get selected feature
     * @param listModel DefaultListModel on gui, displays the features
     */
    public void removeMetric(JList list, DefaultListModel<GenericMetric> listModel) {
        int index = list.getSelectedIndex();
        listModel.removeElementAt(index); //Remove from gui
    }

    /**
     * cartesianMetricWindow creates a new window which represents an cartesian metric, where the user
     * can enter the value. Creates a new CartesianMetric and returns it.
     * @return CartesianMetric
     */
    private CartesianMetric cartesianMetricWindow() {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();

        Object[] message = {
            "X Coordinate:", xField,
            "Y Coordinate:", yField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Cartesian Metric", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new CartesianMetric(Integer.valueOf(xField.getText()), Integer.valueOf(yField.getText()));
        }
        return null;
    }

    /**
     * enumMetricWindow creates a new window which represents an enum metric, where the user
     * can enter the value. Creates a new EnumMetric and returns it.
     * @return EnumMetric
     */
    private EnumMetric enumMetricWindow() {
        JTextField enumField = new JTextField();
        Object[] message = {
            "Enumerated Value:", enumField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Enum Metric", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new EnumMetric(enumField.getText());
        }
        return null;
    }

    /**
     * integerMetricWindow creates a new window which represents an integer metric, where the user
     * can enter the value. Creates a new IntegerMetric and returns it.
     * @return IntegerMetric
     */
    private IntegerMetric integerMetricWindow() {
        JTextField intField = new JTextField();
        Object[] message = {
            "Integer Value:", intField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Integer Metric", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new IntegerMetric(Integer.valueOf(intField.getText()));
        }
        return null;
    }

    /**
     * learnInstance calls MachineLearning learn method with an ArrayList that represents
     * the instance's features.
     * @param listModel guiList which stores all the new features.
     */
    public void learnInstance(DefaultListModel<GenericMetric> listModel) {
        ArrayList<GenericMetric> newInstance = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i ++) {
            newInstance.add(listModel.get(i));
        }
        machineLearning.learn(key, newInstance);
    }

    /**
     * updateInstance updates an instance stored in storage with the new instance which was
     * modified by the user.
     * @param listModel guiList which stores all the new features.
     */
    public void updateInstance(DefaultListModel<GenericMetric> listModel) {
        storage.remove(key);
        learnInstance(listModel);
    }
}
