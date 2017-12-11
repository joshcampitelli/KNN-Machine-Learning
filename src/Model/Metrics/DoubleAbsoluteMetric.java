package Model.Metrics;

import Model.Features.*;
import Model.Storage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class DoubleAbsoluteMetric extends GenericMetric implements Serializable {

	private static final long serialVersionUID = 1L;

	public DoubleAbsoluteMetric(String name, Storage storage){
    	super(name, storage);
    }

    public HashMap<String, Double> getDistance(GenericFeature feature){
        if(feature instanceof DoubleFeature){
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

    public HashMap<String, Double> getInternalDistance(GenericFeature feature, HashMap<String,GenericFeature> internalLearnedFeature){
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
