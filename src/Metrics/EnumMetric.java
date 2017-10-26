package Metrics;

public class EnumMetric implements GenericMetric{
	private enum age {NEW, OLD};	// Looking to remove this as it is a magic value
	private age value;
	
	/* This constructor requires a string value that must match one of the enum values.
	 * If the values do not match, no value is assigned to this metric and an error
	 * message is written to console.
	 */
	public EnumMetric(String val){
		try{
			value = age.valueOf(val.toUpperCase());
		} catch(IllegalArgumentException e){
			System.out.println("ERROR - Value not assigned.  Paramenter not accepted");
		}
	}
	
	/* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
	 * This particular function will check if two enums are equal.  If they are,
	 * this function returns an int of 1; however, the function returns an int of
	 * 0 if they are not.
	 */
	public int getDistance(GenericMetric metric){
		if(metric instanceof EnumMetric){
			if(value.equals(metric.getValue())){
				return 1;
			}
			return 0;
		}
		return -1;
	}
	
	/* See GenericMetrics.getValue() for full java doc
	 * This function returns the enum of the metric for viewing
	 */
	public Object getValue(){
		return value;
	}
}
