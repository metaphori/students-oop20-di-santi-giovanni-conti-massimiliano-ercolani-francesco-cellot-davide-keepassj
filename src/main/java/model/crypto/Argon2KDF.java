package model.crypto;


import org.apache.commons.codec.binary.Hex;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Helper;

public class Argon2KDF extends KDFAdvanced {

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
        final Argon2 argon2 = Argon2Factory.create();
        return Argon2Helper.findIterations(argon2, 1000, this.memory, this.parallelism);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isTweakable() {
        return true;
    }

}
