package Model.Metrics;

import Model.Features.*;
import Model.Storage;
import java.util.HashMap;
import java.util.Set;

public class IntegerAbsoluteMetric extends GenericMetric {

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
        HashMap<String, Double> distances = new HashMap<>();
        if((feature instanceof IntegerFeature)){
            HashMap<String, GenericFeature> learnedFeature = storage.getFeature(featureName);
            Set<String> keys = learnedFeature.keySet();
            for(String key : keys) {
                if(feature.getValue()==null || learnedFeature.get(key).getValue()==null){
                    distances.put(key,0.0);
                }
                else{
                    double distance = Math.abs((int)learnedFeature.get(key).getValue() - (int)feature.getValue());

                    distances.put(key, distance);
                }
            }
            return distances;
        }
        return null;
    }
}
