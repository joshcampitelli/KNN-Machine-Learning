package Model.Metrics;

import Model.Features.*;
import Model.Storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PolarMetric extends GenericMetric implements Serializable {

	private static final long serialVersionUID = 1L;

	public PolarMetric(String name, Storage storage) {
    	super(name, storage);
    }

    /* See GenericMetrics.getDifference(GenericFeature feature) for full java doc
     * This particular function will return a hashmap of the example key and Polar distance
     * of the provided feature and each learned example.
     * The value shall be returned as a Hashmap of {key, positive double distance}.
     * returns null if provided feature is of the wrong type.
     *
     * @author Ethan Morrill
     */

    //requires complex to be of form double distance, int angle or fails.
    @SuppressWarnings("unchecked")
    public HashMap<String, Double> getDistance(GenericFeature feature){
        if(feature instanceof ComplexFeature){
            ArrayList<GenericFeature> internalFeatures =  (ArrayList<GenericFeature>)feature.getValue();
            if(internalFeatures.get(0) instanceof DoubleFeature && internalFeatures.get(1)  instanceof IntegerFeature) {
                double featureDistance = (double) internalFeatures.get(0).getValue();
                int featureAngle = (int) internalFeatures.get(1).getValue();
                super.getDistance(feature);
                for (String key : keys) {
                    ArrayList<GenericFeature> internalLearnedFeature = (ArrayList<GenericFeature>) learnedFeature.get(key).getValue();
                    if(internalFeatures.get(0).getValue() == null || internalFeatures.get(1).getValue()==null){
                        distances.put(key,0.0);
                    }
                    else if(internalLearnedFeature.get(0).getValue()==null || internalLearnedFeature.get(1).getValue()==null){
                        distances.put(key,0.0);
                    }
                    else{
                        double learnedDistance = (double) internalLearnedFeature.get(0).getValue();
                        int learnedAngle = (int) internalLearnedFeature.get(1).getValue();
                        double distance = Math.sqrt(Math.pow(featureDistance,2)+Math.pow(learnedDistance,2)-2
                                *featureDistance*learnedDistance*(Math.cos(featureAngle-learnedAngle)));

                        distances.put(key, distance);
                    }

                }
                return distances;
            }
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
