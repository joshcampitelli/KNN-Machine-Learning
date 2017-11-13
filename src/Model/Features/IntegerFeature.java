package Model.Features;

public class IntegerFeature implements GenericFeature {
	private int value;
	private String name;

	/* This constructor requires an integer.  The value becomes the only
	 * property to this metric, as the metric is used for size/volume metrics.
	 * 
	 * @author Logan MacGillivray
	 */
	public IntegerFeature(String name, int val){
		value = val;
		this.name = name;
	}

	/* See GenericMetrics.getValue() for full java doc
	 * This function returns a positive integer
	 * 
	 * @author Logan MacGillivray
	 */
	public Object getValue(){
		return value;
	}

	public String getName() { return name; }
}
