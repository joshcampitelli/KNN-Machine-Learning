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

}
