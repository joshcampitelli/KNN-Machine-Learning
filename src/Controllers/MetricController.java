package Controllers;

import Model.Metrics.CartesianMetric;
import Model.Metrics.EnumMetric;
import Model.Metrics.GenericMetric;
import Model.Metrics.IntegerMetric;
import Model.Storage;

import javax.swing.*;
import java.util.ArrayList;

public class MetricController {
    private String key;
    private Storage storage;

    public MetricController(String key, Storage storage) {
        this.key = key;
        this.storage = storage;
    }

    private void selectMetric() {
        String[] metricTypes = {"Cartesian Metric", "Enum Metric", "Integer Metric"};
        String input = (String) JOptionPane.showInputDialog(null, "Choose Metric Type:",
            "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null, metricTypes, metricTypes[0]);
        System.out.println(input);
        if (input.equals(metricTypes[0])) {
            addCartesian();
        } else if (input.equals(metricTypes[1])) {
            addEnumerated();
        } else if (input.equals(metricTypes[2])) {
            addInteger();
        }
    }

    /*Allows the user to add a Cartesian Metric*/
    private void addCartesian() {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();

        Object[] message = {
            "X Coordinate:", xField,
            "Y Coordinate:", yField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Cartesian Metric", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            CartesianMetric cartesianMetric = new CartesianMetric(Integer.valueOf(xField.getText()), Integer.valueOf(yField.getText()));
            ArrayList<GenericMetric> list = storage.getLearned().get(key);
            list.add(cartesianMetric);
        }
    }

    private void addEnumerated() {
        JTextField enumField = new JTextField();
        Object[] message = {
            "Enumerated Value:", enumField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Enum Metric", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            EnumMetric enumMetric = new EnumMetric(enumField.getText());
            ArrayList<GenericMetric> list = storage.getLearned().get(key);
            list.add(enumMetric);
        }
    }

    private void addInteger() {
        JTextField intField = new JTextField();
        Object[] message = {
            "Integer Value:", intField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Integer Metric", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            IntegerMetric integerMetric = new IntegerMetric(Integer.valueOf(intField.getText()));
            ArrayList<GenericMetric> list = storage.getLearned().get(key);
            list.add(integerMetric);
        }
    }

    public static void main(String[] args) {
        MetricController tc = new MetricController("", new Storage());
        tc.selectMetric();
    }
}
