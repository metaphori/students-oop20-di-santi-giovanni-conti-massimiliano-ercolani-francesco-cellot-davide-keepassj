package model.crypto;


import org.apache.commons.codec.binary.Hex;

import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;

public class Argon2KDF implements KDF {

    static final int MEMORY = 65536;
    static final int LENGTH_KEY = 32;
    /**
     * Return generated key.
     * @param password
     * @param salt
     * @param rounds
     * @return key.
     */
    @Override
    public final byte[] generateKey(final byte[] password, final byte[] salt, final int rounds) {
        Argon2Advanced argon2 = Argon2Factory.createAdvanced();
        byte[] key = argon2.pbkdf(10, MEMORY, 1, password, salt, LENGTH_KEY);
        System.out.println(Hex.encodeHex(key));
        return key;
    }

}
