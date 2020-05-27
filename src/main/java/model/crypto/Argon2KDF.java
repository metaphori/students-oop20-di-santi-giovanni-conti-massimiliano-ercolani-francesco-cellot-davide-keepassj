package model.crypto;

import com.google.common.primitives.Bytes;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class Argon2KDF implements KDF {

    static final int MEMORY = 65536;
    /**
     * Return generated key.
     * @param password
     * @param seed
     * @param rounds
     * @return key.
     */
    @Override
    public final byte[] generateKey(final byte[] password, final byte[] seed, final int rounds) {
        int parallelism = 1;
        Argon2 argon2 = Argon2Factory.create();
        final byte[] data = Bytes.concat(password, seed);
        String hash = argon2.hash(rounds, MEMORY, parallelism, data);
        System.out.println(hash);
        return null;
    }

}
