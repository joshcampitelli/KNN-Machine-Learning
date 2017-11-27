package UnitTests.FeatureTests;

import Model.Features.EnumFeature;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiscreteFeatureTest {
    private EnumFeature intFeat;

    @Before
    public void setUp(){
        intFeat = new EnumFeature("age", "old" );
    }

    @Test
    public void testGetName() {
        assertEquals("Name should be 'age'.", "age", intFeat.getName());
    }

    @Test
    public void testGetValue() {
        assertEquals("Value should be 'old'.", "old", intFeat.getValue());
    }
}
