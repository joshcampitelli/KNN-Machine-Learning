package UnitTests;

import java.util.ArrayList;
import Model.*;
import Model.Features.*;
import Model.Metrics.*;
import junit.framework.*;

public class MachineLearningTest extends TestCase{
	private MachineLearning machineLearn;
	private ArrayList<FeatureLayout> featLayout;
	protected void setUp() {
		featLayout = new ArrayList<>();
		
		featLayout.add(new FeatureLayout("coordinates", CartesianFeature, CartesianMetric));
		featLayout.add(new FeatureLayout("sq. ft.", IntegerFeature, IntegerMetric));
		featLayout.add(new FeatureLayout("age", DiscreteFeature, DiscreteMetric));
		featLayout.add(new FeatureLayout("price", IntegerFeature, IntegerMetric));
		
		machineLearn = new MachineLearning("Housing", featLayout);
		
	}
}
