package UnitTests.MetricTests;

import Model.Features.ComplexFeature;
import Model.Features.EnumFeature;
import Model.Features.GenericFeature;
import Model.Features.IntegerFeature;
import Model.Metrics.ComplexDifferenceMetric;
import Model.Metrics.DiscreteBinaryMetric;
import Model.Metrics.IntegerAbsoluteMetric;
import Model.Storage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ComplexDifferenceMetricTest {

    private HashMap<String, Double> testMap;
    private IntegerFeature intFeat;
    private IntegerAbsoluteMetric intMetric;
    private EnumFeature discreteFeature;
    private DiscreteBinaryMetric discreteMetric;
    private ComplexFeature complexFeature;
    private ComplexDifferenceMetric complexMetric;


    @Before
    public void setUp() {
        Storage storage = new Storage();
        ArrayList<GenericFeature> instance = new ArrayList<>();
        testMap = new HashMap<>();
        ArrayList<GenericFeature> internalFeatures = new ArrayList<>();

        intMetric = new IntegerAbsoluteMetric("sqr ft",storage);
        intFeat = new IntegerFeature("sqr ft", 150, intMetric);
        internalFeatures.add(intFeat);

        String[] allowed = new String[]{"old", "new"};
        discreteMetric = new DiscreteBinaryMetric("age", storage, allowed);
        discreteFeature = new EnumFeature("age","new", discreteMetric);
        internalFeatures.add(discreteFeature);

        complexMetric = new ComplexDifferenceMetric("shed", storage);
        complexFeature = new ComplexFeature("shed", internalFeatures, complexMetric);

        ArrayList<GenericFeature> storageFeatures = new ArrayList<>();
        storageFeatures.add(new IntegerFeature("sqr ft", 100, intMetric));
        storageFeatures.add(new EnumFeature("age", "old", discreteMetric));
        instance.add(new ComplexFeature("shed", storageFeatures, complexMetric));
        storage.insert("s1",instance);
        testMap.put("s1",51.0);

    }

    @Test
    public void testGetDistance(){
        System.out.println(complexMetric.getDistance(complexFeature).get("s1"));

        assertEquals("Distance should be 51 and compare to true", true, testMap.get("s1").equals(complexMetric.getDistance(complexFeature).get("s1")));
    }
}
