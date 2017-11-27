package UnitTests.FeatureTests;

import Model.Features.CartesianFeature;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CartesianFeatureTest {
    private CartesianFeature cartesianFeature;
    private int[] testValues;

    @Before
    public void setUp(){
        cartesianFeature = new CartesianFeature("coordinates", 10,5);
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
}
