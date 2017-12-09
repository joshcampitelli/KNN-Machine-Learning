package Model.Features;

import java.io.Serializable;

import Model.Metrics.GenericMetric;

public class DoubleFeature extends GenericFeature implements Serializable {

    /* This constructor requires an integer.  The value becomes the only
	 * property to this metric, as the metric is used for size/volume metrics.
	 *
	 * @author Ethan Morrill
	 */
    public DoubleFeature(String name, double value, GenericMetric metric){
        super(name, value, metric);
    }

    /* This function will return the name of the feature stored in each feature.  This
	 * function should be called by the getFeature function when retrieving specific values of a feature.
	 * No paramters required.
	 */
    public String getName() {
        return name;
    }

    public String toString() {
        return name + " (Double): Value = " + value;
    }
}
