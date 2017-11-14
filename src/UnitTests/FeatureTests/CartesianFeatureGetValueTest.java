package UnitTests.FeatureTests;

import Model.Features.CartesianFeature;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartesianFeatureGetValueTest {
    private CartesianFeature cartFeat;
    private int[] test;

    @Before
    public void setUp(){
        cartFeat = new CartesianFeature("coordinates", 10,5);
        test = new int[]{10,5};
    }

    @Test
    public void testSize() {
        assertEquals("Size should be array [10,5] .", test  , cartFeat.getValue());
    }
}
