package Model.Features;

public class EnumFeature extends GenericFeature {
	
	/* This constructor requires two string values the name of the feature, and the discrete value.
	 * 
	 * @author Logan MacGillivray, Ethan Morrill
	 */
	public EnumFeature(String name, String value){
		super(name, value);
	}

	public String toString() {
		return name + " (Discrete): Value = " + value;
	}
}
