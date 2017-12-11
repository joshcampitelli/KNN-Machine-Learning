package Model.Metrics;

import Model.Features.*;
import Model.Storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ComplexDifferenceMetric extends GenericMetric implements Serializable {

	private static final long serialVersionUID = 1L;

	public ComplexDifferenceMetric(String name, Storage storage){
    	super(name, storage);
    }

     /* See GenericMetrics.getDifference(GenericFeature feature) for full java doc
     * This particular function will return a hashmap of the example key and absolute distance of all internal features
     * of the provided feature and each learned example.
     * The value shall be returned as a Hashmap of {key, positive double distance}.
     * returns null if provided feature is of the wrong type.
     *
     * @author Ethan Morrill
     */

    public HashMap<String, Double> getDistance(GenericFeature feature) {

        if(feature instanceof ComplexFeature){
            super.getDistance(feature);
            ArrayList<String> internalFeatureNames = new ArrayList<>();
            for(String key: keys){
                ArrayList<GenericFeature> internalFeatures = (ArrayList)learnedFeature.get(key).getValue();
                for(GenericFeature internalFeature: internalFeatures) {

                    internalFeatureNames.add(internalFeature.getName());

                }
                break;
            }

            for(String internalFeatName: internalFeatureNames){

                HashMap<String, GenericFeature> internalFeature = storage.getInternalFeature(featureName, internalFeatName);
                for(String key: keys) {

                    HashMap<String, Double> internalDistances = internalFeature.get(key).getMetric().getInternalDistance(((ComplexFeature) feature).getInteralFeature(internalFeatName), internalFeature);
                    if (distances.containsKey(key)) {
                        distances.put(key, internalDistances.get(key) + distances.get(key));
                    }
                    else {
                        distances.put(key, internalDistances.get(key));
                    }
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
