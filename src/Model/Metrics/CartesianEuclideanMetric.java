package Model.Metrics;

import Model.Features.*;
import Model.Storage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class CartesianEuclideanMetric extends GenericMetric implements Serializable {

	private static final long serialVersionUID = 1L;

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
    public HashMap<String, Double> getDistance(GenericFeature feature){
        int[] value = (int[])feature.getValue();
        if(feature instanceof CartesianFeature){
            super.getDistance(feature);
            for(String key : keys) {
                if(feature.getValue()==null || learnedFeature.get(key).getValue()==null){
                    distances.put(key, 0.0);
                }
                else{
                    int [] learnedValue = (int[]) learnedFeature.get(key).getValue();
                    double squareSum = 0;
                    for(int i = 0; i < value.length; i++){
                        squareSum += Math.pow(value[i]-learnedValue[i], 2);
                    }
                    distances.put(key, Math.sqrt(squareSum));
                }

            }
            return distances;
        }
        return null;
    }

    public HashMap<String, Double> getInternalDistance(GenericFeature feature, HashMap<String,GenericFeature> internalLearnedFeature){
        return null;
    }
}
