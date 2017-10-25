import Metrics.*;
import java.util.*;


public class MachineLearning {
	String problem;
	Storage storage;
	Map<String, Integer> valuesMap;
	
	public MachineLearning(String problem) {
		this.problem = problem;
		storage = new Storage();
		valuesMap = new HashMap<>();
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
			for (j = 0; j < value.length - 1; j++) {
				totalDistance = totalDistance + value[j].getDistance(metrics[i]);
				i++;
			}
			newKey = givenKey + existingKey;
			
			valuesMap.put(newKey, totalDistance);			
			i = 0;
		}
		
		//Sort hashmap to find smalled distances
		
		return predictedValue;
	}

	public void PredictLearn(int k, String key, GenericMetric... metrics) {
		this.Predict(k, key, metrics);
		this.Learn(key, metrics);
	}
	
	public int PredictError(String problem, int k, String key,  GenericMetric... metrics) {
		int expectedValue = (int) metrics[metrics.length - 1].getValue();
		int predictedValue = this.Predict(k, key, metrics);
		
		return Math.abs(predictedValue-expectedValue);
	}
	
	public static void main (String[] args) {
		MachineLearning newML = new MachineLearning("house");
		
		CartesianMetric CM = new CartesianMetric(10,20);
		IntegerMetric IM = new IntegerMetric(1000);
		
		newML.Learn("h1", CM,IM);

		System.out.println("Finished");
	}
}
