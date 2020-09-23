package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.JAXBException;

import model.db.Database;
import model.db.Entry;
import model.db.Group;

public class TestDatabase {

private Database myDb = null;

    @org.junit.Before
    public void initFactory() {
        this.myDb = new Database();
    }

    @org.junit.Test
    public void testAddEntry() {
        final Entry g = new Entry();
        assertNotNull(myDb);
        assertTrue(myDb.addEntry(g));
        assertFalse(myDb.isEmpty());
    }

    @org.junit.Test
    public void testDelEntry() {
        final Entry g = new Entry();
        assertNotNull(myDb);
        assertTrue(myDb.addEntry(g));
        assertTrue(myDb.nameAlreadyExist("prova"));
        assertTrue(myDb.deleteEntry(g));
        assertFalse(myDb.nameAlreadyExist("prova"));
    }

    @org.junit.Test
    public void testGroup() {
        Group group = new Group("Other");
        Entry app = new Entry();
        app.setGroupName(group);
        myDb.addEntry(app);

        //true for correct add to list
        assertTrue(myDb.addGroup(group));
        //false for a group already added
        assertFalse(myDb.addGroup(group));
        //can't remove from list for 1 item in that group
        assertFalse(myDb.delGroup(group));

        //remove item used for test
        myDb.deleteEntry(app);
        //true for correct remove
        assertTrue(myDb.delGroup(group));
        //false for nothing to remove
        assertFalse(myDb.delGroup(group));
    }

    @org.junit.Test
    public void testGetterEntrys() {
        Entry one = new Entry();
        one.setNameAccount("one");
        Entry two = new Entry();
        two.setNameAccount("two");

        myDb.addEntry(one);
        myDb.addEntry(two);

        ArrayList<Entry> list = new ArrayList<>();
        list.add(one);
        list.add(two);

        assertEquals(one, myDb.getEntry(one.getNameAccount()));
        assertEquals(list, myDb.getAllEntry());
        assertEquals(list, myDb.getAllEntryOfSpecifiedGroup(new Group("prova")));
    }
}
