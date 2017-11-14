package UnitTests.FeatureTests;

import Model.Features.CartesianFeature;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CartesianFeatureGetValueTest {
    private CartesianFeature cartFeat;
    private int[] test;

    @Before
    public void setUp(){
        cartFeat = new CartesianFeature("coordinates", 10,5);
        test = new int[]{10,5};
        int[] getTest = (int[]) cartFeat.getValue();
    }

    @Test
    public void testSize() {
        System.out.println(cartFeat.getValue());
        assertEquals("Array comparison should return true.", true  , Arrays.equals(test,(int[]) cartFeat.getValue()));
    }
}
