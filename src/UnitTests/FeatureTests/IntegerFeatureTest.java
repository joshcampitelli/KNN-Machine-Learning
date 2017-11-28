package UnitTests.FeatureTests;

import Model.Features.IntegerFeature;
import org.junit.Before;
import org.junit.Test;
import Model.Storage;
import Model.Metrics.IntegerAbsoluteMetric;

import static org.junit.Assert.*;

public class IntegerFeatureTest {
    private IntegerFeature integerFeature;
    private IntegerAbsoluteMetric intMetric;

    @Before
    public void setUp(){
        Storage storage = new Storage();
        intMetric = new IntegerAbsoluteMetric("price", storage);
        integerFeature = new IntegerFeature("price", 1000, intMetric);
    }

    @Test
    public void testGetName() {
        assertEquals("Name should be 'price'.", "price", integerFeature.getName());
    }

    @Test
    public void testGetValue() {
        assertEquals("Value should be 1000.", 1000, integerFeature.getValue());
    }

    @Test
    public void testGetMetric(){
        assertEquals("Metric comparison should return true",true,intMetric.equals(integerFeature.getMetric()));
    }
}
