package UnitTests.MachineLearningTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import Model.MachineLearning;
import Model.Storage;
import Model.Features.*;
import Model.Metrics.DiscreteBinaryMetric;
import Model.Metrics.DoubleAbsoluteMetric;
import Model.Metrics.IntegerAbsoluteMetric;
import Model.Metrics.PolarMetric;

public class TestPredictWithNewMLProblem {
	private MachineLearning ML = null;
	private String problem = null;
	
	@Before
	public void setup() {
		problem = "Soccer";
		ML = new MachineLearning(problem);
		String[] allowableDiscreteValues = {"kick", "dash", "turn"};
		PolarMetric ball = new PolarMetric("Ball", ML.getStorage());
		PolarMetric goal = new PolarMetric("Goal", ML.getStorage());
		PolarMetric fct = new PolarMetric("FCT", ML.getStorage());
		PolarMetric fcb = new PolarMetric("FCB", ML.getStorage());
		DoubleAbsoluteMetric dist = new DoubleAbsoluteMetric("Distance", ML.getStorage());
		IntegerAbsoluteMetric dir = new IntegerAbsoluteMetric("Direction", ML.getStorage());
		DiscreteBinaryMetric action = new DiscreteBinaryMetric("Action", ML.getStorage(), allowableDiscreteValues);
		
		action.setPredictable();
		
		ML.addRequiredFeature(ball);
		ML.addRequiredFeature(goal);
		ML.addRequiredFeature(fct);
		ML.addRequiredFeature(fcb);
		ML.addRequiredFeature(action);
		
		//featuresToLearn -> ftl because I am not writing that over and over

		//ftl1
		ArrayList<GenericFeature> ftl1 = new ArrayList<>();
		ArrayList<GenericFeature> ftl1ball = new ArrayList<>();
		ftl1ball.add(new DoubleFeature("Distance", 1.9, dist));
		ftl1ball.add(new IntegerFeature("Direction", -167, dir));
		ftl1.add(new ComplexFeature("Ball", ftl1ball, ball));
		
		ArrayList<GenericFeature> ftl1goal = new ArrayList<>();
		ftl1goal.add(new DoubleFeature("Distance", 63.8, dist));
		ftl1goal.add(new IntegerFeature("Direction", 31, dir));
		ftl1.add(new ComplexFeature("Goal", ftl1goal, goal));
		
		ArrayList<GenericFeature> ftl1fct = new ArrayList<>();
		ftl1fct.add(new DoubleFeature("Distance", 39.1, dist));
		ftl1fct.add(new IntegerFeature("Direction", -41, dir));
		ftl1.add(new ComplexFeature("FCT", ftl1fct, fct));
		
		ArrayList<GenericFeature> ftl1fcb = new ArrayList<>();
		ftl1fcb.add(new DoubleFeature("Distance", 0, dist));
		ftl1fcb.add(new IntegerFeature("Direction", 0, dir));
		ftl1.add(new ComplexFeature("FCB", ftl1fcb, fcb));
		
		ftl1.add(new EnumFeature("Action", "Kick", action));
		ML.learn("S1", ftl1);
		
		//ftl2
		ArrayList<GenericFeature> ftl2 = new ArrayList<>();
		ArrayList<GenericFeature> ftl2ball = new ArrayList<>();
		ftl2ball.add(new DoubleFeature("Distance", 1.9, dist));
		ftl2ball.add(new IntegerFeature("Direction", 50, dir));
		ftl2.add(new ComplexFeature("Ball", ftl2ball, ball));
		
		ArrayList<GenericFeature> ftl2goal = new ArrayList<>();
		ftl2goal.add(new DoubleFeature("Distance", 63.8, dist));
		ftl2goal.add(new IntegerFeature("Direction", 31, dir));
		ftl2.add(new ComplexFeature("Goal", ftl2goal, goal));
		
		ArrayList<GenericFeature> ftl2fct = new ArrayList<>();
		ftl2fct.add(new DoubleFeature("Distance", 39.1, dist));
		ftl2fct.add(new IntegerFeature("Direction", -41, dir));
		ftl2.add(new ComplexFeature("FCT", ftl2fct, fct));
		
		ArrayList<GenericFeature> ftl2fcb = new ArrayList<>();
		ftl2fcb.add(new DoubleFeature("Distance", 0, dist));
		ftl2fcb.add(new IntegerFeature("Direction", 0, dir));
		ftl2.add(new ComplexFeature("FCB", ftl2fcb, fcb));
		
		ftl2.add(new EnumFeature("Action", "Kick", action));
		ML.learn("S2", ftl2);		
		
		//ftl3
		ArrayList<GenericFeature> ftl3 = new ArrayList<>();
		ArrayList<GenericFeature> ftl3ball = new ArrayList<>();
		ftl3ball.add(new DoubleFeature("Distance", 1.8, dist));
		ftl3ball.add(new IntegerFeature("Direction", 2, dir));
		ftl3.add(new ComplexFeature("Ball", ftl3ball, ball));
		
		ArrayList<GenericFeature> ftl3goal = new ArrayList<>();
		ftl3goal.add(new DoubleFeature("Distance", 61.9, dist));
		ftl3goal.add(new IntegerFeature("Direction", -4, dir));
		ftl3.add(new ComplexFeature("Goal", ftl3goal, goal));
		
		ArrayList<GenericFeature> ftl3fct = new ArrayList<>();
		ftl3fct.add(new DoubleFeature("Distance", 0, dist));
		ftl3fct.add(new IntegerFeature("Direction", 0, dir));
		ftl3.add(new ComplexFeature("FCT", ftl3fct, fct));
		
		ArrayList<GenericFeature> ftl3fcb = new ArrayList<>();
		ftl3fcb.add(new DoubleFeature("Distance", 0, dist));
		ftl3fcb.add(new IntegerFeature("Direction", 0, dir));
		ftl3.add(new ComplexFeature("FCB", ftl3fcb, fcb));
		
		ftl3.add(new EnumFeature("Action", "Kick", action));
		ML.learn("S3", ftl3);
		
		
		//ftl4
		ArrayList<GenericFeature> ftl4 = new ArrayList<>();
		ArrayList<GenericFeature> ftl4ball = new ArrayList<>();
		ftl4ball.add(new DoubleFeature("Distance", 1.8, dist));
		ftl4ball.add(new IntegerFeature("Direction", -85, dir));
		ftl4.add(new ComplexFeature("Ball", ftl4ball, ball));
		
		ArrayList<GenericFeature> ftl4goal = new ArrayList<>();
		ftl4goal.add(new DoubleFeature("Distance", 53.5, dist));
		ftl4goal.add(new IntegerFeature("Direction", 17, dir));
		ftl4.add(new ComplexFeature("Goal", ftl4goal, goal));
		
		ArrayList<GenericFeature> ftl4fct = new ArrayList<>();
		ftl4fct.add(new DoubleFeature("Distance", 0, dist));
		ftl4fct.add(new IntegerFeature("Direction", 0, dir));
		ftl4.add(new ComplexFeature("FCT", ftl4fct, fct));
		
		ArrayList<GenericFeature> ftl4fcb = new ArrayList<>();
		ftl4fcb.add(new DoubleFeature("Distance", 0, dist));
		ftl4fcb.add(new IntegerFeature("Direction", 0, dir));
		ftl4.add(new ComplexFeature("FCB", ftl4fcb, fcb));
		
		ftl4.add(new EnumFeature("Action", "Kick", action));
		ML.learn("S4", ftl4);		
		
		//ftl5
		ArrayList<GenericFeature> ftl5 = new ArrayList<>();
		ArrayList<GenericFeature> ftl5ball = new ArrayList<>();
		ftl5ball.add(new DoubleFeature("Distance", 19.2, dist));
		ftl5ball.add(new IntegerFeature("Direction", 1, dir));
		ftl5.add(new ComplexFeature("Ball", ftl5ball, ball));
		
		ArrayList<GenericFeature> ftl5goal = new ArrayList<>();
		ftl5goal.add(new DoubleFeature("Distance", 24.6, dist));
		ftl5goal.add(new IntegerFeature("Direction", -17, dir));
		ftl5.add(new ComplexFeature("Goal", ftl5goal, goal));
		
		ArrayList<GenericFeature> ftl5fct = new ArrayList<>();
		ftl5fct.add(new DoubleFeature("Distance", 0, dist));
		ftl5fct.add(new IntegerFeature("Direction", 0, dir));
		ftl5.add(new ComplexFeature("FCT", ftl5fct, fct));
		
		ArrayList<GenericFeature> ftl5fcb = new ArrayList<>();
		ftl5fcb.add(new DoubleFeature("Distance", 0, dist));
		ftl5fcb.add(new IntegerFeature("Direction", 0, dir));
		ftl5.add(new ComplexFeature("FCB", ftl5fcb, fcb));
		
		ftl5.add(new EnumFeature("Action", "Dash", action));
		ML.learn("S5", ftl5);

		//ftl6
		ArrayList<GenericFeature> ftl6 = new ArrayList<>();
		ArrayList<GenericFeature> ftl6ball = new ArrayList<>();
		ftl6ball.add(new DoubleFeature("Distance", 15.9, dist));
		ftl6ball.add(new IntegerFeature("Direction", 1, dir));
		ftl6.add(new ComplexFeature("Ball", ftl6ball, ball));
		
		ArrayList<GenericFeature> ftl6goal = new ArrayList<>();
		ftl6goal.add(new DoubleFeature("Distance", 22.3, dist));
		ftl6goal.add(new IntegerFeature("Direction", -18, dir));
		ftl6.add(new ComplexFeature("Goal", ftl6goal, goal));
		
		ArrayList<GenericFeature> ftl6fct = new ArrayList<>();
		ftl6fct.add(new DoubleFeature("Distance", 0, dist));
		ftl6fct.add(new IntegerFeature("Direction", 0, dir));
		ftl6.add(new ComplexFeature("FCT", ftl6fct, fct));
		
		ArrayList<GenericFeature> ftl6fcb = new ArrayList<>();
		ftl6fcb.add(new DoubleFeature("Distance", 0, dist));
		ftl6fcb.add(new IntegerFeature("Direction", 0, dir));
		ftl6.add(new ComplexFeature("FCB", ftl6fcb, fcb));
		
		ftl6.add(new EnumFeature("Action", "Dash", action));
		ML.learn("S6", ftl6);
		
		//ftl7
		ArrayList<GenericFeature> ftl7 = new ArrayList<>();
		ArrayList<GenericFeature> ftl7ball = new ArrayList<>();
		ftl7ball.add(new DoubleFeature("Distance", 14.5, dist));
		ftl7ball.add(new IntegerFeature("Direction", 1, dir));
		ftl7.add(new ComplexFeature("Ball", ftl7ball, ball));
		
		ArrayList<GenericFeature> ftl7goal = new ArrayList<>();
		ftl7goal.add(new DoubleFeature("Distance", 20.7, dist));
		ftl7goal.add(new IntegerFeature("Direction", -20, dir));
		ftl7.add(new ComplexFeature("Goal", ftl7goal, goal));
		
		ArrayList<GenericFeature> ftl7fct = new ArrayList<>();
		ftl7fct.add(new DoubleFeature("Distance", 0, dist));
		ftl7fct.add(new IntegerFeature("Direction", 0, dir));
		ftl7.add(new ComplexFeature("FCT", ftl7fct, fct));
		
		ArrayList<GenericFeature> ftl7fcb = new ArrayList<>();
		ftl7fcb.add(new DoubleFeature("Distance", 0, dist));
		ftl7fcb.add(new IntegerFeature("Direction", 0, dir));
		ftl7.add(new ComplexFeature("FCB", ftl7fcb, fcb));
		
		ftl7.add(new EnumFeature("Action", "Dash", action));
		ML.learn("S7", ftl7);
		
		//ftl8
		ArrayList<GenericFeature> ftl8 = new ArrayList<>();
		ArrayList<GenericFeature> ftl8ball = new ArrayList<>();
		ftl8ball.add(new DoubleFeature("Distance", 11, dist));
		ftl8ball.add(new IntegerFeature("Direction", 1, dir));
		ftl8.add(new ComplexFeature("Ball", ftl8ball, ball));
		
		ArrayList<GenericFeature> ftl8goal = new ArrayList<>();
		ftl8goal.add(new DoubleFeature("Distance", 0, dist));
		ftl8goal.add(new IntegerFeature("Direction", 0, dir));
		ftl8.add(new ComplexFeature("Goal", ftl8goal, goal));
		
		ArrayList<GenericFeature> ftl8fct = new ArrayList<>();
		ftl8fct.add(new DoubleFeature("Distance", 44.8, dist));
		ftl8fct.add(new IntegerFeature("Direction", -5, dir));
		ftl8.add(new ComplexFeature("FCT", ftl8fct, fct));
		
		ArrayList<GenericFeature> ftl8fcb = new ArrayList<>();
		ftl8fcb.add(new DoubleFeature("Distance", 0, dist));
		ftl8fcb.add(new IntegerFeature("Direction", 0, dir));
		ftl8.add(new ComplexFeature("FCB", ftl8fcb, fcb));
		
		ftl8.add(new EnumFeature("Action", "Dash", action));
		ML.learn("S8", ftl8);
		
		
	}
	
