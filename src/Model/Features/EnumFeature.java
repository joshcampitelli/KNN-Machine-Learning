package Model.Features;

import Model.Metrics.GenericMetric;

public class EnumFeature implements GenericFeature {
	private enum age {NEW, OLD};	// Looking to remove this as it is a magic value
	private age value;
	private String name;


	/* This constructor requires a string value that must match one of the enum values.
	 * If the values do not match, no value is assigned to this metric and an error
	 * message is written to console.
	 * 
	 * @author Logan MacGillivray
	 */
	public EnumFeature(String val){
		try{
			value = age.valueOf(val.toUpperCase());
		} catch(IllegalArgumentException e){
			System.out.println("ERROR - Value not assigned.  Paramenter not accepted");
		}
	}

	/* See GenericMetrics.getValue() for full java doc
	 * This function returns the enum of the metric for viewing
	 * 
	 * @author Logan MacGillivray
	 */
	public Object getValue(){
		return value;
	}
}
