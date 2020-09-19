package db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import model.db.Database;
import model.db.Entry;
import model.db.Group;
import model.kdbx.KDB;

public class TestDatabase {

private Database gf = null;

    @org.junit.Before
    public void initFactory() {
        this.gf = new Database();
    }

    @org.junit.Test
    public void testAddEntry() {
        final Entry g = new Entry();
        assertNotNull(gf);
        assertTrue(gf.addEntry(g));
        assertFalse(gf.isEmpty());
    }

    @org.junit.Test
    public void testDelEntry() {
        final Entry g = new Entry();
        assertNotNull(gf);
        assertTrue(gf.addEntry(g));
        assertTrue(gf.nameAlreadyExist("prova"));
        assertTrue(gf.delEntry("prova"));
        assertFalse(gf.nameAlreadyExist("prova"));
    }

    @org.junit.Test
    public void testCategory() {
        Group group = new Group("Other");
        Entry app = new Entry();
        app.setCategory(group);
        gf.addEntry(app);

        //true for correct add to list
        assertTrue(gf.addCategory(group));
        //false for a category already added
        assertFalse(gf.addCategory(group));
        //true for correct remove from list
        assertTrue(gf.delCategory(group));
        //false for nothing to remove
        assertFalse(gf.delCategory(group));
    }
}
