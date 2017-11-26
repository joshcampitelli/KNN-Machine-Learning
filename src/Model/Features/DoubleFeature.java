package Model.Features;

public class DoubleFeature extends GenericFeature {

    /* This constructor requires an integer.  The value becomes the only
	 * property to this metric, as the metric is used for size/volume metrics.
	 *
	 * @author Ethan Morrill
	 */
    public DoubleFeature(String name, double value){
        super(name, value);
    }

    /* This function will return the name of the feature stored in each feature.  This
	 * function should be called by the getFeature function when retrieving specific values of a feature.
	 * No paramters required.
	 */
    public String getName() {
        return name;
    }
}
