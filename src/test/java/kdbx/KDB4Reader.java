package kdbx;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import model.kdbx.KDB4File;
import model.kdbx.KDB4Header;

import org.junit.Test;

public class KDB4Reader {

    @Test
    public void test() {
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("database.kdbx");
        KDB4File db = new KDB4File(inputStream);
        KDB4Header header = db.getHeader();
        assertEquals(header.getCipher(), "AES");
    }

}
