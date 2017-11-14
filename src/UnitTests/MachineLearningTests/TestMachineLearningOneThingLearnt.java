package UnitTests.MachineLearningTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Model.FeatureLayout;
import Model.MachineLearning;
import Model.Storage;
import Model.Features.*;
import Model.Metrics.CartesianEuclideanMetric;

/**
 * 
 * @author Ryan Ribeiro
 */
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
	public void testGetProblem() {
		assertEquals("Problem should be 'Housing'", problem, housing.getProblem());
	}
	
	@Test
	public void testGetStorage() {
		Storage storage = new Storage();
		storage.insert("h1", featuresToLearn);
		assertEquals("Returned storage should be equal to locally declared storage", storage, housing.getStorage());
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
	
	@Test
	public void testDeleteLearned() {
		housing.deleteLearned("h1");
		assertEquals("Size of storage should be 0 if the entry 'h1"
				+ " is deleted", 0, housing.getStorage().getSize());
	}
	
	@Test
	public void testPredict() {
		ArrayList<GenericFeature> predictFeatures = new ArrayList<>();
		predictFeatures.add(new CartesianFeature("coordinates", 15, 25));
		predictFeatures.add(new IntegerFeature("sq. ft.", 1000));
		predictFeatures.add(new EnumFeature("age", "1"));
		
		assertEquals("Predited value should be 500000", 500000, housing.predict(1, predictFeatures));
	}
	
	@Test
	public void testPredictError() {
		ArrayList<GenericFeature> predictFeatures = new ArrayList<>();
		predictFeatures.add(new CartesianFeature("coordinates", 15, 25));
		predictFeatures.add(new IntegerFeature("sq. ft.", 1000));
		predictFeatures.add(new EnumFeature("age", "1"));
		predictFeatures.add(new IntegerFeature("price", 300000));
		
		assertEquals("PreditedError value should be 200000", 200000, housing.predictError(1, predictFeatures));
	}
}
