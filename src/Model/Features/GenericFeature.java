package Model.Features;

public interface GenericFeature {

    /* This function will return the value stored in each metric.  This
	 * function should be called by the getDifference function when comparing
	 * two metrics.  No paramters required.
	 */
    public Object getValue();
}
