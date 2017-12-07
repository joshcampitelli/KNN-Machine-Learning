package Model.Features;

import java.io.Serializable;

import Model.Metrics.GenericMetric;

public class GenericFeature implements Serializable {
	protected Object value;
	protected String name;
	protected GenericMetric metric;
	
	public GenericFeature(String name, Object val, GenericMetric metric) {
		this.value = val;
		this.name = name;
		this.metric = metric;
	}
	
    /* This function will return the value stored in each metric.  This
	 * function should be called by the getDifference function when comparing
	 * two metrics.  No paramters required.
	 */
    public Object getValue() {
    	return value;
    }

    /* This function will return the name of the feature stored in each feature.  This
	 * function should be called by the getFeature function when retrieving specific values of a feature.
	 * No paramters required.
	 */
    public String getName() {
    	return name;
    }
    
    public GenericMetric getMetric() {
    	return metric;
    }
    
    public boolean isPredictable() {
    	return metric.isPredictable();
    }
}
