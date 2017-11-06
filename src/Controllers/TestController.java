package Controllers;

import Model.Metrics.CartesianMetric;
import Model.Metrics.GenericMetric;
import Model.Storage;

import javax.swing.*;

public class TestController {
    private String key;
    private Storage storage;

    public TestController(String key, Storage storage) {
        this.key = key;
        this.storage = storage;
    }

    private void addMetric() {
        String[] metricTypes = {"Cartesian Metric", "Enum Metric", "Integer Metric"};
        String input = (String) JOptionPane.showInputDialog(null, "Choose Metric Type:",
            "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null, metricTypes, metricTypes[0]);
        System.out.println(input);
        if (input.equals(metricTypes[0])) {
            addCartesian();
        }
    }

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
            //GenericMetric[] metrics = storage.getLearned().get(key);

        }
    }

    private void addEnumerated() {

    }

    private void addInteger() {

    }

    public static void main(String[] args) {
        TestController tc = new TestController("", new Storage());
        tc.addMetric();
    }
}
