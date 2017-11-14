package UnitTests.FeatureTests;

import Model.Features.CartesianFeature;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CartesianFeatureGetNameTest {
    private CartesianFeature cartFeat;

    @Before
    public void setUp(){
        cartFeat = new CartesianFeature("coordinates", 10,5);
    }

    @Test
    public void testSize() {
        assertEquals("Size should be 'coordinates.", "coordinates" , cartFeat.getName());
    }
}
