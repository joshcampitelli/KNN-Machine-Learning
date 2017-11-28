package UnitTests.MetricTests;

import Model.Features.GenericFeature;
import Model.Storage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Metrics.CartesianEuclideanMetric;
import Model.Features.CartesianFeature;

public class CartesianEuclideanMetricTests {
    private HashMap<String, Double> testMap;

    private CartesianFeature cartFeat;
    private CartesianEuclideanMetric cartMetric;

    @Before
    public void setUp(){
        Storage storage = new Storage();
        ArrayList<GenericFeature> instance = new ArrayList<>();
        testMap = new HashMap<>();

        cartMetric = new CartesianEuclideanMetric("coordinates", storage);
        instance.add(new CartesianFeature("coordinates",10,5,cartMetric));
        storage.insert("h1",instance);
        cartFeat = new CartesianFeature("coordinates",10,10,cartMetric);
        testMap.put("h1", 5.0);

    }

    @Test
    public void testGetDistance(){
        assertEquals("Distance should be 5 and compare to true", true, testMap.get("h1").equals(cartMetric.getDistance(cartFeat).get("h1")));
    }
}
