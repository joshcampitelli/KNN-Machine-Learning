package Metrics;

public class IntegerMetric implements GenericMetric{
	private int value;
	
	public IntegerMetric(int val){
		value = val;
	}
	
	public int getDifference(GenericMetric metric){
		if(metric instanceof IntegerMetric){	
			// Return 0 if the metric passed is of the 
			// same value as this, else return 1
			if(value == (int)metric.getValue()){
				return 0;
			}
		}
		
		// Return 1 if the object passed is 
		// of a different type
		return 1;
	}
	
	public Object getValue(){
		return value;
	}
}
