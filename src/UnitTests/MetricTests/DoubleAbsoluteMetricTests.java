package UnitTests.MetricTests;

import Model.Features.GenericFeature;
import Model.Storage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Metrics.DoubleAbsoluteMetric;
import Model.Features.DoubleFeature;

public class DoubleAbsoluteMetricTests {

    private HashMap<String, Double> testMap;

    private DoubleAbsoluteMetric doubleMetric;
    private DoubleFeature doubleFeature;

    @Before
    public void setUp() {
        Storage storage = new Storage();
        ArrayList<GenericFeature> instance = new ArrayList<>();
        testMap = new HashMap<>();

        doubleMetric = new DoubleAbsoluteMetric("distance", storage);
        instance.add(new DoubleFeature("distance",1.9, doubleMetric));
        storage.insert("h1",instance);
        doubleFeature = new DoubleFeature("distance", 3.9, doubleMetric);
        testMap.put("h1", 2.0);
    }

    @Test
    public void testGetDistance(){
        assertEquals("Distance should be 2.0 and compare to true", true, testMap.get("h1").equals(doubleMetric.getDistance(doubleFeature).get("h1")));
    }

}
