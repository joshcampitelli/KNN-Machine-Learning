package UnitTests.StorageTests;

import Model.Features.GenericFeature;
import Model.Features.IntegerFeature;
import Model.Storage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StorageTestOneInstance {
    private Storage storage;
    private ArrayList<GenericFeature> instance;

    @Before
    public void setUp() {
        storage = new Storage();
        instance = new ArrayList<>();
        instance.add(new IntegerFeature("",1));
    }

    @Test
    public void testSize() {
        storage.insert("", instance);
        assertEquals("Size should be 1.", 1, storage.getSize());
    }
}