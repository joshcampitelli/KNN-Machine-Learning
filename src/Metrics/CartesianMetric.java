package Metrics;

public class CartesianMetric implements GenericMetric{
	private int[] coordinates = new int[2];
	
	public CartesianMetric(int x, int y){
		coordinates[0] = x;
		coordinates[1] = y;
	}
	
	public int getDifference(GenericMetric metric){
		if(metric instanceof CartesianMetric){
			int[] value = (int[])metric.getValue();
			double squareSum = 0;
			for(int i = 0; i < value.length; i++){
				squareSum += Math.pow(value[i]-coordinates[i], 2);
			}
			return (int)Math.sqrt(squareSum);
		}
		return -1;
	}
	
	public Object getValue(){
		return coordinates;
	}
}
