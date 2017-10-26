package Metrics;

public class IntegerMetric implements GenericMetric{
	private int value;
	
	public IntegerMetric(int val){
		value = val;
	}
	
	public int getDifference(GenericMetric metric){
		if(metric instanceof IntegerMetric){	
			// Returns the absolute difference between the two ints
			return Math.abs(value - (int)metric.getValue());
		}
		
		// Return 1 if the object passed is 
		// of a different type
		return -1;
	}
	
	public Object getValue(){
		return value;
	}
}
