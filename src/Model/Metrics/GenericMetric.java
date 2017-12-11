package Model.Metrics;
import Model.Storage;
/* @author Logan MacGillivray
 */
import Model.Features.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public abstract class GenericMetric implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String featureName;
	protected Storage storage;
	private boolean predictable;
	protected HashMap<String, Integer> permittedValues;
	protected HashMap<String, Double> distances;
	protected HashMap<String, GenericFeature> learnedFeature;
	protected Set<String> keys;
	
	public GenericMetric(String name, Storage storage) {
		this.featureName = name;
		this.storage = storage;
		predictable = false;
		permittedValues = null;
		distances = null;
		learnedFeature = null;
		keys = null;
		
	}
	
	/* This function will return a HashMap of the learnedInstance keys and positive doubles as a difference
	 * between two features.  It will also return null when the feature passed
	 * does not match the current feature.  A feature parameter is required.
	 * 
	*/
	public HashMap<String, Double> getDistance(GenericFeature feature){

		distances = new HashMap<>();
		learnedFeature = storage.getFeature(featureName);
		keys = learnedFeature.keySet();

		return distances;
	}
	/* This function will return a HashMap of the learnedInternalfeatures keys and positive double values of the difference
	 * between the leaned internalFeature and the internalFeature of the desired Complex feature. Returns null if the
	 * feature is of the wrong type
	 *
	 * @author Ethan Morrill
	 */
	public abstract HashMap<String, Double> getInternalDistance(GenericFeature feature, HashMap<String,GenericFeature> internalLearnedFeature);

	/* This function returns the feature name that the metric is afiliated with for viewing
	 *
	 * @author Logan Macgllvray
	 */
    public String getName(){
    	return featureName;
    }

    public void setPredictable() {
    	predictable = true;
    }
    
    public boolean isPredictable() {
    	return predictable;
    }
    
    public HashMap<String, Integer> getPermittedValues() {
    	return permittedValues;
    }
    
    public void setPermittedValues(HashMap<String, Integer> permitVal) {
    	this.permittedValues = permitVal;
    }
}
