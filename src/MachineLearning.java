import Metrics.GenericMetric;
import java.util.*;

public class MachineLearning {
	String problem;
	Storage storage;
	TreeMap<String, Integer> valuesTree;
	char keyChar;
	
	public MachineLearning(String problem) {
		this.problem = problem;
		storage = new Storage();
		valuesTree = new TreeMap<>();
		
		if (problem.equals("house"))
			keyChar = 'h';
		else
			keyChar = 'a';
	}

	public void Learn(GenericMetric[] metrics){
		//storage.insert(metrics);
		int i = 0;
		for (GenericMetric m: metrics) {
			i++;
			storage.insert(keyChar+i,m);
		}
		
		/*
		 * Had to remove the boolean value since insert() doesn't return anything
		 * at this point in time.
		 * 
		 * return false;
		 * 
		 */
	}
	
	public int Predict(GenericMetric[] metrics, int k){
		int predictedValue = 0;
		HashMap<String, GenericMetric[]> learnedInfo;
		
		learnedInfo = storage.getLearned();		
		
		
		return predictedValue;
	}

	public void PredictLearn(GenericMetric[] metrics, int k){
		this.Predict(metrics, k);
		this.Learn(metrics);
	}
	
	public void PredictError(String problem, GenericMetric[] metrics, int k){
		
	}
	
	public static void main (String[] args) {
		
	}
}
