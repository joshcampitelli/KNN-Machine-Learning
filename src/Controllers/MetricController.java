package Controllers;

import Model.MachineLearning;
import Model.Metrics.CartesianMetric;
import Model.Metrics.EnumMetric;
import Model.Metrics.GenericMetric;
import Model.Metrics.IntegerMetric;
import Model.Storage;

import javax.swing.*;

public class MetricController {
    private String key;
    private MachineLearning machineLearning;
    private Storage storage;

    public MetricController(String key, MachineLearning machineLearning) {
        this.key = key;
        this.machineLearning = machineLearning;
        //storage = machineLearning.getStorage(); //Method undefined right now.
    }

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
            //storage.addMetric(key, metric);
            listModel.addElement(metric);
        }
    }

    /*edit Metric gets the selected metric from the JList and swaps it with the user's updated metric*/
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
            //storage.replaceMetric(key, index, metric);
            listModel.removeElementAt(index);
            listModel.add(index, metric);
        }
    }

    public void removeMetric(JList list, DefaultListModel<GenericMetric> listModel) {
        int index = list.getSelectedIndex();
        listModel.removeElementAt(index);
        //storage.removeMetric(key, index);
    }

    /*Allows the user to add a Cartesian Metric*/
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
}
