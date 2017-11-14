package UnitTests.FeatureTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CartesianFeatureGetNameTest.class,CartesianFeatureGetValueTest.class,DiscreteFeatureGetNameTest.class,
DiscreteFeatureGetValueTest.class, IntegerFeatureGetNameTest.class, IntegerFeatureGetValueTest.class})
public class AllFeatureTests {
}
