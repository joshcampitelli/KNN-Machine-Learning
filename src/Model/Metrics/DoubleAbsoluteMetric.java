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
            super.getDistance(feature);
            for(String key : keys) {

                double distance = Math.abs((double)feature.getValue() - (double)learnedFeature.get(key).getValue());
                distances.put(key, distance);

            }
            return distances;

        }
        return null;
    }

    public HashMap<String, Double> getInternalDistance(GenericFeature feature, HashMap<String,GenericFeature> internalLearnedFeature){

        if((feature instanceof DoubleFeature)){
            HashMap<String, Double> internalDistances = new HashMap<>();
            Set<String> internalKeys = internalLearnedFeature.keySet();
            for(String key : internalKeys){
                double distance = Math.abs((double)internalLearnedFeature.get(key).getValue() - (double)feature.getValue());
                internalDistances.put(key, distance);
            }
            return internalDistances;
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
