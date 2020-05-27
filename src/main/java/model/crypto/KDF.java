package model.crypto;

/**
 * Interface to generate secret key.
 *
 */
public interface KDF {

    /**
     * Generate key.
     * @param password
     * @param rounds
     * @param seed
     * @return key.
     */
    byte[] generateKey(byte[] password, byte[] seed, int rounds);
}
