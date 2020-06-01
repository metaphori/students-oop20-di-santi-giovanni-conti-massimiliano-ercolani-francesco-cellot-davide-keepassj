package crypto;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import model.crypto.KDF;
import model.crypto.KDFFactory;
import model.crypto.Util;

public class TestKDF {

    @Test
    public void testArgon2() throws Exception {
        final KDF argon2 = KDFFactory.create("Argon2");
        final byte[] password = Util.sha256("ciao".getBytes());
        final byte[] salt = Util.sha256("test".getBytes());
        final int rounds = 10;
        final int memory = 10_000;
        final int parallelism = 4;
        argon2.setMemory(memory);
        argon2.setKeySize(64);
        argon2.setParallelism(parallelism);
        final byte[] key = argon2.generateKey(password, salt, rounds);
        System.out.println(Hex.encodeHex(key));
    }

    @Test
    public final void testPBKDF2() {
        final KDF pbkdf2 = KDFFactory.create("PBKDF2");
        final byte[] password = Util.sha256("ciao".getBytes());
        final byte[] salt = Util.sha256("test".getBytes());
        final int rounds = 10;
        final int keySize = 64;
        pbkdf2.setKeySize(keySize);
        pbkdf2.generateKey(password, salt, rounds);
    }

    @Test
    public final void testScrypt() throws Exception {
        final KDF scrypt = KDFFactory.create("SCrypt");
        final byte[] password = Util.sha256("ciao".getBytes());
        final byte[] salt = Util.sha256("test".getBytes());
        final int rounds = 8;
        final int parallelism = 4;
        scrypt.setKeySize(64);
        scrypt.setParallelism(parallelism);
        final byte[] key = scrypt.generateKey(password, salt, rounds);
        System.out.println(Hex.encodeHex(key));
    }
}
