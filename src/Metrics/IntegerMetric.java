package Metrics;

public class IntegerMetric implements GenericMetric{
	private int value;
	
	/* This constructor requires an integer.  The value becomes the only
	 * property to this metric, as the metric is used for size/volume metrics.
	 */
	public IntegerMetric(int val){
		value = val;
	}
	
	/* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
	 * This particular function will return the (positive) difference between two
	 * integers.
	 */
	public int getDistance(GenericMetric metric){
		if(metric instanceof IntegerMetric){
			return Math.abs(value - (int)metric.getValue());
		}
		return -1;
	}
	
	/* See GenericMetrics.getValue() for full java doc
	 * This function returns a positive integer
	 */
	public Object getValue(){
		return value;
	}
}
