package db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import model.db.Database;
import model.db.Entry;

public class TestDatabase {

private Database gf = null;

    @org.junit.Before
    public void initFactory() {
        this.gf = new Database();
    }

    @org.junit.Test
    public void testAddEntry() {
        final Entry g = new Entry("prova", "name", "mypass00", "category", "www.prova.it", "");
        assertNotNull(gf);
        assertTrue(gf.addEntry(g));
        assertFalse(gf.isEmpty());
    }

    @org.junit.Test
    public void testDelEntry() {
        final Entry g = new Entry("prova", "name", "mypass00", "category", "www.prova.it", "");
        assertNotNull(gf);
        assertTrue(gf.addEntry(g));
        assertTrue(gf.nameAlreadyExist("prova"));
        assertTrue(gf.delEntry("prova"));
        assertFalse(gf.nameAlreadyExist("prova"));
    }

    @org.junit.Test
    public void testCategory() {
        String category = "Other";
        //true for correct add to list
        assertTrue(gf.addCategory(category));
        //false for a category already added
        assertFalse(gf.addCategory(category));
        //true for correct remove from list
        assertTrue(gf.delCategory(category));
        //false for nothing to remove
        assertFalse(gf.delCategory(category));
    }
}
