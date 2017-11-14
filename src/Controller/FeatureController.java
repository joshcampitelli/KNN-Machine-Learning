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

    public enum State {
        editProblem,
        addProblem
    }

    /**
     * @param machineLearning Reference to Model
     */
    public FeatureController(MachineLearning machineLearning, String state) {
        key = machineLearning.getProblem();
        this.machineLearning = machineLearning;
        storage = machineLearning.getStorage();
        if (state.toLowerCase().equals("add")) {
            this.state = State.addProblem;
        } else {
            this.state = State.editProblem;
        }
    }

    public void initialize(DefaultListModel<GenericFeature> listModel) {
        if (state == State.addProblem) {
            instantiateFeatures(listModel);
        }
    }

    public State getState() {
        return state;
    }

    /**
     * instantiateFeatures method called upon constructing the AlternateWindow it's purpose is to
     * get user input for 'problems' to be learned with MachineLearning. As the user enters each
     * feature they are added to the DefaultListModel, upon completion they're 'learned' by
     * MachineLearning
     * @param listModel gui list to display features.
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
     * @param list
     * @param listModel
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
     */
    public void updateInstance(DefaultListModel<GenericFeature> listModel) {
        storage.remove(key);
        learnInstance(listModel);
    }

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
}
