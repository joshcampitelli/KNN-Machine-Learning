package UnitTests.FeatureTests;

import Model.Features.IntegerFeature;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerFeatureGetNameTest {
    private IntegerFeature intFeat;

    @Before
    public void setUp(){
        intFeat = new IntegerFeature("price", 1000);
    }

    @Test
    public void testSize() {
        assertEquals("Size should be 'price'.", "price", intFeat.getName());
    }
}
