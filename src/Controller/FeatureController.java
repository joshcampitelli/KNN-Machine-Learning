package Controller;

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
    //todo: only update gui for now. Once the user clicks save/learn/updateinstance (depending on construct param)
    //todo: is when you update storage and dispose the frame.
    /**
     * todo: may need another parameter specifying the case in which the window is being opened (edit/addnew)
     * todo: which will then enable/disable MenuItems accordingly.
     * @param machineLearning Reference to Model
     */
    public FeatureController(MachineLearning machineLearning) {
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

    }

    /**
     * AddFeature Method should just add locally to the ListModel until the user clicks learn (maybe save modified)
     * which will then use the MachineLearning class' learn method to insert into Storage.
     * @param listModel gui list
     */
    public void addFeature(DefaultListModel<GenericFeature> listModel) {
        String[] featureTypes = {"Cartesian Feature", "Enum Feature", "Integer Feature"};
        String input = (String) JOptionPane.showInputDialog(null, "Choose Feature Type:",
            "Feature Types:", JOptionPane.QUESTION_MESSAGE, null, featureTypes, featureTypes[0]);
        GenericFeature feature = null;
        if (input.equals(featureTypes[0])) {
            feature = cartesianFeatureWindow();
        } else if (input.equals(featureTypes[1])) {
            feature = enumFeatureWindow();
        } else if (input.equals(featureTypes[2])) {
            feature = integerFeatureWindow();
        }
        if (feature != null) {
            listModel.addElement(feature);
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
            feature = cartesianFeatureWindow();
        } else if (feature instanceof EnumFeature) {
            feature = enumFeatureWindow();
        } else if (feature instanceof IntegerFeature) {
            feature = integerFeatureWindow();
        }

        if (feature != null) {
            /*Replace the current feature with updated*/
            listModel.removeElementAt(index);
            listModel.add(index, feature); //Replace in gui
        }
    }

    /**
     * removeFeature removes a feature from the gui's DefaultListModel, this is only for the gui it does
     * not modify the actual storage until the window is closed using learn/update.
     * @param list JList on gui, used to get selected feature
     * @param listModel DefaultListModel on gui, displays the features
     */
    public void removeFeature(JList list, DefaultListModel<GenericFeature> listModel) {
        int index = list.getSelectedIndex();
        listModel.removeElementAt(index); //Remove from gui
    }

    /**
     * cartesianFeatureWindow creates a new window which represents an cartesian feature, where the user
     * can enter the value. Creates a new CartesianFeature and returns it.
     * @return CartesianFeature
     */
    private CartesianFeature cartesianFeatureWindow() {
        JTextField xField = new JTextField();
        JTextField yField = new JTextField();

        Object[] message = {
            "X Coordinate:", xField,
            "Y Coordinate:", yField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Cartesian Feature", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new CartesianFeature("", Integer.valueOf(xField.getText()), Integer.valueOf(yField.getText())); //todo: Add Name field
        }
        return null;
    }

    /**
     * enumFeatureWindow creates a new window which represents an enum feature, where the user
     * can enter the value. Creates a new EnumFeature and returns it.
     * @return EnumFeature
     */
    private EnumFeature enumFeatureWindow() {
        JTextField enumField = new JTextField();
        Object[] message = {
            "Enumerated Value:", enumField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Enum Feature", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new EnumFeature(enumField.getText());
        }
        return null;
    }

    /**
     * integerFeatureWindow creates a new window which represents an integer feature, where the user
     * can enter the value. Creates a new IntegerFeature and returns it.
     * @return IntegerFeature
     */
    private IntegerFeature integerFeatureWindow() {
        JTextField intField = new JTextField();
        Object[] message = {
            "Integer Value:", intField,
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Integer Feature", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new IntegerFeature("", Integer.valueOf(intField.getText())); //todo: add name field
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
        machineLearning.learn(key, newInstance);
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
}
