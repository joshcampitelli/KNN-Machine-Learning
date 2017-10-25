package Metrics;

public interface GenericMetric {
	
	/* This function will return a positive whole number as a difference
	 * between two metrics.  It will also return -1 when the metric passed 
	 * does not match the current metric.  A metric parameter is required.
	 * 
	 */
	public int getDistance(GenericMetric metric);
	
	/* This function will return the value stored in each metric.  This 
	 * function should be called by the getDifference function when comparing
	 * two metrics.  No paramters required.
	 */
	public Object getValue();
}
