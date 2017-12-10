package Model.Features;

import java.io.Serializable;

import Model.Metrics.GenericMetric;

public class IntegerFeature extends GenericFeature implements Serializable {

	private static final long serialVersionUID = 1L;

	/* This constructor requires an integer.  The value becomes the only
	 * property to this metric, as the metric is used for size/volume metrics.
	 * 
	 * @author Logan MacGillivray
	 */
	public IntegerFeature(String name, int value, GenericMetric metric){
		super(name, value, metric);
	}

	public String toString() {
		return name + " (Integer): Value = " + value;
	}
}
