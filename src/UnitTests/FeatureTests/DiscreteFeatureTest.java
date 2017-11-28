package UnitTests.FeatureTests;

import Model.Features.EnumFeature;
import org.junit.Before;
import org.junit.Test;
import Model.Storage;
import Model.Metrics.DiscreteBinaryMetric;


import static org.junit.Assert.*;

public class DiscreteFeatureTest {
    private EnumFeature discreteFeat;
    private DiscreteBinaryMetric discrMetric;


    @Before
    public void setUp(){
        Storage storage = new Storage();
        String[] allowed = new String[]{"old", "new"};
        discrMetric = new DiscreteBinaryMetric("age", storage, allowed);
        discreteFeat = new EnumFeature("age", "old", discrMetric );
    }

    @Test
    public void testGetName() {
        assertEquals("Name should be 'age'.", "age", discreteFeat.getName());
    }

    @Test
    public void testGetValue() {
        assertEquals("Value should be 'old'.", "old", discreteFeat.getValue());
    }

    @Test
    public void testGetMetric(){
        assertEquals("Metric Comparison should return true", true, discrMetric.equals(discreteFeat.getMetric()));
    }
}
