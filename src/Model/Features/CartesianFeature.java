package Model.Features;

public class CartesianFeature implements GenericFeature {
	private int[] coordinates = new int[2];
	private String name;

	/* This constructor requires a String and two integers.  The values are then stored into a
	 * two element array as a x-value and a y-value, with the String being the feature name stored locally.
	 * 
	 * @author Logan MacGillivray, Ethan Morrll
	 */
	public CartesianFeature(String name, int x, int y){
		coordinates[0] = x;
		coordinates[1] = y;
		this.name = name;
	}

	/* See GenericFeature.getValue() for full java doc
	 * This function returns an array stored in this feature
	 * 
	 * @author Logan MacGillivray
	 */
	public Object getValue(){
		return coordinates;
	}

	/* See GenericFeature.getName() for full java doc
	 * Function returns the name field of the feature
	 * @author Ethan Morrill
	 */
	public String getName() { return name; }
}
