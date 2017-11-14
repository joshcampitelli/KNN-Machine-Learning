package UnitTests.StorageTests;

import Model.Storage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the Storage when size = 0
 * @author Josh Campitelli
 */
public class StorageTestNothingLearned {
    private Storage storage;
    @Before
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void testSize() {
        assertEquals("Size should be 0.", 0, storage.getSize());
    }
}