package Metrics;

public interface GenericMetric {
	
	
	public Object getValue();
	
	public int getDifference(GenericMetric metric);
}
