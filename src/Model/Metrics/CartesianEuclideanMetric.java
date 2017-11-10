package Model.Metrics;

import Model.Features.*;

public class CartesianEuclideanMetric implements GenericMetric {

    private String featureName;

    /* This constructor requires two integers.  The values are then stored into a
     * two element array as a x-value and a y-value.
     *
     * @author Logan MacGillivray
     */
    public CartesianEuclideanMetric(String name){

        featureName = name;

    }

    /* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
     * This particular function will return the hypotenuse of two xy-coordinates.
     * The value shall be returned as a positive integer.
     *
     * @author Logan MacGillivray
     */
    public int getDistance(GenericFeature feature, GenericFeature learnedFeature){
        if((feature instanceof CartesianFeature)&&(learnedFeature instanceof CartesianFeature)){
            int[] value = (int[])feature.getValue();
            int [] learnedValue = (int[]) learnedFeature.getValue();
            double squareSum = 0;
            for(int i = 0; i < value.length; i++){
                squareSum += Math.pow(value[i]-learnedValue[i], 2);
            }
            return (int)Math.sqrt(squareSum);
        }
        return -1;
    }


}
