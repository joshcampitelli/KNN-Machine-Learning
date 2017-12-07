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
	private ArrayList<FeatureTypes> requiredFeatures;
	private ArrayList<GenericMetric> metrics;
	
	public enum FeatureTypes {
		CartesianFeature,
		EnumFeature,
		IntegerFeature,
		ComplexFeature
	}
	
	public MachineLearning(String problem) {
		this.problem = problem;
		storage = new Storage();
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
		int predictedValue, tempPredictedValue = 0, i = 0, j = 0;;
		String name = metrics.get(metrics.size() - 1).getName();
		Map<String, Double> distancesSum = new HashMap<>(); //Sum of distances between passed data and all previously stored data, sorted by keys of the stored data
		
		//This first for loop goes through the passed features and sums up the distances between them and all the previously stored features.
		for (GenericFeature feature : features) {
			/*
			 * It's necessary to ensure that the feature is not the final predictable feature, as when we do predictError(), predict() is called with the
			 * final value already given. If this check isn't done, then that final feature will be used in the prediction, giving an incorrect prediction.  
			 */
			if (!(metrics.get(i).isPredictable())) {
				Map<String, Double> distances = new HashMap<>(); //distances between a specific feature and all stored data of that specific feature, sorted by keys of the stored data

				distances = feature.getMetric().getDistance(feature);
			
				//Loop through distances and add values to distancesSum
				for (String key : distances.keySet()) {
					//If there is already an entry with that key, as there will be if there is more than 1 GenericFeature in features, then it sums the values but saves it with the same key
					if (distancesSum.containsKey(key)) {
						distancesSum.put(key, distancesSum.get(key) + distances.get(key));
					} else {
						distancesSum.put(key,  distances.get(key));
					}	
				}
			}
			i++; //This is used to know which metric in the ArrayList metrics we are at
		}
		
		GenericMetric lastMetric = metrics.get(metrics.size() - 1); //this is the last GenericMetric stored in the ArrayList metrics
		
		if (lastMetric instanceof IntegerAbsoluteMetric) {
			for (j = 0; j < k; j++) {
				Entry<String, Double> minimumDistance = null;
				//loops over each distance, determines smallest
				for(Entry<String, Double> entry : distancesSum.entrySet()) {
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
			return Integer.toString(predictedValue);
			
		} else if (lastMetric instanceof DiscreteBinaryMetric) {
			String tempGuess; //the name of the action being predicted as the best option
			HashMap<String, Integer> bestMatchValuesTally = new HashMap<>(); //Running tally of how many times a given value is selected as the one with the minimum distance to our problem
			for (j = 0; j < k; j++) {
				Entry<String, Double> minimumDistance = null;
				//loops over each distance, determines smallest
				for(Entry<String, Double> entry : distancesSum.entrySet()) {
					if (minimumDistance == null || minimumDistance.getValue() > entry.getValue()) {
						minimumDistance = entry;
					}
				}
				
				//gets all previously stored actions associated with their keys
				HashMap<String, GenericFeature> allActions = new HashMap<>();
				allActions = storage.getFeature(name);
				
				//Gets the action associated with a given piece of learned data
				tempGuess = (String) allActions.get(minimumDistance.getKey()).getValue();
				
				//If a value has already been chosen before as the value with the minimum distance to our problem, we add another tally to show that it has been chosen as the best option multiple times
				if (bestMatchValuesTally.containsKey(tempGuess)) {
					bestMatchValuesTally.put(tempGuess, bestMatchValuesTally.get(tempGuess) + 1);
				} else {
					//Otherwise, we add it with a tally of 1 to indicate it's the first occurrence of it being choose
					bestMatchValuesTally.put(tempGuess, 1);
				}
				distancesSum.remove(minimumDistance.getKey());
			}
			
			Entry<String, Integer> bestMatchedValuesMaxTally = null;
			/* 
			 * Loops over the HashMap of the values and the number of tallys they got, selecting the one with the most tallys.
			 * If there is ever a tie, presumably the one that was entered into the table first was the one that had the first occurrence of a best choice for our given problem. Therefore, if 
			 * it has an equal number of tallys, it probably was closer to being the best answer than the other option. This is not foolproof, since you could have it be the first occurrence,
			 * have a two of the next best occur, have the first occur again, and it would be debatable that two and three are a better estimate than one and four. 
			 */
			for (Entry<String, Integer> entry : bestMatchValuesTally.entrySet()) {
				if (bestMatchedValuesMaxTally == null || bestMatchValuesTally.get(entry.getKey()) > bestMatchValuesTally.get(bestMatchedValuesMaxTally.getKey())) {
					bestMatchedValuesMaxTally = entry;
				}
			}
			//returns the key, which is the name of the value, 
			return bestMatchedValuesMaxTally.getKey();
		}
		return "";
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

		GenericFeature feature = features.get(features.size() - 1);
		HashMap<String, Integer> permittedValues = feature.getMetric().getPermittedValues();
		if (feature instanceof IntegerFeature) {
			expectedValue = (int) feature.getValue();
			predictedValue = Integer.parseInt(this.predict(k, features));
		} else if (feature instanceof EnumFeature) {
			expectedValue = permittedValues.get(feature.getValue());
			predictedValue = permittedValues.get(this.predict(k, features));
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
