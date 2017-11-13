package Model.Metrics;

import Model.Features.*;
import Model.Storage;

import java.util.HashMap;
import java.util.Set;

public class CartesianEuclideanMetric implements GenericMetric {

    private String featureName;
    private Storage storage;

    /* This constructor requires two integers.  The values are then stored into a
     * two element array as a x-value and a y-value.
     *
     * @author Logan MacGillivray
     */
    public CartesianEuclideanMetric(String name, Storage storage){

        featureName = name;
        this.storage = storage;


    }

    /* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
     * This particular function will return the hypotenuse of two xy-coordinates.
     * The value shall be returned as a positive integer.
     *
     * @author Logan MacGillivray
     */
    public HashMap<String, Integer> getDistance(GenericFeature feature){
        HashMap<String, Integer> distances = new HashMap<>();
        int[] value = (int[])feature.getValue();
        if(feature instanceof CartesianFeature){
            HashMap<String, GenericFeature> learnedFeature = storage.getFeature(featureName);
            Set<String> keys = learnedFeature.keySet();
            for(String key : keys) {
                int [] learnedValue = (int[]) learnedFeature.get(key).getValue();
                double squareSum = 0;
                for(int i = 0; i < value.length; i++){
                    squareSum += Math.pow(value[i]-learnedValue[i], 2);
                }
                distances.put(key, (int)Math.sqrt(squareSum));

            }
            return distances;
        }
        return null;
    }
    
    public String getName(){
    	return featureName;
    }


}
