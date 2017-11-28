package UnitTests.FeatureTests;

import Model.Features.CartesianFeature;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import Model.Storage;
import Model.Metrics.CartesianEuclideanMetric;

public class CartesianFeatureTest {
    private CartesianFeature cartesianFeature;
    private int[] testValues;
    private CartesianEuclideanMetric cartMetric;

    @Before
    public void setUp(){
        Storage storage = new Storage();
        cartMetric = new CartesianEuclideanMetric("coordinates", storage);
        cartesianFeature = new CartesianFeature("coordinates", 10,5, cartMetric);
        testValues = new int[]{10,5};
    }

    @Test
    public void testGetName() {
        assertEquals("Name should be 'coordinates'", "coordinates" , cartesianFeature.getName());
    }

    @Test
    public void testGetValue() {
        assertEquals("Array comparison should return true.",true, Arrays.equals(testValues,(int[]) cartesianFeature.getValue()));
    }

    @Test
    public void testGetMetric(){
        assertEquals("The metric comparison should return true.", true, cartMetric.equals(cartesianFeature.getMetric()));
    }
}
