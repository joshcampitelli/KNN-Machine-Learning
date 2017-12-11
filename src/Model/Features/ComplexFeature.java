package Model.Features;

import java.io.Serializable;
import java.util.ArrayList;

import Model.Metrics.GenericMetric;

public class ComplexFeature extends GenericFeature implements Serializable {

	private static final long serialVersionUID = 1L;

	/** @author Logan
	 *  Construct a ComplexFeature made of smaller GenericFeatures
	 *  
	 *  @param name of the feature (String), other features that make up this one (ArrayList)
	 */
	public ComplexFeature(String name, ArrayList<GenericFeature> feature, GenericMetric metric) {
		super(name, feature, metric);
	}

	public GenericFeature getInteralFeature(String featureName){
		ArrayList<GenericFeature> internalFeatures = (ArrayList)this.getValue();
		for(GenericFeature feature: internalFeatures){
			if(feature.getName().equals(featureName)){
				return feature;
			}
		}
		return null;
	}

	/** @author Logan
	 *  This function appends another feature to the ComplexFeature
	 *  
	 *  @param feature you would like to add (GenericFeature)
	 */
	@SuppressWarnings("unchecked")
	public void append(GenericFeature feature) {
		((ArrayList<GenericFeature>)value).add(feature);
	}

	/** @author Logan
	 *  This function writes out the ComplexFeature into a String
	 *  
	 *  @param
	 */
	@SuppressWarnings("unchecked")
	public String toString(){
		String rtn = name + " features:\n";
		ArrayList<GenericFeature> features = (ArrayList<GenericFeature>)value;
		for(int i = 0; i < features.size(); i++) {
			rtn += features.get(i).toString() + " ";
		}
		return rtn;
	}
	
}
