package model.crypto;

/**
 * Interface to generate secret key.
 */
public interface KDF {

    /**
     * Generate key.
     * @param password This is the password used to generate the key.
     * @param salt This is the salt used to generate the key.
     * @param rounds This is the number of rounds used to generate the key.
     * @return key.
     */
    byte[] generateKey(byte[] password, byte[] salt, int rounds);

    /**
     * Get the default rounds to use for generating a key securely.
     * @return rounds.
     */
    int getDefaultRounds();

    /**
     * Get the default memory used to generate the key.
     * @return memory.
     */
    int getDefaultMemory();

    /**
     * Get the default parallelism used to generate the key.
     * @return parallelism.
     */
    int getDefaultParallelism();

    /**
     * Check if the KDF can be tweaked with memory and parallelism.
     * @return true if the Object is tweakable.
     */
    boolean isTweakable();

    /**
     * Set the memory cost used by KDF to generate the key.
     * @param memory This is the memory cost.
     * @throws Exception When the memory requested is too high.
     */
    void setMemory(int memory) throws KDFBadParameter;

    /**
     * Set the number of threads to generate the key.
     * @param parallelism This is the number of threads.
     * @throws Exception When the parallelism is too high.
     */
    void setParallelism(int parallelism) throws KDFBadParameter;

    /**
     * Set the key size desired as return from generateKey.
     * @param keySize This is the key size.
     */
    void setKeySize(int keySize);

    /**
     * Get the max memory that can be set.
     * @return max memory.
     */
    int getMaxMemory();

    /**
     * Get the max number of parallelism that can be set.
     * @return max parallelism.
     */
    int getMaxParallelism();

}
