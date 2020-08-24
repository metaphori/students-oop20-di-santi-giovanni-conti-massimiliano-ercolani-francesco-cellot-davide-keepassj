package model.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF implements KDF {

    private static final int MULTIPLIER = 8;
    private static final int DEFAULT_KEY_SIZE = 32;
    private static final int ROUNDS = 10000;

    private int keySize = DEFAULT_KEY_SIZE;

    @Override
    public final byte[] generateKey(final byte[] password, final byte[] salt, final int rounds) {
        final PBEKeySpec spec = new PBEKeySpec(new String(password).toCharArray(), salt, rounds, this.keySize * MULTIPLIER);
        SecretKeyFactory skf = null;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println("This shouldn't happen");
        }
        return null;
    }

    @Override
    public final int getDefaultRounds() {
        return PBKDF.ROUNDS;
    }

    @Override
    public final boolean isTweakable() {
        return false;
    }

    @Override
    public final void setMemory(final int memory) {
        return;
    }

    @Override
    public final void setParallelism(final int parallelism) {
        return;
    }

    @Override
    public final void setKeySize(final int keySize) {
        this.keySize = keySize;
    }

}
