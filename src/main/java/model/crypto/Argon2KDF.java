package model.crypto;


import org.apache.commons.codec.binary.Hex;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Helper;

public class Argon2KDF extends KDFAdvanced {

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
        final byte[] key = argon2.pbkdf(rounds, this.memory, this.parallelism, password, salt, this.keySize);
        return key;
    }

    @Override
    public final int getDefaultRounds() {
        Argon2 argon2 = Argon2Factory.create();
        final int iterations = Argon2Helper.findIterations(argon2, 1000, this.memory, this.parallelism);
        return iterations;
    }

    @Override
    public final boolean isTweakable() {
        return true;
    }

}
