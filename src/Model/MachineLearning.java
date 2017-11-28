package Model;

import Model.Features.*;
import Model.Metrics.*;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Ryan Ribeiro
 */
public class MachineLearning {
	private String problem;
	//private List<FeatureLayout> featureLayout;
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
	private ArrayList<GenericMetric> metrics;

	public MachineLearning(String problem) {
		this.problem = problem;
		storage = new Storage();
		distancesSum = new HashMap<>();
		requiredFeatures = new ArrayList<>();
		metrics = new ArrayList<>();
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
	public String predict(int k, ArrayList<GenericFeature> features) {
		if (k > storage.getSize() || k < 0) {
			//error
		}
		
		int predictedValue, tempPredictedValue = 0;
		int i; //loop control variable
		for (GenericFeature feature : features) {
			String name = feature.getName();
			Map<String, Integer> distances = new HashMap<>();
			i = 0;
			for (FeatureTypes featureType : requiredFeatures) {
				if (feature.isPredictable()) {
					if (feature instanceof IntegerFeature) {
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
							allPrices = storage.getFeature(name);
							//sums the values for each of the smallest distances
							tempPredictedValue += (int)(allPrices.get(minimumDistance.getKey()).getValue());
							//removes smallest distance so that the next iteration will produce the next smallest distance
							distancesSum.remove(minimumDistance.getKey());
						}
						//predictedValue is based on kNN, so divide by k to get average value
						predictedValue = tempPredictedValue / k;
						return predictedValue + "";
					} else if (feature instanceof EnumFeature) {
						HashMap<String, Integer> permittedValues = feature.getMetric().getPermittedValues();
						HashMap<String, Integer> valuesTally = new HashMap<>();
						HashMap<String, GenericFeature> allActions = new HashMap<>();
						allActions = storage.getFeature(name);
						for (i = 0; i < k; i++) {
							Entry<String, Integer> minimumDistance = null;
							//loops over each distance, determines smallest
							for(Entry<String, Integer> entry : distancesSum.entrySet()) {
								if (minimumDistance == null || permittedValues.get(minimumDistance.getValue()) > permittedValues.get(entry.getValue())) {
									minimumDistance = entry;
								}
							}
							//gets all previously stored actions associated with their keys
							
							//sums the values for each of the smallest distances
							//tempPredictedValue += permittedValues.get(allActions.get(minimumDistance.getKey()).getValue());
							if (valuesTally.containsKey(minimumDistance.getKey())) {
								valuesTally.put(minimumDistance.getKey(), valuesTally.get(minimumDistance.getKey()) + 1);
							}							
							//removes smallest distance so that the next iteration will produce the next smallest distance
							distancesSum.remove(minimumDistance.getKey());							
						}
						Entry<String, Integer> maxTally = null;
						for (Entry<String, Integer> entry : valuesTally.entrySet()) {
							if (maxTally == null || valuesTally.get(entry.getKey()) > valuesTally.get(maxTally.getKey())) {
								maxTally = entry;
							}
						}
						return (maxTally.getKey()); 
					}
				} else {					
					for (String key : distances.keySet()) {
						if (distancesSum.containsKey(key)) {
							distancesSum.put(key, distancesSum.get(key) + distances.get(key));
						} else {
							distancesSum.put(key,  distances.get(key));
						}
					}
				}
			}
		}
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
		int predictedValue = 0;
		
		//looping to find the feature that contains the value for that set of features
		for (GenericFeature genFeat : features) {
			HashMap<String, Integer> permittedValues = genFeat.getMetric().getPermittedValues();
			if (genFeat.isPredictable()) {
				if (genFeat instanceof IntegerFeature) {
					expectedValue = Integer.parseInt((String)genFeat.getValue());
					predictedValue = Integer.parseInt(this.predict(k, features));
				} else if (genFeat instanceof EnumFeature) {
					expectedValue = permittedValues.get(genFeat.getValue());
					predictedValue = permittedValues.get(this.predict(k, features));
				}
			}
		}
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
	
	public void setPredictable(String name) {
		for(GenericMetric metric : metrics) {
			if (metric.getName().equals(name)) {
				metric.setPredictable();
			}
		}
	}
	
	/**
	 * Adds a FeatureTypes to the ArrayList of requiredFeatures
	 * 
	 * @param metric
	 */
	public void addRequiredFeature(GenericMetric metric){
		metrics.add(metric);
	}

	public ArrayList<GenericMetric> getMetrics() {
		return metrics;
	}

	/**
	 * Returns the FeatureType at index i in the ArrayList of requiredFeatures
	 * 
	 * @param i
	 * @return	returns the FeatureType at index i of type requiredFeatures
	 */
	//add GenericMetric param and store in an ArrayList<GenericMetric>
	public FeatureTypes getRequiredFeatures(int i){
		if (i >= requiredFeatures.size() || i < 0) {
			//error
		}
		return requiredFeatures.get(i);
	}

	/**
	 * Returns a list of FeatureLayouts
	 * 
	 * @return returns a list of requiredFeatures of type List<FeatureLayout>
	 */
	public ArrayList<FeatureTypes> getRequiredFeatures() {
		return requiredFeatures;
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

	/**
	 * Updates an existing problem in storage.
	 * @param key key to HashMap in storage
	 * @param updatedInfo new array list to be inserted
	 */
	public void update(String key, ArrayList<GenericFeature> updatedInfo){
		storage.update(key, updatedInfo);
	}
}
