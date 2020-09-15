package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import model.db.Datab;
import model.db.ElementDb;

public class TestDatab {

private Datab gf = null;

    @org.junit.Before
    public void initFactory() {
        this.gf = new Datab();
    }

    @org.junit.Test
    public void testAddEntry() {
        final ElementDb g = new ElementDb("prova", "name", "mypass00", "www.prova.it", "");
        assertNotNull(gf);
        assertTrue(gf.isEmpty());
        assertTrue(gf.addEntry(new ElementDb("prova", "name", "mypass00", "www.prova.it", "")));
        assertFalse(gf.isEmpty());
        assertTrue(gf.nameAlreadyExist("prova"));
        assertEquals(g, gf.getEntry("prova"));
        assertTrue(gf.delEntry("prova"));
        assertTrue(gf.isEmpty());
    }
}
