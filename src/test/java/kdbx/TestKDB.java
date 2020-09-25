package kdbx;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.crypto.AEADBadTagException;

import org.junit.Test;

import model.crypto.KDFBadParameter;
import model.kdbx.KDB;
import model.kdbx.KDBHeader;

public class TestKDB {

    private final byte[] plaintext1 = "This is a test 1".getBytes();
    private final byte[] plaintext2 = "This is a test 2".getBytes();
    private final byte[] plaintext3 = "This is a test 3".getBytes();
    private final byte[] password = "ciao".getBytes();

    public final void testKDBWrite1() {
        final File database = new File("test-write-1.kdbj");
        final List<byte[]> credentials = Arrays.asList(password);
        final KDBHeader header = new KDBHeader();
        header.setCipher("AES");
        header.setComment("This is a database".getBytes());
        header.setPublicCustomData("This is a data".getBytes());
        try {
            final KDB kdb = new KDB(database, credentials, header);
            kdb.write(plaintext1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void testKDBReader1() throws IOException, AEADBadTagException {
        final File database = new File("test-write-1.kdbj");
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
        final File database = new File("test-write-2.kdbj");
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
        final File database = new File("test-write-2.kdbj");
        final List<byte[]> credentials = Arrays.asList(password);
        try {
            final KDB kdbRead = new KDB(database, credentials);
            assertArrayEquals(plaintext2, kdbRead.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void testKDBWrite3() throws KDFBadParameter {
        final int memory = 32_768;
        final int rounds = 9;
        final File database = new File("test-write-3.kdbj");
        final List<byte[]> credentials = Arrays.asList(password);
        final KDBHeader header = new KDBHeader();
        header.setCipher("ChaCha20Poly1305");
        header.setKDF("Scrypt");
        header.setKDFMemory(memory);
        header.setKDFParallelism(1);
        header.setTransformRounds(rounds);
        try {
            final KDB kdb = new KDB(database, credentials, header);
            kdb.write(plaintext3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final void testKDBReader3() throws IOException, AEADBadTagException {
        final File database = new File("test-write-3.kdbj");
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
    public final void testKDB3() throws AEADBadTagException, IOException, KDFBadParameter {
        testKDBWrite3();
        testKDBReader3();
    }

    @Test
    public final void testAllCipherAndKDF() throws AEADBadTagException, IOException, KDFBadParameter {
        final File database = new File("test.kdbj");
        final KDBHeader header = new KDBHeader();
        final byte[] first = "all test".getBytes();
        final List<byte[]> credentials = Arrays.asList(password, "this should be the additional key".getBytes());
        final byte[] second = "second this is the second, this is the second".getBytes();
        Set<String> ciphers = header.getCipherDescriptions().keySet();
        Set<String> kdfs = header.getKDFDescriptions().keySet();
        int count = 0;
        for (final String cipher: ciphers) {
            for (final String kdf: kdfs) {
                final KDBHeader h = new KDBHeader();
                h.setCipher(cipher);
                h.setKDF(kdf);
                System.out.println("Using: " + cipher + " with: " + kdf);
                if (count >= 1) {
                    KDB k = new KDB(database, credentials);
                    final byte[] dec = k.read();
                    System.out.println("READ: " + Arrays.toString(dec));
                    assertArrayEquals(second, k.read());
                }
                KDB kdb = new KDB(database, credentials, h);
                kdb.write(first);
                assertArrayEquals(first, kdb.read());
                kdb.write(second);
                assertArrayEquals(second, kdb.read());
                count++;
            }
        }
    }
}
