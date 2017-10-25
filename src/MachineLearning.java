import Metrics.*;
import java.util.*;


public class MachineLearning {
	String problem;
	Storage storage;
	TreeMap<String, Integer> valuesTree;
	
	public MachineLearning(String problem) {
		this.problem = problem;
		storage = new Storage();
		valuesTree = new TreeMap<>();
	}

	public void Learn(String key, GenericMetric... metrics) {
		storage.insert(key, metrics);
		
		/*
		 * Had to remove the boolean value since insert() doesn't return anything
		 * at this point in time.
		 * 
		 * return false;
		 * 
		 */
	}
	
	public int Predict(int k, String givenKey, GenericMetric... metrics) {
		int predictedValue = 0, totalDistance = 0, i = 0, j = 0;
		String newKey;
		HashMap<String, GenericMetric[]> learnedInfo;
		
		learnedInfo = storage.getLearned();
		
		for (Map.Entry<String, GenericMetric[]> entry : learnedInfo.entrySet()) {
			String existingKey = entry.getKey();
			GenericMetric[] value = entry.getValue();
			for (j = 0; j < value.length(); )
			
			
			for (GenericMetric v : value) {
				totalDistance = totalDistance + v.getDistance(metrics[i]);				
				i++;
			}
			
			newKey = givenKey + existingKey;
			
			//Tree isn't going to work, needs to be a hashmap.
			//valuesTree.put(newKey, totalDifference);			
			i = 0;
		}
		
		
		/*
		for (GenericMetric m : metrics) {
			tempDifference = m.getDifference(metric);
			totalDifference = totalDifference + tempDifference;
		}
		*/
		
		
		return predictedValue;
	}

	public void PredictLearn(int k, String key, GenericMetric... metrics) {
		this.Predict(k, key, metrics);
		this.Learn(key, metrics);
	}
	
	public void PredictError(String problem, int k, GenericMetric... metrics) {
		
	}
	
	public static void main (String[] args) {
		MachineLearning newML = new MachineLearning("house");
		
		CartesianMetric CM = new CartesianMetric(10,20);
		IntegerMetric IM = new IntegerMetric(1000);
		
		newML.Learn("h1", CM,IM);

		System.out.println("Finished");
	}
}
