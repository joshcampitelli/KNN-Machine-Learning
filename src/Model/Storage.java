package Model;

import java.util.ArrayList;
import java.util.HashMap;
import Model.Metrics.*;
/**
* The Model.Storage Class keeps a log of all learned metrics and their
* respective problems.
*
* Code and comments by:
* 
* @author Josh Campitelli
*/
public class Storage {
	private HashMap<String, ArrayList<GenericMetric>> learned;
	/*provides access to metrics for any given problem identified by key*/
	private ArrayList<GenericMetric> metrics;
	
	public Storage() {
		learned = new HashMap<>();
	}

	/**
	 * The getLearned method returns the learned metrics for all known
	 * problems stored in the HashMap.
	 * @return reference to the HashMap<String, ArrayList<GenericMetric>>
	 * @author Josh Campitelli
	 */
	public HashMap<String, ArrayList<GenericMetric>> getLearned() {
		return learned;
	}

	/**
	 * The insert method simply puts data into the HashMap, where the problem
	 * parameter represents the problem, and metrics[] are the known metrics.
	 * @param key identifies the problem (HashMap Key)
	 * @param metrics array of metrics to be inserted into HashMap
	 * @author Josh Campitelli
	 */
	public void insert(String key, GenericMetric metrics[]) {
		ArrayList<GenericMetric> list = new ArrayList<>();
		for (int i = 0; i < metrics.length; i++) {
			list.add(metrics[i]);
		}
		learned.put(key, list);
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

	public void update(String key, ArrayList<GenericMetric> updatedInfo){

		learned.replace(key, updatedInfo);
		//Error Handling for if failed available
	}

	/**
	 * Adds a metric to the problem specified by "key" parameter.
	 * @param key identifies the problem (HashMap Key)
	 * @param metric to be inserted into the HashMap's ArrayList
	 * @author Josh Campitelli
	 */
	public void addMetric(String key, GenericMetric metric) {
		metrics = learned.get(key);
		metrics.add(metric);
	}

	/**
	 * Replaces the metric in the HashMap's ArrayList index position
	 * with the given metric.
	 * @param key identifies the problem (HashMap Key)
	 * @param index position within the ArrayList
	 * @param metric to be inserted into HashMap's ArrayList
	 * @author Josh Campitelli
	 */
	public void replaceMetric(String key, int index, GenericMetric metric) {
		metrics = learned.get(key);
		metrics.remove(index);
		metrics.add(index, metric);
	}

	/**
	 * Removes the metric in the HashMap's ArrayList index position
	 * @param key identifies the problem (HashMap Key)
	 * @param index position within the ArrayList
	 * @author Josh Campitelli
	 */
	public void removeMetric(String key, int index) {
		metrics = learned.get(key);
		metrics.remove(index);
	}
}
