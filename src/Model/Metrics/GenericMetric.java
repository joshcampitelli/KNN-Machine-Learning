package Model.Metrics;
/* @author Logan MacGillivray
 */
import Model.Features.*;
public interface GenericMetric {

	/* This function will return a positive whole number as a difference
	 * between two metrics.  It will also return -1 when the metric passed 
	 * does not match the current metric.  A metric parameter is required.
	 * 
	 */
	public int getDistance(GenericFeature feature, GenericFeature learnedFeature);

}
