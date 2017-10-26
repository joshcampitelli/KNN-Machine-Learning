import Metrics.*;
import java.util.*;
import java.util.Map.Entry;


public class MachineLearning {
	String problem;
	Storage storage;
	Map<String, Integer> valuesMap;
	
	public MachineLearning(String problem) {
		this.problem = problem;
		storage = new Storage();
		valuesMap = new HashMap<>();
	}

	/**
	 * Learn takes a given key and an array of an unknown number of metrics, and stores it in storage of type Storage.
	 * 
	 * @param key
	 * @param metrics
	 */
	public void Learn(String key, GenericMetric... metrics) {
		storage.insert(key, metrics);
	}
	
	/**
	 * Given a key, a series of unknown metrics, and a k value for the kNN problem, Predict compares the information with
	 * and unknown value, and predicts the value based on the information it already knows. Returns the predicted value as
	 * an int.
	 * 
	 * @param k
	 * @param givenKey
	 * @param metrics
	 * @return	returns the predicted value for a problem with an unknown value. Return type is of int.
	 */
	public int Predict(int k, String givenKey, GenericMetric... metrics) {
		int predictedValue = 0, totalDistance = 0, tempPredictedValue = 0;
		int i = 0, j = 0; //loop control variables
		HashMap<String, GenericMetric[]> learnedInfo; //HashMap of previously learned information
		
		learnedInfo = storage.getLearned();
		
		/*
		 * Loops through each entry in HashMap 'learnedInfo', saving the key and values.
		 */
		for (Map.Entry<String, GenericMetric[]> entry : learnedInfo.entrySet()) {
			String existingKey = entry.getKey();
			GenericMetric[] value = entry.getValue();
			/*
			 * Then loops through each value in the array of values, which are all different
			 * types of GenericMetric
			 */
			for (j = 0; j < value.length - 1; j++) {
				//Finally summing up the distances of each metric compared to the pre-existing learned metrics
				totalDistance = totalDistance + value[j].getDistance(metrics[i]);
				i++;
			}
			//Storing the difference distances, to be used later in finding the smallest distances
			valuesMap.put(existingKey, totalDistance);			
			i = 0;
		}
		
		/*
		 * Looping through the HashMap of distances to find the smallest distance
		 */
		for (j = 0; j < k; j++) {
			Entry<String, Integer> minimumDistance = null;
			for(Entry<String, Integer> entry : valuesMap.entrySet()) {
				if (minimumDistance == null || minimumDistance.getValue() > entry.getValue()) {
					minimumDistance = entry;
				}
			}
			//Get the value of the key with the smallest distance, adding it to a running sum
			tempPredictedValue = tempPredictedValue + minimumDistance.getValue();
			//Remove the smallest distance, this way we are always pulling the next smallest distance
			valuesMap.remove(minimumDistance.getKey());
		}
		//predictedValue is based on kNN, so divide by k to get average value
		predictedValue = tempPredictedValue / k;
		
		return predictedValue;
	}
 
	/**
	 * Predict error determines the error between the predicted value and the expected value. Returns the distance.
	 * 
	 * @param k
	 * @param key
	 * @param metrics
	 * @return returns the distance between the expected value and the predicted value
	 */
	public int PredictError(int k, String key,  GenericMetric... metrics) {
		//Value is the last metric in the array of metrics
		int expectedValue = (int) metrics[metrics.length - 1].getValue();
		int predictedValue = this.Predict(k, key, metrics);
		
		return Math.abs(predictedValue-expectedValue);
	}
}
