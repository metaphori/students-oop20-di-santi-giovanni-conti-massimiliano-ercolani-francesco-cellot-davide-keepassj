package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.db.Database;
import model.db.Entry;
import model.db.Group;

public class TestDatabase {

    private Database myDb;
    private final Group group = new Group("Other");
    private final String nameAccount = "one";
    private final Entry firstEntry = new Entry(nameAccount, "", "", group, "", "");

    @org.junit.Before
    public void initFactory() {
        this.myDb = new Database();
    }

    @org.junit.Test
    public void testAddEntry() {
        assertNotNull(myDb);
        assertTrue(myDb.addEntry(firstEntry));
        assertFalse(myDb.isEmpty());
    }

    @org.junit.Test
    public void testDelEntry() {
        assertNotNull(myDb);
        assertTrue(myDb.addEntry(firstEntry));
        assertTrue(myDb.entryAlreadyExist(firstEntry));
        assertTrue(myDb.deleteEntry(firstEntry));
        assertFalse(myDb.entryAlreadyExist(firstEntry));
    }

    @org.junit.Test
    public void testGroup() {
        myDb.addEntry(firstEntry);

        //true for correct add to list
        assertTrue(myDb.addGroup(group));
        //false for a group already added
        assertFalse(myDb.addGroup(group));
        //can't remove from list for 1 item in that group
        assertFalse(myDb.deleteGroup(group));

        //remove item used for test
        myDb.deleteEntry(firstEntry);
        //true for correct remove
        assertTrue(myDb.deleteGroup(group));
        //false for nothing to remove
        assertFalse(myDb.deleteGroup(group));
    }

    @org.junit.Test
    public void testGetterEntrys() {
        final Entry secondEntry = new Entry("two", "", "", group, "", "");

        myDb.addGroup(group);
        myDb.addEntry(firstEntry);
        myDb.addEntry(secondEntry);

        final ArrayList<Entry> list = new ArrayList<>();
        list.add(firstEntry);
        list.add(secondEntry);

        assertEquals(firstEntry, myDb.getEntry(firstEntry.getNameAccount()));
        assertEquals(list, myDb.getAllEntry());
        assertEquals(list, myDb.getAllEntryOfSpecifiedGroup(group));
    }
}
