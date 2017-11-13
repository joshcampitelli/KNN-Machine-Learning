package Model;

import Model.Metrics.GenericMetric;

public class FeatureLayout {
    public enum FeatureType {
        IntegerFeature,
        CartesianFeature,
        DiscreteFeature
    }

    private String name;
    private FeatureType featureType;
    private GenericMetric distanceMetric;

    public FeatureLayout(String name, FeatureType featureType, GenericMetric metric) {
        this.name = name;
        this.featureType = featureType;
        distanceMetric = metric;
    }

    public String getName() {
        return name;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public GenericMetric getMetric(){
        return distanceMetric;
    }
}
