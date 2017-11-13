package Model.Features;

public interface GenericFeature {

    /* This function will return the value stored in each metric.  This
	 * function should be called by the getDifference function when comparing
	 * two metrics.  No paramters required.
	 */
    public Object getValue();

    /* This function will return the name of the feature stored in each feature.  This
	 * function should be called by the getFeature function when retrieving specific values of a feature.
	 * No paramters required.
	 */
    public String getName();
}
