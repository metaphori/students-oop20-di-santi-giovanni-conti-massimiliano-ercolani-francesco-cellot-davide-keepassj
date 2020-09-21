package model.crypto;

import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;

public class Argon2KDF extends KDFAdvanced {

    private static final int ROUNDS = 60;

    /**
     * {@inheritDoc}
     */
    @Override
    public final byte[] generateKey(final byte[] password, final byte[] salt, final int rounds) {
        final Argon2Advanced argon2 = Argon2Factory.createAdvanced();
        return argon2.pbkdf(rounds, this.memory, this.parallelism, password, salt, this.keySize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getDefaultRounds() {
        /* Unfortunately this functions takes too long
        final Argon2 argon2 = Argon2Factory.create();
        return Argon2Helper.findIterations(argon2, 1000, this.memory, this.parallelism)
        */
        return ROUNDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isTweakable() {
        return true;
    }

}
