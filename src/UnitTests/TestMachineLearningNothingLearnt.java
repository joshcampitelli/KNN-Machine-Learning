package UnitTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Model.FeatureLayout;
import Model.MachineLearning;
import Model.Features.CartesianFeature;
import Model.Metrics.CartesianEuclideanMetric;

public class TestMachineLearningNothingLearnt {
	private String problem = null;
	private MachineLearning housing = null;
	
	@Before
	public void setup(){
		problem = "Housing";
		housing = new MachineLearning(problem);
	}
	
	@Test
	public void testGetProblem(){
		assertEquals("Problem should return 'Housing'", problem, housing.getProblem());
	}
	
	@Test
	public void testAddFeatureLayout() {
		FeatureLayout feat = new FeatureLayout("",FeatureLayout.FeatureType.CartesianFeature,
				new CartesianEuclideanMetric("",housing.getStorage()));
		housing.addFeatureLayout("", FeaturLayout);
	}
}
