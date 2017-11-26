package Model.Metrics;
/* @author Logan MacGillivray
 */
import Model.Features.*;

import java.util.HashMap;

public interface GenericMetric {

	/* This function will return a positive whole number as a difference
	 * between two metrics.  It will also return -1 when the metric passed 
	 * does not match the current metric.  A metric parameter is required.
	 * 
	 */
	public HashMap<String, Double> getDistance(GenericFeature feature);


	/* Funct will return the name of the feature which the metric is related to.
	 * No Parameters required.
	 */
	public String getName();
}
