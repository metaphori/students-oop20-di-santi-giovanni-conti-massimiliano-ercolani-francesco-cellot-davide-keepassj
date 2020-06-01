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

    private final byte[] plaintext1 = "This is a test 1".getBytes();
    private final byte[] plaintext2 = "This is a test 2".getBytes();
    private final byte[] plaintext3 = "This is a test 3".getBytes();
    @Test
    public void testKDBWrite1() {
        File database = new File("test-write-1.kdbx");
        final List<byte[]> credentials = Arrays.asList("ciao".getBytes());
        KDBHeader header = new KDBHeader();
        header.setCipher("AES");
        try {
            KDB kdb = new KDB(database, credentials, header);
            kdb.write(plaintext1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testKDBReader1() throws IOException {
        File database = new File("test-write-1.kdbx");
        final List<byte[]> credentials = Arrays.asList("ciao".getBytes());
        try {
            KDB kdbRead = new KDB(database, credentials);
            assertArrayEquals(plaintext1, kdbRead.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testKDBWrite2() {
        File database = new File("test-write-2.kdbx");
        final List<byte[]> credentials = Arrays.asList("ciao".getBytes());
        KDBHeader header = new KDBHeader();
        header.setCipher("AESGCM");
        try {
            KDB kdb = new KDB(database, credentials, header);
            kdb.write(plaintext2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testKDBReader2() throws IOException {
        File database = new File("test-write-2.kdbx");
        final List<byte[]> credentials = Arrays.asList("ciao".getBytes());
        try {
            KDB kdbRead = new KDB(database, credentials);
            assertArrayEquals(plaintext2, kdbRead.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testKDBWrite3() {
        File database = new File("test-write-3.kdbx");
        final List<byte[]> credentials = Arrays.asList("ciao".getBytes());
        KDBHeader header = new KDBHeader();
        header.setCipher("ChaCha20Poly1305");
        try {
            KDB kdb = new KDB(database, credentials, header);
            kdb.write(plaintext3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testKDBReader3() throws IOException {
        File database = new File("test-write-3.kdbx");
        final List<byte[]> credentials = Arrays.asList("ciao".getBytes());
        try {
            KDB kdbRead = new KDB(database, credentials);
            assertArrayEquals(plaintext3, kdbRead.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
