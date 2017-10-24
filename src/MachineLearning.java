import Metrics.GenericMetric;
import java.util.*;

public class MachineLearning {
	String problemType;
	Storage houseStorage;
	TreeMap valuesTree;
	
	public MachineLearning(String problem) {
		this.problemType = problem;
		this.valuesTree = new TreeMap<String, Integer>();
		houseStorage = new Storage();
	}

	public boolean Learn(GenericMetric metrics[]){
		
		return false;
	}
	
	public int Predict(GenericMetric metrics[]){
		return 0;
	}

	public void PredictLearn(GenericMetric metrics[]){
		
	}
	
	public void PredictError(String problem, GenericMetric metrics[]){
		
	}
	
	public static void main (String[] args) {
		
	}
}
