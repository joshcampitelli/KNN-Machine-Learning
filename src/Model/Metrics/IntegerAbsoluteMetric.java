package Model.Metrics;

import Model.Features.*;
import Model.Storage.*;

public class IntegerAbsoluteMetric implements GenericMetric {

    private String featureName;

    /* Old metric class placed here temporarily, will function differently post design meeting
     */

    /* This constructor requires a double or no input.  The value becomes the only
     * property to this metric, to determine the weightage of this distance metric.
     * By default each metric will have equal weight of 1
     * @author Logan MacGillivray, Ethan Morrill
     */
    public IntegerAbsoluteMetric(String name){

        featureName = name;

    }

    /* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
     * This particular function will return the (positive) difference between two
     * integers.
     *
     * @author Logan MacGillivray, Ethan Morrill
     */
    public int getDistance(GenericFeature feature,GenericFeature learnedFeature){
        if((feature instanceof IntegerFeature)&&(learnedFeature instanceof IntegerFeature)){
            return Math.abs((int)learnedFeature.getValue() - (int)feature.getValue());
        }
        return -1;
    }
}