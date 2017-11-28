package UnitTests.StorageTests;

import Model.Features.GenericFeature;
import Model.Features.IntegerFeature;
import Model.Storage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import Model.Metrics.IntegerAbsoluteMetric;
import static org.junit.Assert.*;

/**
 * Tests the Storage when size = 1
 * @author Josh Campitelli
 */
public class StorageTestOneInstance {
    private Storage storage;
    private ArrayList<GenericFeature> instance;
    private IntegerAbsoluteMetric intMetric;

    @Before
    public void setUp() {
        storage = new Storage();
        instance = new ArrayList<>();
        intMetric = new IntegerAbsoluteMetric("test", storage);
        instance.add(new IntegerFeature("key",1, intMetric));
    }

    @Test
    public void testSize() {
        storage.insert("key", instance);
        assertEquals("Size should be 1.", 1, storage.getSize());
    }

    @Test
    public void testRemove() {
        storage.insert("key", instance);
        assertEquals("Size should be 1.", 1, storage.getSize());
        storage.remove("key");
        assertEquals("Size should be 0.", 0, storage.getSize());
    }
}