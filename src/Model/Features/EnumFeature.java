package Model.Features;

public class EnumFeature implements GenericFeature {

	private String name;
	private String value;


	/* This constructor requires two string values the name of the feature, and the discrete value.
	 * 
	 * @author Logan MacGillivray, Ethan Morrill
	 */
	public EnumFeature(String name, String value){

		this.name = name;
		this.value = value;
	}

	/* See GenericFeature.getValue() for full java doc
	 * This function returns the enum of the metric for viewing
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
		return name + " (Discrete): Value = " + value;
	}
}
