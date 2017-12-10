package Model;

import Model.Features.*;
import Model.Metrics.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 * @author Ryan Ribeiro
 * 
 */
public class MachineLearning implements Serializable {
	private static final long serialVersionUID = 1L;
	private String problem;
	private Storage storage;
	private int totalError;
	private ArrayList<GenericMetric> metrics;
	
	public MachineLearning(String problem) {
		this.problem = problem;
		storage = new Storage();
		metrics = new ArrayList<>();
		totalError = 0;
	}

	/**
	 * Takes a given key and an ArrayList of GenericFeatures, and 'learns' the information by storing
	 * it in an instance of Storage.
	 * 
	 * @param	key				a key of type String by which a set of features can be identified
	 * @param 	features		an ArrayList of GenericFeatures containing information for a given
	 * 							piece of data
	 * @author 	Ryan Ribeiro
	 */
	public void learn(String key, ArrayList<GenericFeature> features) {
		storage.insert(key, features);
	}

	/**
	 * Takes a given ArrayList of GenericFeatures, and predicts what the value for these features should
	 * be based on the previously learned problems. The predicted value is also based on the type of
	 * kNN problem, determined by the value k given, where the k best predictions are taken into
	 * consideration when determining the predicted value.
	 *
	 * @param k					an Integer used to specify the type of kNN problem
	 * @param features			an ArrayList of GenericFeatures containing information for a given
	 * 							piece of data, excluding the final value which is to be predicted
	 * @return 					returns the predicted value for a given problem
	 * @author Ryan Ribeiro
	 */	
	public String predict(int k, ArrayList<GenericFeature> features) {
		int predictedValue, tempPredictedValue = 0, i = 0, j = 0;;
		String name = metrics.get(metrics.size() - 1).getName();
		Map<String, Double> distancesSum = new HashMap<>(); //Sum of distances between passed data and all previously stored data, sorted by keys of the stored data
		GenericMetric lastMetric = metrics.get(metrics.size() - 1); //this is the last GenericMetric stored in the ArrayList metrics
		
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
		
		if (lastMetric instanceof IntegerAbsoluteMetric) {
			for (j = 0; j < k; j++) {
				Entry<String, Double> minimumDistance = this.getMinimumDistance(distancesSum);
								
				//gets all previously stored prices associated with their keys
				HashMap<String, GenericFeature> allPrices = storage.getFeature(name);
				
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
				Entry<String, Double> minimumDistance = this.getMinimumDistance(distancesSum);
				
				//gets all previously stored actions associated with their keys
				HashMap<String, GenericFeature> allActions = storage.getFeature(name);
				
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
	 * Determines the entry with the smallest distance out of a list of entries
	 * 
	 * @param distSum			a Map of Strings associated with a double which represents distance
	 * @return					returns an Entry in the Map which has the smallest distance
	 * @Author Ryan Ribeiro
	 */
	private Entry<String, Double> getMinimumDistance(Map<String, Double> distSum) {
		Entry<String, Double> minimumDistance = null;
		//loops over each distance, determines smallest
		for(Entry<String, Double> entry : distSum.entrySet()) {
			if (minimumDistance == null || minimumDistance.getValue() > entry.getValue()) {
				minimumDistance = entry;
			}
		}
		return minimumDistance;
	}

	/**
	 * Predict error determines the error between the predicted value and the expected value. Returns the distance.
	 *
	 *
	 * @param k					an Integer used to specify the type of kNN problem
	 * @param features			an ArrayList of GenericFeatures containing information for a given
	 * 							piece of data, excluding the final value which is to be predicted
	 * @return 					returns the difference between the expected predicted value and the actual predicted value
	 * @Author Ryan Ribeiro, Ethan Morrill
	 */
	public int predictError(int k, ArrayList<GenericFeature> features) {
		int expectedValue = 0;
		int predictedValue = 0;
		GenericFeature feature = features.get(features.size() - 1);
		HashMap<String, Integer> permittedValues = feature.getMetric().getPermittedValues();
		
		if (feature instanceof IntegerFeature) {
			//If it's an IntegerFeature, the String from predict() needs to be converted into an integer equivalent
			expectedValue = (int) feature.getValue();
			predictedValue = Integer.parseInt(this.predict(k, features));
		} else if (feature instanceof EnumFeature) {
			//If it's an EnumFeature, the value for a given feature can be obtained from the permittedValues HashMap
			expectedValue = permittedValues.get(feature.getValue());
			predictedValue = permittedValues.get(this.predict(k, features));
		}
		
		//error is the difference between the predicted value and the expected value
		int error = Math.abs(predictedValue-expectedValue);
		addError(error);
		return error;
	}

	/**
	 * Returns the current total error of the problem, for printing purposes
	 *
	 * @return 					the current total error of the problem as an int
	 * @Author Ethan Morrill
	 */
	public int getTotalError(){
		//Return the total error of the MachineLearning Problem
		return totalError;
	}

	/**
	 * Increase the total error by the new error obtained from predictError()
	 *
	 * @param error				an Integer whose value represents the error obtained from predictError()
	 * @Author Ethan Morrill
	 */
	public void addError(int error){
		//Increase value of total error by the new error
		totalError += error;
	}
	
	/**
	 * Returns the name of problem for the instance of MachineLearning
	 * 
	 * @return 					the name of the problem for this instance of MachineLearning
	 * @Author Ryan Ribeiro
	 */
	public String getProblem(){
		return problem;
	}
	
	/**
	 * Returns reference to the storage used for the instance of MachineLearning
	 * 
	 * @return 					reference to the storage for this instance of MachineLearning
	 * @Author Ryan Ribeiro
	 */
	public Storage getStorage(){
		return storage;
	}
	
	/**
	 * Sets a boolean value used to determine if a given metric is predictable or not.
	 * 
	 * @param name				a String used to identify which metric is to be set as predictable
	 * @Author Logan MacGillivray
	 */
	public void setPredictable(String name) {
		for(GenericMetric metric : metrics) {
			if (metric.getName().equals(name)) {
				metric.setPredictable();
			}
		}
	}
	
	/**
	 * Adds a metric to the ArrayList of metrics
	 * 
	 * @param metric
	 * @Author Ryan Ribeiro
	 */
	public void addRequiredFeature(GenericMetric metric){
		metrics.add(metric);
	}

	/**
	 * Returns an ArrayList of GenericMetrics for the instance of MachineLearning
	 * @return					an ArrayList of GenericMetrics for the instance of MachineLearning
	 * @Author Ryan Ribeiro
	 */
	public ArrayList<GenericMetric> getMetrics() {
		return metrics;
	}
	
	/**
	 * Deletes an existing piece of learned information with key 'key' from storage
	 * 
	 * @param key				a String by which the values stored in storage can be identified
	 * @Author Josh Campitelli
	 */
	public void deleteLearned (String key) {
		storage.remove(key);
	}

	/**
	 * Updates an existing problem in storage.
	 * 
	 * @param key 				a String by which the values stored in storage can be identified
	 * @param updatedInfo 		a new ArrayList of GenericFeatures to replace the previously learned ones
	 * @Author Logan MacGillivary
	 */
	public void update(String key, ArrayList<GenericFeature> updatedInfo){
		storage.update(key, updatedInfo);
	}
	
	/**
	 * Returns the size of the instance of MachineLearning as the size of it's Storage
	 * 
	 * @return					an Integer representing the size of the Storage for this MachineLearning instance
	 * @Author Ryan Ribeiro
	 */
	public int getSize() {
		return storage.getSize();
	}
	
	/**
	 * Saves the instance of MachineLearning to a file of name 'fileName' using serialization.
	 * 
	 * @param fileName			a String which will become the name of the file being saved
	 * @author Ryan Ribeiro
	 */
	public void serialSave(String fileName) {
		if (!(fileName.endsWith(".ser"))) {
			fileName = fileName + ".ser";
		}
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName); 		//Creates an FileOutputStream using the fileName provided
			ObjectOutputStream out = new ObjectOutputStream(fileOut);		//Creates an ObjectOutputStream using the created FileOutputStream
			out.writeObject(this);		//Writes out this instance of MachineLearning
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Opens the file specified with the name 'fileName' passed by the user. Opens using serialization,
	 * and returns an instance of MachineLearning.
	 * 
	 * @param fileName			a String which will be used to determine which file to open
	 * @return returns 			an instance of MachineLearning
	 * @Author Ryan Ribeiro
	 */
	public MachineLearning serialOpen(String fileName) {
		MachineLearning createdMachineLearning = null;
		
		try {
			FileInputStream fileIn = new FileInputStream(fileName);		//Creates an FileInputStream using the fileName provided
			ObjectInputStream in = new ObjectInputStream(fileIn);		//Creates an ObjectInputStream using the created FileInputStream
			createdMachineLearning = (MachineLearning) in.readObject(); //Casts the returned object to MachineLearning
			in.close();
			fileIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return createdMachineLearning;		//Opened instance of MachineLearning is returned
	}
	
	/**
	 * Compares two instances of MachineLearning, and determines if they are equal to one another. That
	 * is that they both have the same problem name, total error, matching metrics, and both their
	 * storages contain the same information.
	 * 
	 * @param compareML			instance of MachineLearning to be compared with 'this' instance
	 * @return 					returns boolean result: true if equal, false if not
	 * @Author Ryan Ribeiro
	 */
	public boolean equals(MachineLearning compareML) {
		int i;
		ArrayList<GenericMetric> compareMetrics = compareML.getMetrics();
		//If the sizes are not the same, the two aren't comparable
		if (!(this.getSize() == compareML.getSize())) {
			return false;
		}
		//All if statements must pass in order for the two instances of MachineLearning to be considered equal
		if (!(this.problem.equals(compareML.getProblem()))) {
			return false;
		}
		if (!(this.totalError == compareML.getTotalError())) {
			return false;
		}
		//Looping through the metrics and comparing if they have the same name
		for (i = 0; i < metrics.size(); i++) {
			if (!(metrics.get(i).getName().equals(compareMetrics.get(i).getName()))) {
				return false;
			}
		}		
		if (!(this.storage.equals(compareML.getStorage()))) {
			return false;
		}
		return true;
	}
}
