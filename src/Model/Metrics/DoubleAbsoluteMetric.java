package Model.Metrics;

import Model.Features.*;
import Model.Storage;
import java.util.HashMap;
import java.util.Set;

public class DoubleAbsoluteMetric extends GenericMetric {

    public DoubleAbsoluteMetric(String name, Storage storage){
    	super(name, storage);
    }

    public HashMap<String, Double> getDistance(GenericFeature feature){
        HashMap<String, Double> distances = new HashMap<>();
        if(feature instanceof DoubleFeature){
            HashMap<String, GenericFeature> learnedFeature = storage.getFeature(featureName);
            Set<String> keys = learnedFeature.keySet();
            for(String key : keys) {
                if(feature.getValue()==null || learnedFeature.get(key).getValue()==null){
                    distances.put(key,0.0);
                }
                else{
                    double distance = Math.abs((double)feature.getValue() - (double)learnedFeature.get(key).getValue());
                    distances.put(key, distance);
                }
            }
            return distances;

        }
        return null;
    }

    /* See GenericMetric.getName() for full java doc
	 * This function returns the feature name that the metric is afiliated with for viewing
	 *
	 * @author Logan Macgllvray
	 */
    public String getName(){
        return featureName;
    }
}
