package UnitTests.FeatureTests;

import Model.Features.IntegerFeature;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerFeatureGetValueTest {
    private IntegerFeature intFeat;

    @Before
    public void setUp(){
        intFeat = new IntegerFeature("price", 1000);
    }

    @Test
    public void testSize() {
        assertEquals("Size should be 1000.", 1000, intFeat.getValue());
    }
}
