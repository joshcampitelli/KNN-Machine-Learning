package Model;

import Model.Metrics.*;
import Model.FeatureLayout;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Ryan Ribeiro
 *
 */
public class MachineLearning {
	private String problem;
	private Storage storage;
	private Map<String, Integer> valuesMap;
	private int totalError;
	private ArrayList<FeatureLayout> myFeatures;

	public MachineLearning(String problem) {
		this.problem = problem;
		storage = new Storage();
		valuesMap = new HashMap<>();
		totalError = 0;
		myFeatures = new ArrayList<FeatureLayout>();
	}

	public void learn(String key, ArrayList<GenericMetric> metrics) {
		for (GenericMetric metric : metrics) {
			Learn(key, metric);
		}
	}

	/**
	 * Learn takes a given key and an array of an unknown number of metrics, and stores it in storage of type Model.Storage.
	 *
	 * @author Ryan Ribeiro
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
	 * @author Ryan Ribeiro
	 *
	 * @param k
	 * @param givenKey
	 * @param metrics
	 * @return	returns the predicted value for a problem with an unknown value. Return type is of int.
	 */
	public int Predict(int k, String givenKey, GenericMetric... metrics) {
		int predictedValue = 0, totalDistance = 0, tempPredictedValue = 0;
		int i = 0, j = 0; //loop control variables

		HashMap<String, ArrayList<GenericMetric>> learnedInfo; //HashMap of previously learned information

		learnedInfo = storage.getLearned();
		
		/*Loops through each entry in HashMap 'learnedInfo', saving the key and values.*/
		for (Map.Entry<String, ArrayList<GenericMetric>> entry : learnedInfo.entrySet()) {
			String existingKey = entry.getKey();
			ArrayList<GenericMetric> value = entry.getValue();
			/*
			 * Then loops through each value in the array of values, which are all different
			 * types of GenericMetric
			 */
			for (j = 0; j < value.size() - 1; j++) {
				//Finally summing up the distances of each metric compared to the pre-existing learned metrics
				totalDistance += value.get(j).getDistance(metrics[i]);
				i++;
			}
			//Storing the difference distances, to be used later in finding the smallest distances
			valuesMap.put(existingKey, totalDistance);
			totalDistance = 0;
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
			ArrayList<GenericMetric> tempMetric = learnedInfo.get(minimumDistance.getKey());
			tempPredictedValue += (int)tempMetric.get(tempMetric.size() - 1).getValue();
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
	 * @author Ryan Ribeiro
	 *
	 * @param k
	 * @param key
	 * @param metrics
	 * @Author Ryan Ribeiro, Ethan Morrill
	 * @return returns the distance between the expected value and the predicted value
	 */
	public int PredictError(int k, String key,  GenericMetric... metrics) {
		//Value is the last metric in the array of metrics
		int expectedValue = (int) metrics[metrics.length - 1].getValue();
		int predictedValue = this.Predict(k, key, metrics);
		int error = Math.abs(predictedValue-expectedValue);
		addError(error);
		return error;
	}

	/**
	 * Return the current total error of the problem, for printing purposes
	 *
	 * @Author Ethan Morrill
	 */
	public int getTotalError(){
		//Return the total error of the Machine Learning Problem
		return totalError;
	}

	/**
	 * Increase the total problem error by the new error that has occured in testing
	 *
	 * @Author Ethan Morrill
	 * @param error
	 */
	public void addError(int error){
		//Increase value of total error of the Machine Learning Problem
		totalError += error;
	}
	
	public String getProblem(){
		return problem;
	}
	
	public Storage getStorage(){
		return storage;
	}
	
	public void addFeatureLayout(String name, String type){
		myFeatures.add(new FeatureLayout(name,FeatureLayout.FeatureType.valueOf(type)));
	}

	
	public FeatureLayout getFeatureLayout(int i){
		return myFeatures.get(i);
  }

	public ArrayList<FeatureLayout> getFeatureLayout() {
		return myFeatures;

	}
}
