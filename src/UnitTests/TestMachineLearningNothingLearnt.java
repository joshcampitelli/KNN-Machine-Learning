package UnitTests;

import org.junit.Before;

import Model.MachineLearning;

public class TestMachineLearningNothingLearnt {
	private MachineLearning housing = null;
	
	@Before
	public void setup(){
		housing = new MachineLearning("Housing");
	}
}
