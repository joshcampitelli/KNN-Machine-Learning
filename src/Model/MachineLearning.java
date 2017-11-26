package Model;

import Model.Features.GenericFeature;
import Model.Metrics.*;
import Model.FeatureLayout;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Ryan Ribeiro
 */
public class MachineLearning {
	private String problem;
	private List<FeatureLayout> featureLayout;
	private Storage storage;
	private int totalError;
	private Map<String, Integer> distancesSum;

	public enum FeatureTypes {
		CartesianFeature,
		EnumFeature,
		IntegerFeature,
		ComplexFeature
	}

	//Array List for the features that are required for each problem
	private ArrayList<FeatureTypes> requiredFeatures;

	public MachineLearning(String problem) {
		this.problem = problem;
		storage = new Storage();
		distancesSum = new HashMap<>();
		featureLayout = new ArrayList<>();
		totalError = 0;
	}

	/**
	 * Learn takes a given key and an array of an unknown number of metrics, and stores it in storage of type Model.Storage.
	 *
	 * 
	 * @param key
	 * @param features
	 * @author Ryan Ribeiro
	 */
	public void learn(String key, ArrayList<GenericFeature> features) {
		storage.insert(key, features);
	}

	/**
	 * Compares a passed ArrayList of features to the previously learned features to calculate the
	 * distance between each existing piece of learned information with the new information. Then,
	 * using the passed value 'k', predict calculates the predicted value for the new information
	 * based on the 'k' shortest distances of learned information. Returns the predicted value for
	 * the new information as an int.
	 *
	 * @param k
	 * @param features
	 * @return returns the predicted value for a problem with an unknown value. Return type is of int.
	 * @author Ryan Ribeiro
	 */	
	public int predict(int k, ArrayList<GenericFeature> features) {
		if (k > storage.getSize() || k < 0) {
			//error
		}
		
		int predictedValue, tempPredictedValue = 0;
		int i; //loop control variable
		
		//looping through all features passed to predict
		for (GenericFeature feature : features) {
			//temporary HashMap to store returned distances
			Map<String, Integer> distances = new HashMap<>();
			String name = feature.getName();
			//looping through all FeatureLayouts created from constructor
			for (FeatureLayout featLay : featureLayout) {
				//checks if it's the price metric, and if it is we don't want the distance of it
				if (featLay.getName().toLowerCase() == "price") {
					//Do nothing
				} else {
					//matches the names to know which feature belongs to which FeatureLayout
					if (featLay.getName().equals(name))
						distances = featLay.getMetric().getDistance(feature);
					
					//summing the total distance from the existing distancesSum and the returned
					//distances. Sums on a key by key basis 
					for (String key : distances.keySet()) {
						if (distancesSum.containsKey(key)) {
							distancesSum.put(key, distancesSum.get(key) + distances.get(key));
						} else {
							distancesSum.put(key, distances.get(key));
						}
					}
					//for each loop exists with distancesSum containing the sum of the distances
					//for all features of a specific key 
				}
			}			
		}
		
		//looping to determine the smallest distance 'k' times
		for (i = 0; i < k; i++) {
			Entry<String, Integer> minimumDistance = null;
			//loops over each distance, determines smallest
			for(Entry<String, Integer> entry : distancesSum.entrySet()) {
				if (minimumDistance == null || minimumDistance.getValue() > entry.getValue()) {
					minimumDistance = entry;
				}
			}
			//gets all previously stored prices associated with their keys
			HashMap<String, GenericFeature> allPrices = new HashMap<>();
			allPrices = storage.getFeature("price");
			//sums the values for each of the smallest distances
			tempPredictedValue += (int)(allPrices.get(minimumDistance.getKey()).getValue());
			//removes smallest distance so that the next iteration will produce the next smallest distance
			distancesSum.remove(minimumDistance.getKey());
		}
		//predictedValue is based on kNN, so divide by k to get average value
		predictedValue = tempPredictedValue / k;

		return predictedValue;
	}

	/**
	 * Predict error determines the error between the predicted value and the expected value. Returns the distance.
	 *
	 *
	 * @param k
	 * @param features
	 * @return returns the distance between the expected value and the predicted value
	 * @Author Ryan Ribeiro, Ethan Morrill
	 */
	public int predictError(int k, ArrayList<GenericFeature> features) {
		if (k > storage.getSize() || k < 0) {
			//error
		}
		
		int expectedValue = 0;
		
		//looping to find the feature that contains the value for that set of features
		for (GenericFeature genFeat : features) {
			if (genFeat.getName().toLowerCase() == "price")
				//"price" is going to always be an IntegerFeature, so casting it to int is safe
				expectedValue = (int)(genFeat.getValue());			
		}	
		int predictedValue = this.predict(k, features);
		//error is the difference between the predicted value and the expected value
		int error = Math.abs(predictedValue-expectedValue);
		addError(error);
		return error;
	}

	/**
	 * Return the current total error of the problem, for printing purposes
	 *
	 * @return the current total error of the problem as an int
	 * @Author Ethan Morrill
	 */
	public int getTotalError(){
		//Return the total error of the MachineLearning Problem
		return totalError;
	}

	/**
	 * Increase the total problem error by the new error that has occurred in testing
	 *
	 * @param error
	 * @Author Ethan Morrill
	 */
	public void addError(int error){
		//Increase value of total error of the MachineLearning Problem
		totalError += error;
	}
	
	/**
	 * Returns the name of problem for the instance of MachineLearning
	 * 
	 * @return the name of the problem as a String
	 */
	public String getProblem(){
		return problem;
	}
	
	/**
	 * Returns reference to the storage used for the instance of MachineLearning
	 * 
	 * @return reference to the storage as type Storage
	 */
	public Storage getStorage(){
		return storage;
	}
	
	/**
	 * Adds a FeatureLayout to the ArrayList of FeatureLayouts
	 * 
	 * @param metric
	 */
	public void addFeatureLayout(GenericMetric metric){
		featureLayout.add(new FeatureLayout(metric));
	}
  
	/**
	 * Returns the FeatureLayout at index i in the ArrayList of FeatureLayouts
	 * 
	 * @param i
	 * @return	returns the FeatureLayout at index i of type FeatureLayout
	 */
	public FeatureLayout getFeatureLayout(int i){
		if (i >= featureLayout.size() || i < 0) {
			//error
		}
		return featureLayout.get(i);
	}

	/**
	 * Returns a list of FeatureLayouts
	 * 
	 * @return returns a list of FeatureLayouts of type List<FeatureLayout>
	 */
	public List<FeatureLayout> getFeatureLayout() {
		return featureLayout;
	}
	
	/**
	 * Deletes an existing piece of learned information with key 'key' from storage
	 * 
	 * @param key
	 */
	public void deleteLearned (String key) {
		//the error trapping for this one has to happen on the end of 'remove' in Storage
		storage.remove(key);
	}
}
