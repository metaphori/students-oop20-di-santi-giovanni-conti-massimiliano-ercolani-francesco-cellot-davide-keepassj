package crypto;

import static org.junit.Assert.*;

import org.junit.Test;

import model.crypto.Argon2KDF;
import model.crypto.KDF;
import model.crypto.PBKDF;
import model.crypto.Util;

public class TestKDF {

    @Test
    public void testArgon2() {
        KDF argon2 = new Argon2KDF();
        byte[] password = Util.sha256("ciao".getBytes());
        byte[] seed = Util.sha256("test".getBytes());
        int rounds = 10;
        argon2.generateKey(password, seed, rounds);
    }

    @Test
    public final void testPBKDF() {
        KDF pbkdf2 = new PBKDF();
        byte[] password = Util.sha256("ciao".getBytes());
        byte[] seed = Util.sha256("test".getBytes());
        int rounds = 10;
        pbkdf2.generateKey(password, seed, rounds);
    }

}
