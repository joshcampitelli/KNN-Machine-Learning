package Model.Features;

public class IntegerFeature extends GenericFeature {
	
	/* This constructor requires an integer.  The value becomes the only
	 * property to this metric, as the metric is used for size/volume metrics.
	 * 
	 * @author Logan MacGillivray
	 */
	public IntegerFeature(String name, int value){
		super(name, value);
	}

	public String toString() {
		return name + " (Integer): Value = " + value;
	}
}
