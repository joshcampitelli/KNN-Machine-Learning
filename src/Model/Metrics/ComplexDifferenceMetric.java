package Model.Metrics;

import Model.Features.*;
import Model.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ComplexDifferenceMetric extends GenericMetric {

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

     //Has temporary getMetric until implementation is known
    public HashMap<String, Double> getDistance(GenericFeature feature) {
        HashMap<String, Double> distances = new HashMap<>();
        if(feature instanceof ComplexFeature){
            ArrayList<GenericFeature> internalFeatures =  (ArrayList<GenericFeature>)feature.getValue();
            for(GenericFeature internalFeature: internalFeatures){
                HashMap<String, Double> featureDistances = internalFeature.getMetric().getDistance(internalFeature);
                for (String key : featureDistances.keySet()) {
                    if (distances.containsKey(key)) {
                        distances.put(key, featureDistances.get(key) + distances.get(key));
                    } else {
                        distances.put(key, featureDistances.get(key));
                    }
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
