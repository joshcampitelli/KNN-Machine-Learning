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
	
	public Storage() {
		learned = new HashMap<>();
	}

	/*
	 * The getLearned method returns the learned metrics for all known
	 * problems stored in the HashMap.
	 * @author Josh Campitelli
	 */
	public HashMap<String, ArrayList<GenericMetric>> getLearned() {
		return learned;
	}

	/*
	* The insert method simply puts data into the HashMap, where the problem
	* parameter represents the problem, and metrics[] are the known metrics.
	* Ex:
	* 	problem = "h1", metrics[] = {new CartesianMetric(12, 25), new IntegerMetric(1200),
	* @author Josh Campitelli														new enumMetric("new")};
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
	 */
	public void addMetric(String key, GenericMetric metric) {
		ArrayList<GenericMetric> metrics = learned.get(key);
		metrics.add(metric);
	}

	/**
	 * Replaces the metric in the HashMap's ArrayList index position
	 * with the given metric.
	 * @param key identifies the problem (HashMap Key)
	 * @param index position within the ArrayList
	 * @param metric to be inserted into HashMap's ArrayList
	 */
	public void replaceMetric(String key, int index, GenericMetric metric) {
		ArrayList<GenericMetric> metrics = learned.get(key);
		metrics.remove(index);
		metrics.add(index, metric);
	}

	/**
	 * Removes the metric in the HashMap's ArrayList index position
	 * @param key identifies the problem (HashMap Key)
	 * @param index position within the ArrayList
	 */
	public void removeMetric(String key, int index) {
		ArrayList<GenericMetric> metrics = learned.get(key);
		metrics.remove(index);
	}
}
