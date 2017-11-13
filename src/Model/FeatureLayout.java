package Model;

public class FeatureLayout {
    public enum FeatureType {
        IntegerFeauture,
        CartesianFeature,
        DiscreteFeature
    }

    private String name;
    private FeatureType featureType;
    private String[] discreteValues;
    //Another variable for type of metric to use

    public FeatureLayout(String name, FeatureType featureType) {
        this.name = name;
        this.featureType = featureType;
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
}
