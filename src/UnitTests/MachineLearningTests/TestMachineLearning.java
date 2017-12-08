package UnitTests.MachineLearningTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import Model.MachineLearning;
import Model.Storage;
import Model.Features.*;
import Model.Metrics.CartesianEuclideanMetric;
import Model.Metrics.DiscreteBinaryMetric;
import Model.Metrics.IntegerAbsoluteMetric;
/**
 * 
 * @author Ryan Ribeiro
 * 
 */
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
		CartesianEuclideanMetric cartMet = new CartesianEuclideanMetric("coordinates", machineLearning.getStorage());
		IntegerAbsoluteMetric intMet = new IntegerAbsoluteMetric("sq. ft.", machineLearning.getStorage());
		DiscreteBinaryMetric disBiMet = new DiscreteBinaryMetric("age", machineLearning.getStorage(), allowableDiscreteValues);
		IntegerAbsoluteMetric intPriceMet = new IntegerAbsoluteMetric("price", machineLearning.getStorage());
		
		intPriceMet.setPredictable();
		
		machineLearning.addRequiredFeature(cartMet);
		machineLearning.addRequiredFeature(intMet);
		machineLearning.addRequiredFeature(disBiMet);
		machineLearning.addRequiredFeature(intPriceMet);
	
		featuresToLearn1 = new ArrayList<>();
		featuresToLearn1.add(new CartesianFeature("coordinates", 12, 25, cartMet));
		featuresToLearn1.add(new IntegerFeature("sq. ft.", 1200, intMet));
		featuresToLearn1.add(new EnumFeature("age", "new", disBiMet));
		featuresToLearn1.add(new IntegerFeature("price", 500000, intPriceMet));		
		machineLearning.learn("h1", featuresToLearn1);
		
		featuresToLearn2 = new ArrayList<>();
		featuresToLearn2.add(new CartesianFeature("coordinates", 10, 50, cartMet)); 
		featuresToLearn2.add(new IntegerFeature("sq. ft.", 1000, intMet));
		featuresToLearn2.add(new EnumFeature("age", "old", disBiMet));
		featuresToLearn2.add(new IntegerFeature("price", 300000, intPriceMet));
		machineLearning.learn("h2", featuresToLearn2);
		
		featuresToLearn3 = new ArrayList<>();
		featuresToLearn3.add(new CartesianFeature("coordinates", 30, 100, cartMet));
		featuresToLearn3.add(new IntegerFeature("sq. ft.", 800, intMet));
		featuresToLearn3.add(new EnumFeature("age", "new", disBiMet));
		featuresToLearn3.add(new IntegerFeature("price", 400000, intPriceMet));
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
	
	@Test
	public void testDeleteLearned() {
		Storage testStorage = new Storage();
		testStorage.insert("h1", featuresToLearn1);
		testStorage.insert("h2", featuresToLearn2);
		testStorage.insert("h3", featuresToLearn3);
		
		machineLearning.deleteLearned("h1");
		testStorage.remove("h1");		
		assertEquals("", testStorage.getSize(), machineLearning.getStorage().getSize());
		
		machineLearning.deleteLearned("h2");
		testStorage.remove("h2");		
		assertEquals("", testStorage.getSize(), machineLearning.getStorage().getSize());
		
		machineLearning.deleteLearned("h3");
		testStorage.remove("h3");		
		assertEquals("", testStorage.getSize(), machineLearning.getStorage().getSize());
	}
	
	@Test
	public void testPredict() {
		ArrayList<GenericFeature> featuresToLearn4 = new ArrayList<>();
		String[] allowableDiscreteValues = {"old", "new"};
		CartesianEuclideanMetric cartMet = new CartesianEuclideanMetric("coordinates", machineLearning.getStorage());
		IntegerAbsoluteMetric intMet = new IntegerAbsoluteMetric("sq. ft.", machineLearning.getStorage());
		DiscreteBinaryMetric disBiMet = new DiscreteBinaryMetric("age", machineLearning.getStorage(), allowableDiscreteValues);
		
		featuresToLearn4.add(new CartesianFeature("coordinates", 15, 20, cartMet));
		featuresToLearn4.add(new IntegerFeature("sq. ft.", 1000, intMet));
		featuresToLearn4.add(new EnumFeature("age", "new", disBiMet));		
		
		int k;
		String testPredictedPrice;
		
		k = 1; testPredictedPrice = "300000";		
		assertEquals("", testPredictedPrice, machineLearning.predict(k, featuresToLearn4));		

		k = 2; testPredictedPrice = "400000";		
		assertEquals("", testPredictedPrice, machineLearning.predict(k, featuresToLearn4));

		k = 3; testPredictedPrice = "400000";		
		assertEquals("", testPredictedPrice, machineLearning.predict(k, featuresToLearn4));
	}
	
	@Test
	public void testPredictError() {
		ArrayList<GenericFeature> featuresToLearn4 = new ArrayList<>();
		String[] allowableDiscreteValues = {"old", "new"};
		CartesianEuclideanMetric cartMet = new CartesianEuclideanMetric("coordinates", machineLearning.getStorage());
		IntegerAbsoluteMetric intMet = new IntegerAbsoluteMetric("sq. ft.", machineLearning.getStorage());
		DiscreteBinaryMetric disBiMet = new DiscreteBinaryMetric("age", machineLearning.getStorage(), allowableDiscreteValues);
		IntegerAbsoluteMetric intPriceMet = new IntegerAbsoluteMetric("price", machineLearning.getStorage());
		
		featuresToLearn4.add(new CartesianFeature("coordinates", 15, 20, cartMet));
		featuresToLearn4.add(new IntegerFeature("sq. ft.", 1000, intMet));
		featuresToLearn4.add(new EnumFeature("age", "new", disBiMet));
		featuresToLearn4.add(new IntegerFeature("price", 300000, intPriceMet));
		
		int k, expectedError;
		
		k = 1; expectedError = 0;
		assertEquals("", expectedError, machineLearning.predictError(k, featuresToLearn4));		

		k = 2; expectedError = 100000;
		assertEquals("", expectedError, machineLearning.predictError(k, featuresToLearn4));

		k = 3; expectedError = 100000;	
		assertEquals("", expectedError, machineLearning.predictError(k, featuresToLearn4));
	}
	
	@Test
	public void testGetTotalError() {
		ArrayList<GenericFeature> featuresToLearn4 = new ArrayList<>();
		String[] allowableDiscreteValues = {"old", "new"};
		CartesianEuclideanMetric cartMet = new CartesianEuclideanMetric("coordinates", machineLearning.getStorage());
		IntegerAbsoluteMetric intMet = new IntegerAbsoluteMetric("sq. ft.", machineLearning.getStorage());
		DiscreteBinaryMetric disBiMet = new DiscreteBinaryMetric("age", machineLearning.getStorage(), allowableDiscreteValues);
		IntegerAbsoluteMetric intPriceMet = new IntegerAbsoluteMetric("price", machineLearning.getStorage());
		
		featuresToLearn4.add(new CartesianFeature("coordinates", 15, 20, cartMet));
		featuresToLearn4.add(new IntegerFeature("sq. ft.", 1000, intMet));
		featuresToLearn4.add(new EnumFeature("age", "new", disBiMet));
		featuresToLearn4.add(new IntegerFeature("price", 300000, intPriceMet));
		
		int k;
		int testPredictedTotalError;
		
		k = 1; testPredictedTotalError = 0;
		machineLearning.predictError(k, featuresToLearn4);
		assertEquals("", testPredictedTotalError, machineLearning.getTotalError());
		
		k = 2; testPredictedTotalError = 100000;	
		machineLearning.predictError(k, featuresToLearn4);
		assertEquals("", testPredictedTotalError, machineLearning.getTotalError());
		
		k = 3; testPredictedTotalError = 200000;		
		machineLearning.predictError(k, featuresToLearn4);
		assertEquals("", testPredictedTotalError, machineLearning.getTotalError());
	}
	
	@Test
	public void testEquals() {
		String problem2 = "Housing";
		MachineLearning machineLearning2 = new MachineLearning(problem2);
		String[] allowableDiscreteValues = {"old", "new"};
		CartesianEuclideanMetric cartMet = new CartesianEuclideanMetric("coordinates", machineLearning2.getStorage());
		IntegerAbsoluteMetric intMet = new IntegerAbsoluteMetric("sq. ft.", machineLearning2.getStorage());
		DiscreteBinaryMetric disBiMet = new DiscreteBinaryMetric("age", machineLearning2.getStorage(), allowableDiscreteValues);
		IntegerAbsoluteMetric intPriceMet = new IntegerAbsoluteMetric("price", machineLearning2.getStorage());
		ArrayList<GenericFeature> featuresToLearnA;
		ArrayList<GenericFeature> featuresToLearnB;
		ArrayList<GenericFeature> featuresToLearnC;
		
		intPriceMet.setPredictable();
		
		machineLearning2.addRequiredFeature(cartMet);
		machineLearning2.addRequiredFeature(intMet);
		machineLearning2.addRequiredFeature(disBiMet);
		machineLearning2.addRequiredFeature(intPriceMet);
	
		featuresToLearnA = new ArrayList<>();
		featuresToLearnA.add(new CartesianFeature("coordinates", 12, 25, cartMet));
		featuresToLearnA.add(new IntegerFeature("sq. ft.", 1200, intMet));
		featuresToLearnA.add(new EnumFeature("age", "new", disBiMet));
		featuresToLearnA.add(new IntegerFeature("price", 500000, intPriceMet));		
		machineLearning2.learn("h1", featuresToLearnA);
		
		featuresToLearnB = new ArrayList<>();
		featuresToLearnB.add(new CartesianFeature("coordinates", 10, 50, cartMet)); 
		featuresToLearnB.add(new IntegerFeature("sq. ft.", 1000, intMet));
		featuresToLearnB.add(new EnumFeature("age", "old", disBiMet));
		featuresToLearnB.add(new IntegerFeature("price", 300000, intPriceMet));
		machineLearning2.learn("h2", featuresToLearnB);
		
		featuresToLearnC = new ArrayList<>();
		featuresToLearnC.add(new CartesianFeature("coordinates", 30, 100, cartMet));
		featuresToLearnC.add(new IntegerFeature("sq. ft.", 800, intMet));
		featuresToLearnC.add(new EnumFeature("age", "new", disBiMet));
		featuresToLearnC.add(new IntegerFeature("price", 400000, intPriceMet));
		machineLearning2.learn("h3", featuresToLearnC);
		
		assertEquals("", true, machineLearning.equals(machineLearning2));
	}
	
	@Test
	public void testSerialSaveAndOpen() {
		MachineLearning createdMachineLearning = null;
		machineLearning.serialSave("test.ser");
		createdMachineLearning = machineLearning.serialOpen("test.ser");
		
		assertEquals("", true, createdMachineLearning.equals(machineLearning));
	}
	
}
