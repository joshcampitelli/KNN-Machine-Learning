package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import Model.Features.*;
/**
* The Model.Storage Class keeps a log of all learned metrics and their
* respective problems.
*
* Code and comments by:
* 
* @author Josh Campitelli
*/
public class Storage implements Serializable {
	private static final long serialVersionUID = 1L;
	private HashMap<String, ArrayList<GenericFeature>> learned;
	
	public Storage() {
		learned = new HashMap<>();
	}

	/*For UnitTests*/
	public int getSize() {
		return learned.size();
	}

	/**
	 * The getLearned method returns the learned metrics for all known
	 * problems stored in the HashMap.
	 * @return reference to the HashMap<String, ArrayList<GenericMetric>>
	 * @author Josh Campitelli
	 */
	public HashMap<String, ArrayList<GenericFeature>> getLearned() {
		return learned;
	}

	/**
	 * The insert method simply puts data into the HashMap, where the problem
	 * parameter represents the problem, and ArrayList are the known features.
	 * @param key identifies the problem (HashMap Key)
	 * @param features array of metrics to be inserted into HashMap
	 * @author Josh Campitelli
	 */
	public void insert(String key, ArrayList<GenericFeature> features) {
		learned.put(key, features);
	}

	/*
	* The remove method removes an instance of learned information from the hashmap.
	* Parameter represents the string key used to represent the
	* Ex:
	* 	remove("h1")
	* @author Ethan Morrill
	*/
	public void remove(String key){

		learned.remove(key);
		//Error Handling for if failed available
	}

	public void update(String key, ArrayList<GenericFeature> updatedInfo){

		learned.replace(key, updatedInfo);
		//Error Handling for if failed available
	}

	/**
	 * Returns hashmap of keys and features for a given featureName
	 * @param featureName identifies the name f the feature of interest
	 * @return Hashmap of {key, GenercFeature} for the provided featureName
	 * @author Ethan Morrill
	 */
	public HashMap<String, GenericFeature> getFeature(String featureName){
		HashMap<String, GenericFeature> targetFeatures = new HashMap<>();
		Set<String> keys = learned.keySet();
		for(String key : keys){
			ArrayList<GenericFeature> values = learned.get(key);
			for (GenericFeature feature : values) {
				if(feature.getName().equals(featureName)){
					targetFeatures.put(key,feature);
				}
			}
		}
		return targetFeatures;
	}
	
	public String getFeatureString(String key) {
		ArrayList<GenericFeature> list = learned.get(key);
		String str = "";
		for(int i = 0; i < list.size(); i++) {
			str += list.get(i).toString() + "    ";
		}
		return str;
	}
	
	/**
	 * Compares two instances of Storage, and determines if they are equal to one another. That
	 * is that they both have the same size, and that for every entry in learned all features
	 * are equal to one another.
	 * 
	 * @param compareStorage	instance of Storage to be compared with 'this' instance	
	 * @return					returns boolean result: true if equal, false if not
	 * @Author Ryan Ribeiro
	 */
	public boolean equals(Storage compareStorage) {
		int i;
		HashMap<String, ArrayList<GenericFeature>> compareStorageLearn = compareStorage.getLearned();
		Set<String> keys = learned.keySet();
		
		if (this.getSize() != compareStorage.getSize()) {
			return false;
		}
		/*
		 * Iterates through every entry in 'learned', comparing the features names and metrics names within each feature 
		 * to those of the features of the 'learned' values from 'compareStorage'
		 */
		for (String key : keys) {
			for (i = 0; i < learned.get(key).size(); i++) {
				//Comparing feature names
				if (!(learned.get(key).get(i).getName().equals(compareStorageLearn.get(key).get(i).getName()))) {
					return false;
				}
				//Comparing metric names
				if (!(learned.get(key).get(i).getMetric().getName().equals(compareStorageLearn.get(key).get(i).getMetric().getName()))) {
					return false;
				}
			}
		}		
		return true;
	}
}
