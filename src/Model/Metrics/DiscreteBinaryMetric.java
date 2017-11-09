package Model.Metrics;

import Model.Features.*;

public class DiscreteBinaryMetric implements GenericMetric {

    private enum age {NEW, OLD};	// Looking to remove this as it is a magic value
    private DiscreteBinaryMetric.age value;

    /* This constructor requires a string value that must match one of the enum values.
     * If the values do not match, no value is assigned to this metric and an error
     * message is written to console.
     *
     * @author Logan MacGillivray
     */
    public DiscreteBinaryMetric(String val){
        try{
            value = DiscreteBinaryMetric.age.valueOf(val.toUpperCase());
        } catch(IllegalArgumentException e){
            System.out.println("ERROR - Value not assigned.  Paramenter not accepted");
        }
    }

    /* See GenericMetrics.getDifference(GenericMetric metric) for full java doc
     * This particular function will check if two enums are equal.  If they are,
     * this function returns an int of 1; however, the function returns an int of
     * 0 if they are not.
     *
     * @author Logan MacGillivray
     */
    public int getDistance(GenericFeature feature){
        if(feature instanceof EnumFeature){
            if(value.equals(feature.getValue())){
                return 1;
            }
            return 0;
        }
        return -1;
    }

    /* See GenericMetrics.getValue() for full java doc
     * This function returns the enum of the metric for viewing
     *
     * @author Logan MacGillivray
     */
    public Object getValue(){
        return value;
    }
}
