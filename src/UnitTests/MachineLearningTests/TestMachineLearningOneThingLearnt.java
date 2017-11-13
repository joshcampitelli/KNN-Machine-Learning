package UnitTests.MachineLearningTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Model.FeatureLayout;
import Model.MachineLearning;
import Model.Features.*;
import Model.Metrics.CartesianEuclideanMetric;

public class TestMachineLearningOneThingLearnt {
	private String problem = null;
	private MachineLearning housing = null;
	private ArrayList<GenericFeature> featuresToLearn;
	
	@Before
	public void setUp() {
		problem = "Housing";
		housing = new MachineLearning(problem);
		featuresToLearn = new ArrayList<>();
		featuresToLearn.add(new CartesianFeature("coordinates", 12, 25));
		featuresToLearn.add(new IntegerFeature("sq. ft.", 1200));
		featuresToLearn.add(new EnumFeature("age", "1"));
		featuresToLearn.add(new IntegerFeature("price", 500000));
		housing.learn("h1", featuresToLearn);
	}
	
	@Test
	public void testGetFeatureLayout() {
		assertEquals("FeatureLayout at index 0 should be equal to 'featuresToLearn'", featuresToLearn, housing.getFeatureLayout(0));
	}
	
	
	@Test
	public void testGetFeatureLayoutList() {
		assertEquals("Size of list should be 1", 1, housing.getFeatureLayout().size());
		assertEquals("FeatureLayout at index 0 of the list should be equal to 'featuresToLearn'", featuresToLearn, housing.getFeatureLayout().get(0));
	}
}
