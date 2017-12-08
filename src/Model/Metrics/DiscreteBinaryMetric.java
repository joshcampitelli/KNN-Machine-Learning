package Model.Metrics;

import Model.Features.*;
import Model.Storage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class DiscreteBinaryMetric extends GenericMetric implements Serializable {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Integer> permittedValues;

    /* This constructor requires a string value of the feature name, a reference to the problem storage,
     * and an array of permitted values.
     * If the values do not match, Null is returned
     *
     * @author Ethan Morrill
     */
    public DiscreteBinaryMetric(String name, Storage storage, String[] permitted){
    	super(name, storage);
        permittedValues = new HashMap<>(); 
        int i = 0;
        while(i< permitted.length ){
            permittedValues.put(permitted[i], i);
            i += 1;
        }
        super.setPermittedValues(permittedValues);
    }


    /* See GenericMetrics.getDistance(GenericMetric metric) for full java doc
     * This particular function will check if the provided string s valid, then
     * compare to each learned value of the feature. If they are equal,
     * this function returns an int of 0; however, the function returns an int of
     * 0 if they are not.
     *
     * @author Ethan Morrill
     */
    public HashMap<String, Double> getDistance(GenericFeature feature){
        HashMap<String, Double> distances = new HashMap<>();
        if(feature instanceof EnumFeature){
            HashMap<String, GenericFeature> learnedFeature = storage.getFeature(featureName);
            Set<String> keys = learnedFeature.keySet();
            for(String key : keys) {
                if(feature.getValue()==null || learnedFeature.get(key).getValue()==null){
                    distances.put(key, 0.0);
                }
                else if(permittedValues.containsKey(feature.getValue())){
                    if(permittedValues.get(learnedFeature.get(key).getValue()).equals(permittedValues.get(feature.getValue()))){
                        distances.put(key,0.0);
                    }
                    else{
                        distances.put(key ,1.0);
                    }
                }
                else{
                    return null;
                }
            }
            return distances;
        }
        return null;
    }
    
    /*
	 * This function returns the array of permitted string values for discrete features
	 *
	 * @author Logan Macgllvray
	 */
    public String[] getPermited(){
    	return permittedValues.keySet().toArray(new String[permittedValues.size()]);
    }
}
