package Model.Metrics;

import Model.Features.*;
import Model.Storage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class IntegerAbsoluteMetric extends GenericMetric implements Serializable {

	private static final long serialVersionUID = 1L;
    /* Old metric class placed here temporarily, will function differently post design meeting
     */

	/* This constructor requires a double or no input.  The value becomes the only
     * property to this metric, to determine the weightage of this distance metric.
     * By default each metric will have equal weight of 1
     * @author Logan MacGillivray, Ethan Morrill
     */
    public IntegerAbsoluteMetric(String name, Storage storage){
    	super(name,storage);

    }

    /* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
     * This particular function will return the (positive) difference between two
     * integers.
     *
     * @author Logan MacGillivray, Ethan Morrill
     */
    public HashMap<String, Double> getDistance(GenericFeature feature){
        if((feature instanceof IntegerFeature)){
            super.getDistance(feature);
            for(String key : keys) {

                double distance = Math.abs((int)learnedFeature.get(key).getValue() - (int)feature.getValue());
                distances.put(key, distance);

            }
            return distances;
        }
        return null;
    }

    public HashMap<String, Double> getInternalDistance(GenericFeature feature, HashMap<String,GenericFeature> internalLearnedFeature){

        if((feature instanceof IntegerFeature)){
            HashMap<String, Double> internalDistances = new HashMap<>();
            Set<String> internalKeys = internalLearnedFeature.keySet();
            for(String key : internalKeys){
                double distance = Math.abs((int)internalLearnedFeature.get(key).getValue() - (int)feature.getValue());

                internalDistances.put(key, distance);
            }
            return internalDistances;
        }
        return null;
    }
}
