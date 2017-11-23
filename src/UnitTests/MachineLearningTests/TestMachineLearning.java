package UnitTests.MachineLearningTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Model.FeatureLayout;
import Model.MachineLearning;
import Model.Storage;
import Model.Features.*;
import Model.Metrics.CartesianEuclideanMetric;
import Model.Metrics.DiscreteBinaryMetric;
import Model.Metrics.IntegerAbsoluteMetric;

public class TestMachineLearning {
	private String problem = null;
	private MachineLearning machineLearning = null;
	private ArrayList<GenericFeature> featuresToLearn1;
	private ArrayList<GenericFeature> featuresToLearn2;
	private ArrayList<GenericFeature> featuresToLearn3;
	
	@Before
	public void setup() {
		problem = "Housing";
		machineLearning = new MachineLearning(problem);
		String[] allowableDiscreteValues = {"old", "new"};
		
		machineLearning.addFeatureLayout(new CartesianEuclideanMetric("coordinates", machineLearning.getStorage()));
		machineLearning.addFeatureLayout(new IntegerAbsoluteMetric("sq. ft.", machineLearning.getStorage()));
		machineLearning.addFeatureLayout(new DiscreteBinaryMetric("coordinates", machineLearning.getStorage(), allowableDiscreteValues));
		machineLearning.addFeatureLayout(new IntegerAbsoluteMetric("price", machineLearning.getStorage()));
	
		featuresToLearn1 = new ArrayList<>();
		featuresToLearn1.add(new CartesianFeature("coordinates", 12, 25));
		featuresToLearn1.add(new IntegerFeature("sq. ft.", 1200));
		featuresToLearn1.add(new EnumFeature("age", "new"));
		featuresToLearn1.add(new IntegerFeature("price", 500000));		
		machineLearning.learn("h1", featuresToLearn1);
		
		featuresToLearn2 = new ArrayList<>();
		featuresToLearn2.add(new CartesianFeature("coordinates", 10, 50)); 
		featuresToLearn2.add(new IntegerFeature("sq. ft.", 1000));
		featuresToLearn2.add(new EnumFeature("age", "old"));
		featuresToLearn2.add(new IntegerFeature("price", 300000));
		machineLearning.learn("h2", featuresToLearn2);
		
		featuresToLearn3 = new ArrayList<>();
		featuresToLearn3.add(new CartesianFeature("coordinates", 30, 100));
		featuresToLearn3.add(new IntegerFeature("sq. ft.", 800));
		featuresToLearn3.add(new EnumFeature("age", "new"));
		featuresToLearn3.add(new IntegerFeature("price", 400000));
		machineLearning.learn("h3", featuresToLearn3);
	}
	
	/*
	 * learn() isn't tested because it is only ever passed correct parameters since it is being check
	 * in another class before that class calls learn(). Also, learn() was used in setup(), and it worked
	 * fine.
	 */
	
	@Test
	public void testGetProblem() {
		assertEquals("", problem, machineLearning.getProblem());
	}
	
	@Test
	public void testGetStorage() {
		Storage testStorage = new Storage();
		testStorage.insert("h1", featuresToLearn1);
		testStorage.insert("h2", featuresToLearn2);
		testStorage.insert("h3", featuresToLearn3);
		
		HashMap<String, ArrayList<GenericFeature>> learned = new HashMap<>();
		learned = machineLearning.getStorage().getLearned();
		HashMap<String, ArrayList<GenericFeature>> testStorageLearned = new HashMap<>();
		testStorageLearned = testStorage.getLearned();
		
		
		assertEquals("", testStorageLearned.get("h1"), learned.get("h1"));
		assertEquals("", testStorageLearned.get("h2"), learned.get("h2"));
		assertEquals("", testStorageLearned.get("h3"), learned.get("h3"));
	}
	
	/*
	 * I don't need to test addFeatureLayout here. The way it is set up is that it is only ever
	 * called with correct parameters because the parameters are checked by another class when they
	 * are initially entered. Also, I know the method works because it is used in the setup().
	*/
	
	@Test
	public void testGetFeatureLayoutByIndex() {
		List<FeatureLayout> testFeatureLayout = new ArrayList<>();
		String[] allowableDiscreteValues = {"old", "new"};
		
		testFeatureLayout.add(new FeatureLayout(new CartesianEuclideanMetric("coordinates", machineLearning.getStorage())));
		testFeatureLayout.add(new FeatureLayout(new IntegerAbsoluteMetric("sq. ft.", machineLearning.getStorage())));
		testFeatureLayout.add(new FeatureLayout(new DiscreteBinaryMetric("coordinates", machineLearning.getStorage(), allowableDiscreteValues)));
		testFeatureLayout.add(new FeatureLayout(new IntegerAbsoluteMetric("price", machineLearning.getStorage())));
		
		assertEquals("", testFeatureLayout.get(0).getName(), machineLearning.getFeatureLayout(0).getName());
		assertEquals("", testFeatureLayout.get(1).getName(), machineLearning.getFeatureLayout(1).getName());
		assertEquals("", testFeatureLayout.get(2).getName(), machineLearning.getFeatureLayout(2).getName());
		assertEquals("", testFeatureLayout.get(3).getName(), machineLearning.getFeatureLayout(3).getName());	
	}
	
	@Test
	public void testGetFeatureLayoutList() {
		
	}
}