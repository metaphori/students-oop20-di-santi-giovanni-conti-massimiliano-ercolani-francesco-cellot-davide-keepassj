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
     * @param salt 
     * @return key.
     */
    byte[] generateKey(byte[] password, byte[] salt, int rounds);

    int getDefaultRounds();

    boolean isTweakable();

    void setMemory(int memory) throws Exception;

    void setParallelism(int parallelism) throws Exception;

    void setKeySize(int keySize);
}
