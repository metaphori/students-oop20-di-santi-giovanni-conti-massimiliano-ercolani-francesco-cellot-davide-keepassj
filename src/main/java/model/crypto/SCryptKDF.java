package model.crypto;

import java.security.GeneralSecurityException;

import com.lambdaworks.crypto.SCrypt;

public class SCryptKDF extends KDFAdvanced {

    private static final int ROUNDS = 8;

    /**
     * {@inheritDoc}
     */
    @Override
    public final byte[] generateKey(final byte[] password, final byte[] salt, final int rounds) {
        byte[] key = null;
        try {
            key = SCrypt.scrypt(password, salt, this.memory, rounds, this.parallelism, this.keySize);
        } catch (GeneralSecurityException e) {
            System.out.println("Error generating key");
        }
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getDefaultRounds() {
        return ROUNDS; 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setMemory(final int memory) throws KDFBadParameter {
        super.setMemory(memory);
        if (isPowerOfTwo(memory)) {
            this.setMemory(memory);
        } else {
            throw new KDFBadParameter("Memory must be a power of two");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isTweakable() {
        return true;
    }

    private boolean isPowerOfTwo(final int number) {
        return number != 0 && (number & (number - 1)) == 0;
    }

}
