package UnitTests.StorageTests;

import Model.Storage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StorageTestOneInstance {
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