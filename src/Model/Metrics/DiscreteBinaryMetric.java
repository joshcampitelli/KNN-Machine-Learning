package Model.Metrics;

import Model.Features.*;

public class DiscreteBinaryMetric implements GenericMetric {

    private double weightage;

    /* This constructor requires a string value that must match one of the enum values.
     * If the values do not match, no value is assigned to this metric and an error
     * message is written to console.
     *
     * @author Logan MacGillivray
     */
    public DiscreteBinaryMetric(double weight){

        weightage = weight;

    }

    public DiscreteBinaryMetric(){

        weightage = 1.0;

    }

    /* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
     * This particular function will check if two enums are equal.  If they are,
     * this function returns an int of 1; however, the function returns an int of
     * 0 if they are not.
     *
     * @author Logan MacGillivray
     */
    public int getDistance(GenericFeature feature, GenericFeature learnedFeature){
        if(feature instanceof EnumFeature){
            if(learnedFeature.getValue().equals(feature.getValue())){
                return 1;
            }
            return 0;
        }
        return -1;
    }

    /* See GenericMetrics.getValue() for full java doc
     * This function returns the enum of the metric for viewing
     *
     * @author Logan MacGillivray
     */
    public Object getValue(){
        return value;
    }
}
