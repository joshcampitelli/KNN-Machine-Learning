package Model;

import Model.Metrics.*;

public class FeatureLayout {
    public enum FeatureType {
        IntegerFeature,
        CartesianFeature,
        DiscreteFeature
    }

    private String name;
    private FeatureType featureType;
    private String[] discreteValues;
    //Another variable for type of metric to use
    private GenericMetric distanceMetric;


    public FeatureLayout(GenericMetric metric) {
        this.name = metric.getName();
        if(metric instanceof CartesianEuclideanMetric){
        	this.featureType = FeatureType.CartesianFeature;
        } else if(metric instanceof DiscreteBinaryMetric){
        	this.featureType = FeatureType.DiscreteFeature;
        	setDiscreteValues(((DiscreteBinaryMetric) metric).getPermited());
        } else if(metric instanceof IntegerAbsoluteMetric) {
        	this.featureType = FeatureType.IntegerFeature;
        }
        distanceMetric = metric;
    }

    public String getName() {
        return name;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }
    
    public void setDiscreteValues(String str[]){
    	discreteValues = str;
    }
    
    public String[] getDiscreteValues(){
    	return discreteValues;
    }

    public GenericMetric getMetric(){
        return distanceMetric;
    }
}
