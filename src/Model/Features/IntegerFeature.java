package Model.Features;

import Model.Metrics.GenericMetric;

public class IntegerFeature implements GenericFeature {
	private int value;
	private String name;

	/* This constructor requires an integer.  The value becomes the only
	 * property to this metric, as the metric is used for size/volume metrics.
	 * 
	 * @author Logan MacGillivray
	 */
	public IntegerFeature(int val){
		value = val;
	}

	/* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
	 * This particular function will return the (positive) difference between two
	 * integers.
	 * 
	 * @author Logan MacGillivray
	 */
	public int getDistance(GenericMetric metric){
		if(metric instanceof IntegerFeature){
			return Math.abs(value - (int)metric.getValue());
		}
		return -1;
	}

	/* See GenericMetrics.getValue() for full java doc
	 * This function returns a positive integer
	 * 
	 * @author Logan MacGillivray
	 */
	public Object getValue(){
		return value;
	}
}
