package UnitTests.FeatureTests;

import Model.Features.ComplexFeature;
import Model.Features.DoubleFeature;
import Model.Features.GenericFeature;
import Model.Features.IntegerFeature;
import org.junit.Before;
import org.junit.Test;
import Model.Storage;
import Model.Metrics.IntegerAbsoluteMetric;
import Model.Metrics.DoubleAbsoluteMetric;
import Model.Metrics.PolarMetric;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ComplexFeatureTest {
    private IntegerFeature integerFeature;
    private IntegerAbsoluteMetric intMetric;
    private DoubleFeature doubleFeature;
    private DoubleAbsoluteMetric doubleMetric;
    private ComplexFeature complexFeature;
    private PolarMetric polarMetric;
    private ArrayList<GenericFeature> internalFeatures;

    @Before
    public void setUp(){
        Storage storage = new Storage();
        internalFeatures = new ArrayList<>();
        doubleMetric = new DoubleAbsoluteMetric("Distance", storage );
        doubleFeature = new DoubleFeature("Distance", 1.9, doubleMetric );
        internalFeatures.add(doubleFeature);
        intMetric = new IntegerAbsoluteMetric("Direction", storage);
        integerFeature = new IntegerFeature("Direction", 50, intMetric);
        internalFeatures.add(integerFeature);
        polarMetric = new PolarMetric("Ball", storage);
        complexFeature = new ComplexFeature("Ball", internalFeatures, polarMetric);
    }

    @Test
    public void testGetName() {
        assertEquals("Name should be 'Ball'.", "Ball", integerFeature.getName());
    }

    @Test
    public void testGetValue() {
        assertEquals("Value comparator should be true.", true, internalFeatures.equals(complexFeature.getValue()));
    }

    @Test
    public void testGetMetric(){
        assertEquals("Metric comparison should return true",true,polarMetric.equals(complexFeature.getMetric()));
    }



}
