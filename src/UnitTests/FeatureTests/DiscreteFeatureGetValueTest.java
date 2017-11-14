package UnitTests.FeatureTests;

import Model.Features.EnumFeature;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiscreteFeatureGetValueTest {
    private EnumFeature intFeat;

    @Before
    public void setUp() {
        intFeat = new EnumFeature("age", "old");
    }

    @Test
    public void testSize() {
            assertEquals("Size should be 'old'.", "old", intFeat.getValue());
        }
}
