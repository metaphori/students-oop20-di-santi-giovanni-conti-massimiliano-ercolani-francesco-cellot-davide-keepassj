package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import model.db.Entry;
import model.db.Group;

public class TestEntry {

    private Entry gf = null;

    private String nameAccount = "nameAccount";
    private String username = "username";
    private String password = "password";
    private final Group group = new Group("group");
    private String url = "url";
    private String note = "notes";

    @org.junit.Test
    public void testConstructor() {
        gf = new Entry(nameAccount, username, password, group, url, note);
        assertNotNull(gf);
    }

    @org.junit.Test
    public void testGetterSetter() {
        gf = new Entry(nameAccount, username, password, group, url, note);

        nameAccount = "prova";
        username = "nome";
        password = "mypass12334";
        group.setName("other");
        url = "www.ciao.it";
        note = "annotazione";

        gf.setNameAccount(nameAccount);
        gf.setUsername(username);
        gf.setPassword(password);
        gf.setGroupName(group);
        gf.setUrl(url);
        gf.setNote(note);

        assertNotNull(gf);
        assertEquals(nameAccount, gf.getNameAccount());
        assertEquals(username, gf.getUsername());
        assertEquals(password, gf.getPassword());
        assertEquals(group.getName(), gf.getGroupName());
        assertEquals(url, gf.getUrl());
        assertEquals(note, gf.getNote());
    }
}
