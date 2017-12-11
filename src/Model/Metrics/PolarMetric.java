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
            if((internalFeatures.get(0) instanceof DoubleFeature && internalFeatures.get(1)  instanceof IntegerFeature)||(internalFeatures.get(1) instanceof DoubleFeature && internalFeatures.get(0)  instanceof IntegerFeature)) {
                double featureDistance = 0.0;
                int featureAngle = 0;
                HashMap<String, GenericFeature> doubleLearnedFeatures = null;
                HashMap<String, GenericFeature> integerLearnedFeatures = null;
                super.getDistance(feature);
                for(GenericFeature internalFeature: internalFeatures)
                    if( internalFeature instanceof DoubleFeature){
                        featureDistance = (double)internalFeature.getValue();
                        doubleLearnedFeatures = storage.getInternalFeature(featureName, internalFeature.getName());

                    }
                    else{
                        featureAngle = (int) internalFeature.getValue();
                        integerLearnedFeatures = storage.getInternalFeature(featureName, internalFeature.getName());
                    }

                for (String key : keys) {

                    double learnedDistance = (double) doubleLearnedFeatures.get(key).getValue();
                    int learnedAngle = (int) integerLearnedFeatures.get(key).getValue();
                    double distance = Math.sqrt(Math.pow(featureDistance,2)+Math.pow(learnedDistance,2)-2
                            *featureDistance*learnedDistance*(Math.cos(featureAngle-learnedAngle)));

                    distances.put(key, distance);

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
