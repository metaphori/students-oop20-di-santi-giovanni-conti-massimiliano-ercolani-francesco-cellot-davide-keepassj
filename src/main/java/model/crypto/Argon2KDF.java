package model.crypto;


import org.apache.commons.codec.binary.Hex;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Helper;

public class Argon2KDF implements KDF {

    private static final int MEMORY = 65536;
    private static final int LENGTH_KEY = 32;
    private static final int PARALLELISM = 1;

    private int memory = MEMORY;
    private int parallelism = PARALLELISM;
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
        final byte[] key = argon2.pbkdf(rounds, this.memory, this.parallelism, password, salt, Argon2KDF.LENGTH_KEY);
        return key;
    }

    @Override
    public final int getDefaultRounds() {
        Argon2 argon2 = Argon2Factory.create();
        final int iterations = Argon2Helper.findIterations(argon2, 1000, Argon2KDF.MEMORY, Argon2KDF.PARALLELISM);
        return iterations;
    }

    @Override
    public final boolean isTweakable() {
        return true;
    }

    @Override
    public final void setMemory(final int memory) {
        this.memory = memory;
    }

    @Override
    public final void setParallelism(final int parallelism) {
        this.parallelism = parallelism;
    }

}
