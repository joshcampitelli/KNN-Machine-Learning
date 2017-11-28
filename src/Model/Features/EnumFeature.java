package Model.Features;

import Model.Metrics.GenericMetric;

public class EnumFeature extends GenericFeature {
	
	/* This constructor requires two string values the name of the feature, and the discrete value.
	 * 
	 * @author Logan MacGillivray, Ethan Morrill
	 */
	public EnumFeature(String name, String value, GenericMetric metric){
		super(name, value, metric);
	}

	public String toString() {
		return name + " (Discrete): Value = " + value;
	}
}
