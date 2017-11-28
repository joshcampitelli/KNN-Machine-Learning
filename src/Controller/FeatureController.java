package Controller;

import Model.Features.*;
import Model.MachineLearning;

import Model.Metrics.GenericMetric;
import Model.Storage;

import javax.swing.*;
import java.util.ArrayList;

public class FeatureController {
    private String key; //Problem Key in HashMap
    private MachineLearning machineLearning;
    private Storage storage; //HashMap Reference
    private State state; //Indicates edit or add
    private ProblemWindowController control;
    private MachineLearning.FeatureTypes predictFeature;

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
            ArrayList<GenericFeature> features = machineLearning.getStorage().getLearned().get(key);
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
        ArrayList<MachineLearning.FeatureTypes> requiredFeatures = machineLearning.getRequiredFeatures();
        ArrayList<GenericMetric> metrics = machineLearning.getMetrics();
        GenericFeature feature = null;

        //Loop through the required features & their metrics and get user input.
        for (int i = 0; i < requiredFeatures.size() && i < metrics.size(); i++) {
            GenericMetric metric = metrics.get(i);
            MachineLearning.FeatureTypes featureType = requiredFeatures.get(i);

            //Get the predictable feature type.
            if (metric.isPredictable()) {
                predictFeature = featureType;
                continue;
            }

            if (featureType == MachineLearning.FeatureTypes.CartesianFeature) {
                feature = cartesianFeatureWindow(metric.getName(), metric);
            } else if (requiredFeatures.get(i) == MachineLearning.FeatureTypes.IntegerFeature) {
                feature = integerFeatureWindow(metric.getName(), metric);
            } else if (requiredFeatures.get(i) == MachineLearning.FeatureTypes.EnumFeature) {
                feature = enumFeatureWindow(metric.getName(), metric);
            } else if (requiredFeatures.get(i) == MachineLearning.FeatureTypes.ComplexFeature) {
                //todo: loop through each sub-feature and prompt user for values
            }

            //Add to gui feature to gui JList
            if (feature != null)
                listModel.addElement(feature);
        }
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

        if (feature instanceof CartesianFeature) {
            feature = cartesianFeatureWindow(feature.getName(), feature.getMetric());
        } else if (feature instanceof EnumFeature) {
            feature = enumFeatureWindow(feature.getName(), feature.getMetric());
        } else if (feature instanceof IntegerFeature) {
            feature = integerFeatureWindow(feature.getName(), feature.getMetric());
        } else if (feature instanceof DoubleFeature) {
            feature = doubleFeatureWindow(feature.getName(), feature.getMetric());
        }

        //todo: add complex feature implementation
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
    private CartesianFeature cartesianFeatureWindow(String name, GenericMetric metric) {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();

        Object[] message = {
            "X Coordinate:", xField,
            "Y Coordinate:", yField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, name + " (Cartesian):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int x, y;
            try {
                x = Integer.valueOf(xField.getText());
                y = Integer.valueOf(yField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Incorrect coordinate.");
                return cartesianFeatureWindow(name, metric); //Testing recursion here.
            }
            return new CartesianFeature(name, x, y, metric);
        }
        return null;
    }

    /**
     * enumFeatureWindow creates a new window which represents an enum feature, where the user
     * can enter the value. Creates a new EnumFeature and returns it.
     * @return EnumFeature
     * @author Josh Campitelli
     * todo: input validation, check if its an accepted DiscreteValue
     */
    private EnumFeature enumFeatureWindow(String name, GenericMetric metric) {
        JTextField enumField = new JTextField();
        Object[] message = {
            "Discrete Value:", enumField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, name + " (Discrete):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new EnumFeature(name, enumField.getText(), metric);
        }
        return null;
    }

    /**
     * integerFeatureWindow creates a new window which represents an integer feature, where the user
     * can enter the value. Creates a new IntegerFeature and returns it.
     * @return IntegerFeature
     * @author Josh Campitelli
     */
    private IntegerFeature integerFeatureWindow(String name, GenericMetric metric) {
        JTextField intField = new JTextField();
        Object[] message = {
            "Integer Value:", intField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, name + " (Integer):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int value;
            try {
                value = Integer.valueOf(intField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Incorrect value.");
                return integerFeatureWindow(name, metric); //Testing recursion here.
            }

            return new IntegerFeature(name, value, metric);
        }
        return null;
    }

    /**
     * doubleFeatureWindow creates a new window which represents a double feature, where the user
     * can enter the value. Creates a new DoubleFeature and returns it.
     * @return DoubleFeature
     * @author Josh Campitelli
     */
    private DoubleFeature doubleFeatureWindow(String name, GenericMetric metric) {
        JTextField doubleField = new JTextField();
        Object[] message = {
                "Double Value:", doubleField
        };
        int option = JOptionPane.showConfirmDialog(null, message, name + " (Double):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            double value;
            try {
                value = Double.valueOf(doubleField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Incorrect value.");
                return doubleFeatureWindow(name, metric); //Testing recursion here.
            }
            return new DoubleFeature(name, value, metric);
        }
        return null;
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
        machineLearning.learn(key, newInstance);
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
                JOptionPane.showMessageDialog(null, "Predicted Error is: " + predictedValue);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect value for K.");
                predictPrice(listModel);
            }
        }
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
