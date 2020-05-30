package model.crypto;


import org.apache.commons.codec.binary.Hex;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Helper;

public class Argon2KDF implements KDF {

    static final int MEMORY = 65536;
    static final int LENGTH_KEY = 32;
    static final int PARALLELISM = 1;
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
        final byte[] key = argon2.pbkdf(10, Argon2KDF.MEMORY, Argon2KDF.PARALLELISM, password, salt, Argon2KDF.LENGTH_KEY);
        // System.out.println(Hex.encodeHex(key));
        return key;
    }

    @Override
    public final int getDefaultRounds() {
        Argon2 argon2 = Argon2Factory.create();
        final int iterations = Argon2Helper.findIterations(argon2, 1000, Argon2KDF.MEMORY, Argon2KDF.PARALLELISM);
        return iterations;
    }

}
