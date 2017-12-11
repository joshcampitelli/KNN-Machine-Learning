package Controller;

import Model.Features.*;
import Model.MachineLearning;

import Model.Metrics.*;
import Model.Storage;

import javax.swing.*;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;

public class FeatureController {
    private String key;         //Problem Key in HashMap
    private MachineLearning machineLearning;
    private Storage storage;    //HashMap Reference
    private State state;        //Indicates edit or add
    private ProblemWindowController control;
    private GenericMetric predictMetric = null;

    /*
     * Represents the state in which the program was opened:
     * editProblem State - Gets all current attributes from the Storage and allows user to modify.
     * addProblem State - Prompts the user for new values for attributes
     */
    public enum State {
        editProblem,
        addProblem
    }

    public FeatureController(ProblemWindowController pwc, String state, String key) {
        this.control = pwc;
        this.machineLearning = pwc.getMachine();
        this.key = key;
        this.storage = machineLearning.getStorage();
        if (state.toLowerCase().equals("add")) {
            this.state = State.addProblem;
        } else {
            this.state = State.editProblem;
        }
    }

    /**
     * Initializes the gui according to the state of the program, gets data from storage if edit.
     * @param listModel reference to gui list
     * @author Josh Campitelli
     */
    public void initialize(DefaultListModel<GenericFeature> listModel) {
        if (state == State.addProblem) {
            instantiateFeatures(listModel);
        } else {
            ArrayList<GenericFeature> features = storage.getLearned().get(key);
            if (features != null) {
                for (GenericFeature feature : features) {
                    listModel.addElement(feature);
                }
            }
        }
    }

    /**
     * gets the current state of the program.
     * @return State enum
     * @author Josh Campitelli
     */
    public State getState() {
        return state;
    }

    /**
     * instantiateFeatures method called upon constructing the AlternateWindow it's purpose is to
     * get user input for 'problems' to be learned with MachineLearning. As the user enters each
     * feature they are added to the DefaultListModel, upon completion they're 'learned' by
     * MachineLearning
     * @param listModel gui list to display features.
     * @author Josh Campitelli
     */
    private void instantiateFeatures(DefaultListModel<GenericFeature> listModel) {
        ArrayList<GenericMetric> metrics = machineLearning.getMetrics();
        GenericFeature feature = null;

        //Loop through the required features & their metrics and get user input.
        for (GenericMetric metric : metrics) {
            //Get the predictable metric type.
            if (metric.isPredictable()) {
                predictMetric = metric;
                continue;
            }

            feature = getFeature(metric);

            //Add to gui feature to gui JList
            if (feature != null)
                listModel.addElement(feature);
        }
    }


    private GenericFeature complexPolarFeature(GenericMetric metric) {
        GenericFeature subFeature1 = integerFeatureWindow(metric, "Degree"); //open different window with "angle" name
        GenericFeature subFeature2 = doubleFeatureWindow(metric, "Distance");
        ArrayList<GenericFeature> subFeatures = new ArrayList<>();
        subFeatures.add(subFeature1);
        subFeatures.add(subFeature2);
        return new ComplexFeature(metric.getName(), subFeatures, metric);
    }

    /**
     * editFeature gets the selected feature from the JList and swaps it with the user's updated feature
     * @param list JList on gui
     * @param listModel DefaultListModel on gui
     * @author Josh Campitelli
     */
    public void editFeature(JList list, DefaultListModel<GenericFeature> listModel) {
        int index = list.getSelectedIndex();
        GenericFeature feature = listModel.getElementAt(index);

        GenericMetric metric = feature.getMetric();
        feature = getFeature(metric);

        if (feature != null) {
            /*Replace the current feature with updated*/
            listModel.removeElementAt(index);
            listModel.add(index, feature);
        }
    }

