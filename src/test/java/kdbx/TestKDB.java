package kdbx;

import static org.junit.Assert.assertArrayEquals;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.AEADBadTagException;

import org.junit.Test;

import model.kdbx.KDB;
import model.kdbx.KDBHeader;

public class TestKDB {

    private final byte[] plaintext1 = "This is a test 1".getBytes();
    private final byte[] plaintext2 = "This is a test 2".getBytes();
    private final byte[] plaintext3 = "This is a test 3".getBytes();
    private final byte[] password = "ciao".getBytes();

    public final void testKDBWrite1() {
        final File database = new File("test-write-1.kdbx");
        final List<byte[]> credentials = Arrays.asList(password);
        final KDBHeader header = new KDBHeader();
        header.setCipher("AES");
        try {
            final KDB kdb = new KDB(database, credentials, header);
            kdb.write(plaintext1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void testKDBReader1() throws IOException, AEADBadTagException {
        final File database = new File("test-write-1.kdbx");
        final List<byte[]> credentials = Arrays.asList(password);
        try {
            final KDB kdbRead = new KDB(database, credentials);
            byte[] p = kdbRead.read();
            assertArrayEquals(plaintext1, p);
            final byte[] secondPlaintext = "Heyla".getBytes();
            kdbRead.write(secondPlaintext);
            p = kdbRead.read();
            assertArrayEquals(secondPlaintext, p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void testKDBWrite2() {
        final File database = new File("test-write-2.kdbx");
        final List<byte[]> credentials = Arrays.asList(password);
        final KDBHeader header = new KDBHeader();
        header.setCipher("AESGCM");
        try {
            final KDB kdb = new KDB(database, credentials, header);
            kdb.write(plaintext2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void testKDBReader2() throws IOException, AEADBadTagException {
        final File database = new File("test-write-2.kdbx");
        final List<byte[]> credentials = Arrays.asList(password);
        try {
            final KDB kdbRead = new KDB(database, credentials);
            assertArrayEquals(plaintext2, kdbRead.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void testKDBWrite3() {
        final File database = new File("test-write-3.kdbx");
        final List<byte[]> credentials = Arrays.asList(password);
        final KDBHeader header = new KDBHeader();
        header.setCipher("ChaCha20Poly1305");
        header.setKDF("Scrypt");
        header.setKDFMemory(32768);
        header.setKDFParallelism(1);
        header.setTransformRounds(9);
        try {
            final KDB kdb = new KDB(database, credentials, header);
            kdb.write(plaintext3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void testKDBReader3() throws IOException, AEADBadTagException {
        final File database = new File("test-write-3.kdbx");
        final List<byte[]> credentials = Arrays.asList(password);
        try {
            final KDB kdbRead = new KDB(database, credentials);
            byte[] p = kdbRead.read();
            assertArrayEquals(plaintext3, p);
            final byte[] secondPlaintext = "Heyla".getBytes();
            kdbRead.write(secondPlaintext);
            p = kdbRead.read();
            assertArrayEquals(secondPlaintext, p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public final void testKDB1() throws AEADBadTagException, IOException {
        testKDBWrite1();
        testKDBReader1();
    }

    @Test
    public final void testKDB2() throws AEADBadTagException, IOException {
        testKDBWrite2();
        testKDBReader2();
    }

    @Test
    public final void testKDB3() throws AEADBadTagException, IOException {
        testKDBWrite3();
        testKDBReader3();
    }
}
