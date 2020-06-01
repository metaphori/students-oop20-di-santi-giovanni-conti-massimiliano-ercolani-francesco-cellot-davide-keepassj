package model.crypto;

import java.security.GeneralSecurityException;

import com.google.common.primitives.Bytes;
import com.lambdaworks.crypto.SCrypt;
import com.lambdaworks.crypto.SCryptUtil;

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
    public final void setMemory(final int memory) throws Exception {
        if (isPowerOfTwo(memory)) {
            this.setMemory(memory);
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
        return (number != 0) && ((number & (number - 1)) == 0);
    }

}