    /**
     * cartesianFeatureWindow creates a new window which represents an cartesian feature, where the user
     * can enter the value. Creates a new CartesianFeature and returns it.
     * @return CartesianFeature
     * @author Josh Campitelli
     */
    private CartesianFeature cartesianFeatureWindow(GenericMetric metric) {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();

        Object[] message = {
                "X Coordinate:", xField,
                "Y Coordinate:", yField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, metric.getName() + " (Cartesian):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int x, y;
            try {
                x = Integer.valueOf(xField.getText());
                y = Integer.valueOf(yField.getText());
            } catch (NumberFormatException e) {
                //If user leaves blank assume 0.
                if (xField.getText().equals("") || yField.getText().equals("")) {
                    return new CartesianFeature(metric.getName(), 0, 0, metric);
                }

                JOptionPane.showMessageDialog(null, "Incorrect coordinate.");
                return cartesianFeatureWindow(metric); //Testing recursion here.
            }
            return new CartesianFeature(metric.getName(), x, y, metric);
        } else {
            return cartesianFeatureWindow(metric);
        }
    }

    /**
     * enumFeatureWindow creates a new window which represents an enum feature, where the user
     * can enter the value. Creates a new EnumFeature and returns it.
     * @return EnumFeature
     * @author Josh Campitelli
     */
    private EnumFeature enumFeatureWindow(GenericMetric metric) {
        JTextField enumField = new JTextField();
        HashMap<String, Integer> permittedValues = metric.getPermittedValues();

        StringBuffer sb = new StringBuffer();

        for (String value : permittedValues.keySet()) {
            sb.append("\t -" + value + "\n");
        }

        Object[] message = {
                "Options: \n" + sb.toString(),
                "Discrete Value:", enumField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, metric.getName() + " (Discrete):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (metric.getPermittedValues().containsKey(enumField.getText())) {
                return new EnumFeature(metric.getName(), enumField.getText(), metric);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect discrete value.");
                return enumFeatureWindow(metric);
            }
        } else {
            return enumFeatureWindow(metric);
        }
    }

    /**
     * integerFeatureWindow creates a new window which represents an integer feature, where the user
     * can enter the value. Creates a new IntegerFeature and returns it.
     * @return IntegerFeature
     * @author Josh Campitelli
     */
    private IntegerFeature integerFeatureWindow(GenericMetric metric, String text) {
        JTextField intField = new JTextField();
        Object[] message = {
                text + " (Integer) Value:", intField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, metric.getName() + " (Integer):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int value;
            try {
                value = Integer.valueOf(intField.getText());
            } catch (NumberFormatException e) {
                //If user leaves blank assume 0.
                if (intField.getText().equals("")) {
                    return new IntegerFeature(metric.getName(), 0, metric);
                }
                JOptionPane.showMessageDialog(null, "Incorrect value.");
                return integerFeatureWindow(metric, text); //Testing recursion here.
            }

            return new IntegerFeature(metric.getName(), value, metric);
        } else {
            return integerFeatureWindow(metric, text);
        }
    }

    /**
     * doubleFeatureWindow creates a new window which represents a double feature, where the user
     * can enter the value. Creates a new DoubleFeature and returns it.
     * @return DoubleFeature
     * @author Josh Campitelli
     */
    private DoubleFeature doubleFeatureWindow(GenericMetric metric, String text) {
        JTextField doubleField = new JTextField();
        Object[] message = {
                text + " (Double) Value:", doubleField
        };
        int option = JOptionPane.showConfirmDialog(null, message, metric.getName() + " (Double):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            double value;
            try {
                value = Double.valueOf(doubleField.getText());
            } catch (NumberFormatException e) {
                //If user leaves blank assume 0.
                if (doubleField.getText().equals("")) {
                    return new DoubleFeature(metric.getName(), 0.0, metric);
                }
                JOptionPane.showMessageDialog(null, "Incorrect value.");
                return doubleFeatureWindow(metric, text); //Testing recursion here.
            }
            return new DoubleFeature(metric.getName(), value, metric);
        } else {
            return doubleFeatureWindow(metric, text);
        }
    }

    /**
     * learnInstance calls MachineLearning learn method with an ArrayList that represents
     * the instance's features.
     * @param listModel guiList which stores all the new features.
     * @author Josh Campitelli
     */
    public void learnInstance(DefaultListModel<GenericFeature> listModel) {
        ArrayList<GenericFeature> newInstance = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i ++) {
            newInstance.add(listModel.get(i));
        }
        GenericFeature predictFeature= null;

        if (predictMetric != null) {
            predictFeature = getFeature(predictMetric);
        }

        if(predictFeature != null) {
        	newInstance.add(predictFeature);
        	machineLearning.learn(key, newInstance);
        }
    }

    /**
     * Method getFeature checks instance of given metric, prompts the user for the feature's value
     * constructs then returns the feature.
     * @param metric Metric which infers feature
     * @return GenericFeature
     */
    private GenericFeature getFeature(GenericMetric metric) {
        if (metric instanceof CartesianEuclideanMetric) {
            return cartesianFeatureWindow(metric);
        } else if (metric instanceof IntegerAbsoluteMetric) {
            return integerFeatureWindow(metric, "");
        } else if (metric instanceof DiscreteBinaryMetric) {
            return enumFeatureWindow(metric);
        } else if (metric instanceof DoubleAbsoluteMetric) {
            return doubleFeatureWindow(metric, "");
        } else if (metric instanceof PolarMetric) {
            return complexPolarFeature(metric);
        } else {
            return null;
        }
    }

    /**
     * updateInstance updates an instance stored in storage with the new instance which was
     * modified by the user.
     * @param listModel guiList which stores all the new features.
     * @author Josh Campitelli
     */
    public void updateInstance(DefaultListModel<GenericFeature> listModel) {
        storage.remove(key);
        ArrayList<GenericFeature> newInstance = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i ++) {
            newInstance.add(listModel.get(i));
        }
        machineLearning.update(key, newInstance);
    }

    /**
     * predictable determines whether or not a instance has a predictable feature
     * @return boolean
     * @author Josh Campitelli
     */
    public boolean predictableExists() {
        for (GenericMetric metric : machineLearning.getMetrics()) {
            if (metric.isPredictable()) {
                return true;
            }
        }
        return false;
    }

    /**
     * predictPrice gets the k value from the user and calls the predict method within MachineLearning.
     * @param listModel list on gui to display features
     * @author Josh Campitelli
     */
    public void predictPrice(DefaultListModel<GenericFeature> listModel) {
        String predictedValue;
        ArrayList<GenericFeature> newInstance = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i ++) {
            newInstance.add(listModel.get(i));
        }

        JTextField intField = new JTextField();
        Object[] message = {
                "Value for K:", intField,
        };

        int option = JOptionPane.showConfirmDialog(null, message, "KNN Algorithm", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int k = getInteger(intField.getText());
            if (k > 0) {
                predictedValue = machineLearning.predict(k, newInstance);
                JOptionPane.showMessageDialog(null, "Predicted Value is: " + predictedValue);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect value for K.");
                predictPrice(listModel);
            }
        } else {
            predictPrice(listModel);
        }
    }

    /**
     * todo: implement Predict and Learn method.
     * @param listModel
     */
    public void predictLearn(DefaultListModel<GenericFeature> listModel) {
    }

    /**
     * predictPrice gets the k value from the user and calls the predictError method within MachineLearning
     * @param listModel list on gui to display features
     * @author Josh Campitelli
     */
    public void predictError(DefaultListModel<GenericFeature> listModel) {
        int predictError;
        ArrayList<GenericFeature> newInstance = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i ++) {
            newInstance.add(listModel.get(i));
        }

        JTextField intField = new JTextField();
        Object[] message = {
                "Value for K:", intField,
        };

        int option = JOptionPane.showConfirmDialog(null, message, "KNN Algorithm", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int k = getInteger(intField.getText());
            if (k > 0) {
                predictError = machineLearning.predictError(Integer.valueOf(intField.getText()), newInstance);
                JOptionPane.showMessageDialog(null, "Predicted Error is: " + predictError);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect value for K.");
                predictError(listModel);
            }
        } else {
            predictError(listModel);
        }
    }

    /**
     * Error Handling for gui components which require integer.
     * @return integer value,
     */
    private int getInteger(String text){
        try {
            return Integer.valueOf(text);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public ProblemWindowController getPWC(){
        return control;
    }
}
