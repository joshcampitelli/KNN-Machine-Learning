package Model.Features;

import Model.Metrics.GenericMetric;

public class CartesianFeature implements GenericFeature {
	private int[] coordinates = new int[2];
	private String name;

	/* This constructor requires two integers.  The values are then stored into a
	 * two element array as a x-value and a y-value.
	 * 
	 * @author Logan MacGillivray
	 */
	public CartesianFeature(int x, int y){
		coordinates[0] = x;
		coordinates[1] = y;
	}



	/* See GenericMetrics.getValue() for full java doc
	 * This function returns an array stored in this metric
	 * 
	 * @author Logan MacGillivray
	 */
	public Object getValue(){
		return coordinates;
	}
}
