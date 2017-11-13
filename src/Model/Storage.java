package Model;

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
public class Storage {
	private HashMap<String, ArrayList<GenericFeature>> learned;
	/*provides access to metrics for any given problem identified by key*/
	private ArrayList<GenericFeature> features;
	
	public Storage() {
		learned = new HashMap<>();
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
	 * parameter represents the problem, and metrics[] are the known metrics.
	 * @param key identifies the problem (HashMap Key)
	 * @param features array of metrics to be inserted into HashMap
	 * @author Josh Campitelli
	 */
	public void insert(String key, GenericFeature features[]) {
		ArrayList<GenericFeature> list = new ArrayList<>();
		for (int i = 0; i < features.length; i++) {
			list.add(features[i]);
		}
		learned.put(key, list);
	}

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
	 * Adds a metric to the problem specified by "key" parameter.
	 * @param key identifies the problem (HashMap Key)
	 * @param feature to be inserted into the HashMap's ArrayList
	 * @author Josh Campitelli
	 */
	public void addMetric(String key, GenericFeature feature) {
		features = learned.get(key);
		features.add(feature);
	}

	/**
	 * Replaces the metric in the HashMap's ArrayList index position
	 * with the given metric.
	 * @param key identifies the problem (HashMap Key)
	 * @param index position within the ArrayList
	 * @param feature to be inserted into HashMap's ArrayList
	 * @author Josh Campitelli
	 */
	public void replaceMetric(String key, int index, GenericFeature feature) {
		features = learned.get(key);
		features.remove(index);
		features.add(index, feature);
	}

	/**
	 * Removes the metric in the HashMap's ArrayList index position
	 * @param key identifies the problem (HashMap Key)
	 * @param index position within the ArrayList
	 * @author Josh Campitelli
	 */
	public void removeMetric(String key, int index) {
		features = learned.get(key);
		features.remove(index);
	}

	public HashMap<String, GenericFeature> getFeature(String featureName){


		HashMap<String, GenericFeature> targetFeatures = new HashMap<>();
		Set<String> keys = learned.keySet();
		for(String key : keys){
			ArrayList<GenericFeature> values = learned.get(key);
			int i = 0;
			while(i< values.size()){

				GenericFeature feature = values.get(i);
				if(feature.getName().equals(featureName)){
					targetFeatures.put(key,feature);
				}
			}
		}
		return targetFeatures;
	}
}
