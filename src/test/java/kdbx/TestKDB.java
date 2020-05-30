package kdbx;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.kdbx.KDB;
import model.kdbx.KDBHeader;

public class TestKDB {

    @Test
    public void testKDBReader() throws IOException {
        File database = new File("test-write.kdbx");
        final List<byte[]> credentials = Arrays.asList("ciao".getBytes());
        try {
            KDB kdbRead = new KDB(database, credentials);
            kdbRead.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testKDBWrite() {
        File database = new File("test-write.kdbx");
        final List<byte[]> credentials = Arrays.asList("ciao".getBytes());
        byte[] plaintext = "This is a test 2".getBytes();
        KDBHeader header = new KDBHeader();
        header.setCipher("ChaCha20Poly1305");
        try {
            KDB kdb = new KDB(database, credentials, header);
            kdb.write(plaintext);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
