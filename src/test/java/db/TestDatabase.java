package db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import model.db.Database;
import model.db.Entry;
import model.db.Group;

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
        assertTrue(gf.deleteEntry(g));
        assertFalse(gf.nameAlreadyExist("prova"));
    }

    @org.junit.Test
    public void testGroup() {
        Group group = new Group("Other");
        Entry app = new Entry();
        app.setGroup(group);
        gf.addEntry(app);

        //true for correct add to list
        assertTrue(gf.addGroup(group));
        //false for a group already added
        assertFalse(gf.addGroup(group));
        //can't remove from list for 1 item in that group
        assertFalse(gf.delGroup(group));

        //remove item used for test
        gf.deleteEntry(app);
        //true for correct remove
        assertTrue(gf.delGroup(group));
        //false for nothing to remove
        assertFalse(gf.delGroup(group));
    }
}
