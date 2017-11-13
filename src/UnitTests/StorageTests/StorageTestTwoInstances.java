package UnitTests.StorageTests;

import Model.Features.GenericFeature;
import Model.Features.IntegerFeature;
import Model.Storage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StorageTestTwoInstances {
    private Storage storage;
    private ArrayList<GenericFeature> instance1;
    private ArrayList<GenericFeature> instance2;

    @Before
    public void setUp() {
        storage = new Storage();
        instance1 = new ArrayList<>();
        instance2 = new ArrayList<>();

        instance1.add(new IntegerFeature("",1));
        instance2.add(new IntegerFeature("",2));
    }

    @Test
    public void testSize() {
        storage.insert("", instance1);
        storage.insert("", instance2);
        assertEquals("Size should be 1.", 1, storage.getSize());
    }
}