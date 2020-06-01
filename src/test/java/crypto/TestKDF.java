package crypto;

import org.junit.Test;

import model.crypto.Argon2KDF;
import model.crypto.KDF;
import model.crypto.KDFFactory;
import model.crypto.Util;

public class TestKDF {

    @Test
    public void testArgon2() {
        Argon2KDF argon2 = new Argon2KDF();
        byte[] password = Util.sha256("ciao".getBytes());
        byte[] salt = Util.sha256("test".getBytes());
        // final int rounds = argon2.getDefaultRounds();
        final int rounds = 10;
        final int memory = 100000;
        final int parallelism = 4;
        argon2.setMemory(memory);
        System.out.println(argon2.getMemory());
        argon2.setParallelism(parallelism);
        argon2.generateKey(password, salt, rounds);
    }

    @Test
    public final void testPBKDF2() {
        KDF pbkdf2 = KDFFactory.create("PBKDF2");
        byte[] password = Util.sha256("ciao".getBytes());
        byte[] salt = Util.sha256("test".getBytes());
        int rounds = 10;
        pbkdf2.generateKey(password, salt, rounds);
    }

}
