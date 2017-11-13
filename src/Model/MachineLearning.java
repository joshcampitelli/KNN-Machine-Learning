package Model;

import Model.Features.GenericFeature;
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
	private List<FeatureLayout> featureLayout;
	private Storage storage;
	private int totalError;
	private Map<String, Integer> distancesSum;

	public MachineLearning(String problem, ArrayList<FeatureLayout> featureLayout) {
		this.problem = problem;
		this.featureLayout = featureLayout;
		storage = new Storage();
		distancesSum = new HashMap<>();
		totalError = 0;
	}

	/**
	 * Learn takes a given key and an array of an unknown number of metrics, and stores it in storage of type Model.Storage.
	 *
	 * @author Ryan Ribeiro
	 *
	 * @param key
	 * @param features
	 */
	/*
	 * Change:	Learn->learn
	 * 			public void learn(String key, some new way to store stuff);
	 */
	public void learn(String key, ArrayList<GenericFeature> features) {
		storage.insert(key, features);
	}

	/**
	 * Given a key, a series of unknown metrics, and a k value for the kNN problem, Predict compares the information with
	 * and unknown value, and predicts the value based on the information it already knows. Returns the predicted value as
	 * an int.
	 *
	 * @author Ryan Ribeiro
	 *
	 * @param k
	 * @param features
	 * @return	returns the predicted value for a problem with an unknown value. Return type is of int.
	 */	
	/*
	 * Predict is given k (for kNN), givenKey (seems useless, can be removed), needs to be given
	 * the either arrayList or set of information that makes up a row.
	 */
	public int predict(int k, ArrayList<GenericFeature> features) {
		int predictedValue, tempPredictedValue = 0;
		int j; //loop control variable
		
		/*
		 * Take ArrayList<GenericFeature> features, loop through each feature, call getDistance
		 * on each one, return is a HashMap<String, Integer>. Have a master 
		 * HashMap<String: Key, Integer: Distance> which we then sum up up on a key by key basis.
		 * Finally, we take that master HashMap and sort it, and treat it the same way as before
		 * (Shown below) 
		 */
		
		for (GenericFeature feature : features) {
			Map<String, Integer> distances = new HashMap<>();
			String name = feature.getName();
			for (FeatureLayout featLay : featureLayout) {
				if (featLay.getName() == "Price") {
					//Do nothing
				} else {
					if (featLay.getName().equals(name))
						distances = featLay.getMetric().getDistance(feature);
					
					for (String key : distances.keySet()) {
						if (distancesSum.containsKey(key)) {
							distancesSum.put(key, distancesSum.get(key) + distances.get(key));
						} else {
							distancesSum.put(key, distances.get(key));
						}
					}
				}
			}			
		}
		
		for (j = 0; j < k; j++) {
			Entry<String, Integer> minimumDistance = null;
			for(Entry<String, Integer> entry : distancesSum.entrySet()) {
				if (minimumDistance == null || minimumDistance.getValue() > entry.getValue()) {
					minimumDistance = entry;
				}
			}
			tempPredictedValue += distancesSum.get(minimumDistance.getKey());
			distancesSum.remove(minimumDistance.getKey());
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
	 * @param features
	 * @Author Ryan Ribeiro, Ethan Morrill
	 * @return returns the distance between the expected value and the predicted value
	 */
	public int predictError(int k, String key,  ArrayList<GenericFeature> features) {
		//Value is the last metric in the array of metrics
		int expectedValue = 0;
		
		for (GenericFeature genFeat : features) {
			if (genFeat.getName() == "Price")
				expectedValue = (int)(genFeat.getValue()); //"Price" is going to be an IntegerMetric,
														   //so it will be safe to cast it to int			
		}
			
		int predictedValue = this.predict(k, features);
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
	
	public void addFeatureLayout(GenericMetric metric){
		myFeatures.add(new FeatureLayout(metric));
  }

	/*
	 * Need to add functions for display, edit, delete
	 * display:
	 * edit: picking a specific metric and changing it
	 * delete: picking a specific metric and deleting it
	 * 
	 */
  
	public FeatureLayout getFeatureLayout(int i){
		return featureLayout.get(i);
  }

	public List<FeatureLayout> getFeatureLayout() {
		return featureLayout;
	}
}
