package UnitTests.FeatureTests;

import Model.Features.ComplexFeature;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CartesianFeatureTest.class, DiscreteFeatureTest.class, IntegerFeatureTest.class, ComplexFeatureTest.class, DoubleFeatureTest.class})
public class AllFeatureTests {}
