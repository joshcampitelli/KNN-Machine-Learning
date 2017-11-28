package UnitTests.FeatureTests;

import Model.Features.DoubleFeature;
import org.junit.Before;
import org.junit.Test;
import Model.Storage;
import Model.Metrics.DoubleAbsoluteMetric;

import static org.junit.Assert.*;

public class DoubleFeatureTest {
    private DoubleFeature doubleFeature;
    private DoubleAbsoluteMetric doubleMetric;


    @Before
    public void setUp(){
        Storage storage = new Storage();
        doubleMetric = new DoubleAbsoluteMetric("distance", storage );
        doubleFeature = new DoubleFeature("distance", 43.6, doubleMetric );
    }

    @Test
    public void testGetName() {
        assertEquals("Name should be 'distance'.", "distance", doubleFeature.getName());
    }

    @Test
    public void testGetValue() {
        assertEquals("Value should be 43.6", 43.6, doubleFeature.getValue());
    }

    @Test
    public void testGetMetric(){
        assertEquals("Metric Comparison should return true", true, doubleMetric.equals(doubleFeature.getMetric()));
    }

}
