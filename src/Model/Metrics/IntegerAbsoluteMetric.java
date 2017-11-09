package Model.Metrics;

import Model.Features.*;


public class IntegerAbsoluteMetric implements GenericMetric {
    private int value;

    /* Old metric class placed here temporarily, will function differently post design meeting
     */

    /* This constructor requires an integer.  The value becomes the only
     * property to this metric, as the metric is used for size/volume metrics.
     *
     * @author Logan MacGillivray
     */
    public IntegerAbsoluteMetric(int val){
        value = val;
    }

    /* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
     * This particular function will return the (positive) difference between two
     * integers.
     *
     * @author Logan MacGillivray
     */
    public int getDistance(GenericFeature feature){
        if(feature instanceof IntegerFeature){
            return Math.abs(value - (int)feature.getValue());
        }
        return -1;
    }

    /* See GenericMetrics.getValue() for full java doc
     * This function returns a positive integer
     *
     * @author Logan MacGillivray
     */
    public Object getValue(){
        return value;
    }
}
