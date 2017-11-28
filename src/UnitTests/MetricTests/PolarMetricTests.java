package UnitTests.MetricTests;

import Model.Features.GenericFeature;
import Model.Storage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Features.ComplexFeature;
import Model.Features.DoubleFeature;
import Model.Features.GenericFeature;
import Model.Features.IntegerFeature;
import Model.Metrics.IntegerAbsoluteMetric;
import Model.Metrics.DoubleAbsoluteMetric;
import Model.Metrics.PolarMetric;

public class PolarMetricTests {

    private HashMap<String, Double> testMap;
    private DoubleAbsoluteMetric doubleMetric;
    private DoubleFeature doubleFeature;
    private IntegerFeature integerFeature;
    private IntegerAbsoluteMetric intMetric;
    private ComplexFeature complexFeature;
    private PolarMetric polarMetric;

    @Before
    public void setUp() {
        Storage storage = new Storage();
        ArrayList<GenericFeature> instance = new ArrayList<>();
        testMap = new HashMap<>();

        ArrayList<GenericFeature> internalFeatures = new ArrayList<>();
        doubleMetric = new DoubleAbsoluteMetric("Distance", storage );
        doubleFeature = new DoubleFeature("Distance", 1.9, doubleMetric );
        internalFeatures.add(doubleFeature);
        intMetric = new IntegerAbsoluteMetric("Direction", storage);
        integerFeature = new IntegerFeature("Direction", 50, intMetric);
        internalFeatures.add(integerFeature);
        polarMetric = new PolarMetric("Ball", storage);
        complexFeature = new ComplexFeature("Ball", internalFeatures, polarMetric);

        ArrayList<GenericFeature> storageFeatures = new ArrayList<>();
        storageFeatures.add(new DoubleFeature("Distance", 1.8, doubleMetric));
        storageFeatures.add(new IntegerFeature("Direction", 2, intMetric));
        instance.add(new ComplexFeature("Ball", storageFeatures, polarMetric));
        storage.insert("h1",instance);
        testMap.put("h1",1.507695809);
    }

    @Test
    public void testGetDistance(){
        assertEquals("Distance should be 2.0 and compare to true", true, testMap.get("h1").equals(polarMetric.getDistance(complexFeature).get("h1")));
    }
}
