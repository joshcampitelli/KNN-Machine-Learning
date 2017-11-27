package UnitTests.FeatureTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CartesianFeatureTest.class, DiscreteFeatureTest.class, IntegerFeatureGetNameTest.class})
public class AllFeatureTests {}
