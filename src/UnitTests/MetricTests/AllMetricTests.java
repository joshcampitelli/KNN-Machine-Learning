package UnitTests.MetricTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CartesianEuclideanMetricTests.class, DiscreteBinaryMetricTests.class, DoubleAbsoluteMetricTests.class, IntegerAbsoluteMetricTests.class, PolarMetricTests.class, ComplexDifferenceMetricTest.class})
public class AllMetricTests {}