package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import model.db.Entry;
import model.db.Group;

public class TestEntry {

private Entry gf = null;

    @org.junit.Test
    public void testConstructor() {
        gf = new Entry();
        assertNotNull(gf);
    }

    @org.junit.Test
    public void testGetterSetter() {
        String nameAccount = "nome";
        String username = "username";
        String password = "1234abcde";
        String category = "other";
        String url = "www.try.it";
        String note = "note";

        gf = new Entry();
        gf.setNameAccount(nameAccount);
        gf.setUsername(username);
        gf.setPassword(password);
        gf.setGroupName(new Group(category));
        gf.setUrl(url);
        gf.setNote(note);

        assertNotNull(gf);
        assertEquals(nameAccount, gf.getNameAccount());
        assertEquals(username, gf.getUsername());
        assertEquals(password, gf.getPassword());
        assertEquals(category, gf.getGroupName());
        assertEquals(url, gf.getUrl());
        assertEquals(note, gf.getNote());
    }
}
