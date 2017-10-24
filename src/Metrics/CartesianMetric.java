package Metrics;

public class CartesianMetric implements GenericMetric{
	private int[] coordinates = new int[2];
	
	public CartesianMetric(int x, int y){
		coordinates[0] = x;
		coordinates[1] = y;
	}
	
	public int getDifference(GenericMetric metric){
		if(metric instanceof CartesianMetric){
			
		}
		return 1;
	}
	
	public Object getValue(){
		return coordinates;
	}
}
