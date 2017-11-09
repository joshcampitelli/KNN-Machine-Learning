package Model.Metrics;

import Model.Features.*;

public class CartesianEuclideanMetric implements GenericMetric {

    private int[] coordinates = new int[2];

    /* This constructor requires two integers.  The values are then stored into a
     * two element array as a x-value and a y-value.
     *
     * @author Logan MacGillivray
     */
    public CartesianEuclideanMetric(int x, int y){
        coordinates[0] = x;
        coordinates[1] = y;
    }

    /* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
     * This particular function will return the hypotenuse of two xy-coordinates.
     * The value shall be returned as a positive integer.
     *
     * @author Logan MacGillivray
     */
    public int getDistance(GenericFeature feature){
        if(feature instanceof CartesianFeature){
            int[] value = (int[])feature.getValue();
            double squareSum = 0;
            for(int i = 0; i < value.length; i++){
                squareSum += Math.pow(value[i]-coordinates[i], 2);
            }
            return (int)Math.sqrt(squareSum);
        }
        return -1;
    }

    /* See GenericMetrics.getValue() for full java doc
     * This function returns an array stored in this metric
     *
     * @author Logan MacGillivray
     */
    public Object getValue(){
        return coordinates;
    }
}
