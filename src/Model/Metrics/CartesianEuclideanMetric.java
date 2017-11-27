package Model.Metrics;

import Model.Features.*;
import Model.Storage;

import java.util.HashMap;
import java.util.Set;

public class CartesianEuclideanMetric extends GenericMetric {

    /* This constructor requires two integers and a reference to the storage for the problem.
     * The values are then stored into a two element array as a x-value and a y-value.
     * With a reference to the storage stored locally.
     *
     * @author Logan MacGillivray, Ethan Morrill
     */
    public CartesianEuclideanMetric(String name, Storage storage){
    	super(name, storage);
    }

    /* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
     * This particular function will return a hashmap of the example key and euclidean distance
     * of the provided feature and each learned example.
     * The value shall be returned as a Hashmap of {key, positive integer distance}.
     * returns null if provided feature is of the wrong type.
     *
     * @author Logan MacGillivray, Ethan Morrill
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
}
