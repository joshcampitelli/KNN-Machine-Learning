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

	/* See GenericFeature.getValue() for full java doc
	 * This function returns a positive integer
	 * 
	 * @author Logan MacGillivray
	 */
	public Object getValue(){
		return value;
	}

	/* See GenericFeature.getName() for full java doc
	 * This function returns the name of the feature for viewing
	 *
	 * @author Ethan Morrill
	 */
	public String getName() { return name; }

	public String toString() {
		return name + " (Integer): Value = " + value;
	}
}