	@Test
	public void testSize() {
		assertEquals("", 8, ML.getStorage().getSize());
	}
	
	@Test
	public void testPredict() {
		PolarMetric ball = new PolarMetric("Ball", ML.getStorage());
		PolarMetric goal = new PolarMetric("Goal", ML.getStorage());
		PolarMetric fct = new PolarMetric("FCT", ML.getStorage());
		PolarMetric fcb = new PolarMetric("FCB", ML.getStorage());
		DoubleAbsoluteMetric dist = new DoubleAbsoluteMetric("Distance", ML.getStorage());
		IntegerAbsoluteMetric dir = new IntegerAbsoluteMetric("Direction", ML.getStorage());
		
		ArrayList<GenericFeature> ftl0 = new ArrayList<>();
		ArrayList<GenericFeature> ftl0ball = new ArrayList<>();
		ftl0ball.add(new DoubleFeature("Distance", 21.1, dist));
		ftl0ball.add(new IntegerFeature("Direction", 1, dir));
		ftl0.add(new ComplexFeature("Ball", ftl0ball, ball));
		
		ArrayList<GenericFeature> ftl0goal = new ArrayList<>();
		ftl0goal.add(new DoubleFeature("Distance", 41.9, dist));
		ftl0goal.add(new IntegerFeature("Direction", -4, dir));
		ftl0.add(new ComplexFeature("Goal", ftl0goal, goal));
		
		ArrayList<GenericFeature> ftl0fct = new ArrayList<>();
		ftl0fct.add(new DoubleFeature("Distance", 0, dist));
		ftl0fct.add(new IntegerFeature("Direction", 0, dir));
		ftl0.add(new ComplexFeature("FCT", ftl0fct, fct));
		
		ArrayList<GenericFeature> ftl0fcb = new ArrayList<>();
		ftl0fcb.add(new DoubleFeature("Distance", 0, dist));
		ftl0fcb.add(new IntegerFeature("Direction", 0, dir));
		ftl0.add(new ComplexFeature("FCB", ftl0fcb, fcb));
		
		assertEquals("", "Dash", ML.predict(8, ftl0));
	}
}
