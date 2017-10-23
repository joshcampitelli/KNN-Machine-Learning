package Metrics;

public class IntegerMetric implements GenericMetric{
	private int value;
	
	public IntegerMetric(int val){
		value = val;
	}
	
	public int getDifference(){
		return 0;
	}
	
	public Object getValue(){
		return null;
	}
}
