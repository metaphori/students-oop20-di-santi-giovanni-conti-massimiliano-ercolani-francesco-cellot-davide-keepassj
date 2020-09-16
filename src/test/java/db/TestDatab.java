package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import model.db.Database;
import model.db.Entry;

public class TestDatab {

private Database gf = null;

    @org.junit.Before
    public void initFactory() {
        this.gf = new Database();
    }

    @org.junit.Test
    public void testAddEntry() {
        final Entry g = new Entry("prova", "name", "mypass00", "category", "www.prova.it", "");
        assertNotNull(gf);
        assertTrue(gf.isEmpty());
        assertTrue(gf.addEntry(new Entry("prova", "name", "mypass00", "category", "www.prova.it", "")));
        assertFalse(gf.isEmpty());
        assertTrue(gf.nameAlreadyExist("prova"));
        assertEquals(g, gf.getEntry("prova"));
        assertTrue(gf.delEntry("prova"));
        assertTrue(gf.isEmpty());
    }
}
