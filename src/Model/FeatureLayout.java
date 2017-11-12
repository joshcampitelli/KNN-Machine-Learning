package Model;

import Model.Metrics.GenericMetric;

public class FeatureLayout {
    private String name;
    private GenericMetric featureType;
    //Another variable for type of metric to use

    public FeatureLayout(String name, GenericMetric featureType) {
        this.name = name;
        this.featureType = featureType;
    }

    public String getName() {
        return name;
    }

    public GenericMetric getFeatureType() {
        return featureType;
    }
}
