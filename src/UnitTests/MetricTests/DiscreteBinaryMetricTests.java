package UnitTests.MetricTests;

import Model.Features.GenericFeature;
import Model.Storage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Features.EnumFeature;
import Model.Metrics.DiscreteBinaryMetric;

public class DiscreteBinaryMetricTests {
    private HashMap<String, Double> testMap;

    private EnumFeature discreteFeature;
    private DiscreteBinaryMetric discreteMetric;


    @Before
    public void setUp() {
        Storage storage = new Storage();
        ArrayList<GenericFeature> instance = new ArrayList<>();
        testMap = new HashMap<>();

        String[] allowed = new String[]{"old", "new"};
        discreteMetric = new DiscreteBinaryMetric("age", storage, allowed);
        instance.add(new EnumFeature("age","old", discreteMetric));
        storage.insert("h1",instance);
        discreteFeature = new EnumFeature("age","new", discreteMetric);
        testMap.put("h1", 1.0);


    }

    @Test
    public void testGetDistance(){
        assertEquals("Distance should be 1.0 and compare to true", true, testMap.get("h1").equals(discreteMetric.getDistance(discreteFeature).get("h1")));
    }
}
