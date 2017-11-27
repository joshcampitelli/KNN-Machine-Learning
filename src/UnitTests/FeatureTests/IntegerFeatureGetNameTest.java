package UnitTests.FeatureTests;

import Model.Features.IntegerFeature;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerFeatureGetNameTest {
    private IntegerFeature integerFeature;

    @Before
    public void setUp(){
        integerFeature = new IntegerFeature("price", 1000);
    }

    @Test
    public void testGetName() {
        assertEquals("Name should be 'price'.", "price", integerFeature.getName());
    }

    @Test
    public void testGetValue() {
        assertEquals("Value should be 1000.", 1000, integerFeature.getValue());
    }
}
