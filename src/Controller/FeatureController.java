package Controller;

import Model.FeatureLayout;
import Model.MachineLearning;

import Model.Features.GenericFeature;
import Model.Features.IntegerFeature;
import Model.Features.CartesianFeature;
import Model.Features.EnumFeature;

import Model.Storage;

import javax.swing.*;
import java.util.ArrayList;

public class FeatureController {
    private String key; //Problem Key in HashMap
    private MachineLearning machineLearning;
    private Storage storage; //HashMap Reference
    private State state; //Indicates edit or add
    private ProblemWindowController control;

    /*
     * Represents the state in which the program was opened
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

    /**\
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
        GenericFeature feature = null;
        for (FeatureLayout featureLayout : machineLearning.getFeatureLayout()) {
            if (featureLayout.getFeatureType() == FeatureLayout.FeatureType.CartesianFeature) {
                feature = cartesianFeatureWindow(featureLayout.getName());
            } else if (featureLayout.getFeatureType() == FeatureLayout.FeatureType.IntegerFeature) {
                feature = integerFeatureWindow(featureLayout.getName());
            } else if (featureLayout.getFeatureType() == FeatureLayout.FeatureType.DiscreteFeature) {
                feature = enumFeatureWindow(featureLayout.getName());
            }
            if (feature != null) {
                listModel.addElement(feature);
            }
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
            feature = cartesianFeatureWindow(feature.getName());
        } else if (feature instanceof EnumFeature) {
            feature = enumFeatureWindow(feature.getName());
        } else if (feature instanceof IntegerFeature) {
            feature = integerFeatureWindow(feature.getName());
        }

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
    private CartesianFeature cartesianFeatureWindow(String name) {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();

        Object[] message = {
            "X Coordinate:", xField,
            "Y Coordinate:", yField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, name + " (Cartesian):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new CartesianFeature(name, Integer.valueOf(xField.getText()), Integer.valueOf(yField.getText()));
        }
        return null;
    }

    /**
     * enumFeatureWindow creates a new window which represents an enum feature, where the user
     * can enter the value. Creates a new EnumFeature and returns it.
     * @return EnumFeature
     * @author Josh Campitelli
     */
    private EnumFeature enumFeatureWindow(String name) {
        JTextField enumField = new JTextField();
        Object[] message = {
            "Discrete Value:", enumField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, name + " (Discrete):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new EnumFeature(name, enumField.getText());
        }
        return null;
    }

    /**
     * integerFeatureWindow creates a new window which represents an integer feature, where the user
     * can enter the value. Creates a new IntegerFeature and returns it.
     * @return IntegerFeature
     * @author Josh Campitelli
     */
    private IntegerFeature integerFeatureWindow(String name) {
        JTextField intField = new JTextField();
        Object[] message = {
            "Integer Value:", intField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, name + " (Integer):", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new IntegerFeature(name, Integer.valueOf(intField.getText()));
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

        JTextField intField = new JTextField();
        Object[] message = {
                "Price:", intField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Price of Instance", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            newInstance.add(new IntegerFeature("price", Integer.valueOf(intField.getText())));
            machineLearning.learn(key, newInstance);
           
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
        learnInstance(listModel);
        
    }

    /**
     * priceExists determines whether or not a instance has a price parameter
     * @return boolean
     * @author Josh Campitelli
     */
    public boolean priceExists() {
        ArrayList<GenericFeature> features = machineLearning.getStorage().getLearned().get(key);
        if (features != null) {
            for (GenericFeature feature : machineLearning.getStorage().getLearned().get(key)) {
                if (feature.getName().toLowerCase().equals("price")) {
                    return true;
                }
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
        int predictedValue = 0;
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
            predictedValue = machineLearning.predict(Integer.valueOf(intField.getText()), newInstance);
            JOptionPane.showMessageDialog(null, "Predicted Value is: " + predictedValue);
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
            predictError = machineLearning.predictError(Integer.valueOf(intField.getText()), newInstance);
            JOptionPane.showMessageDialog(null, "Predicted Error is: " + predictError);
        }
    }

    public ProblemWindowController getPWC(){
    	return control;
    }
}
