package UnitTests.MetricTests;

import Model.Features.GenericFeature;
import Model.Metrics.IntegerAbsoluteMetric;
import Model.Storage;
import Model.Features.IntegerFeature;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

public class IntegerAbsoluteMetricTests {
    private IntegerFeature intFeat;
    private Storage storage;
    private IntegerAbsoluteMetric intMetric;
    private HashMap<String, Double> testMap;

    @Before
    public void setUp(){
        storage = new Storage();
        intMetric = new IntegerAbsoluteMetric("sqr ft",storage);
        ArrayList<GenericFeature> instance = new ArrayList<>();
        instance.add(new IntegerFeature("sqr ft", 500, intMetric ));
        storage.insert("h1",instance);
        intFeat = new IntegerFeature("sqr ft", 1000, intMetric);
        testMap = new HashMap<>();
        testMap.put("h1",500.0);
    }

    @Test
    public void testGetDistance(){

        assertEquals("Distance should be 500", true, (testMap.get("h1").equals(intMetric.getDistance(intFeat).get("h1"))));

    }
}
