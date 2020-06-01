package model.crypto;

/**
 * KDF Advanced features.
 */
public abstract class KDFAdvanced implements KDF {

    private static final int DEFAULT_KEY_SIZE = 32;
    private static final int DEFAULT_MEMORY = 32768;
    private static final int DEFAULT_PARALLELISM = 2;
    /**
     * Memory cost parameter.
     */
    protected int memory = DEFAULT_MEMORY;
    /**
     * Parallelism parameter.
     */
    protected int parallelism = DEFAULT_PARALLELISM;
    /**
     * This is the key size desired.
     */
    protected int keySize = DEFAULT_KEY_SIZE;

    /**
     * Check parallelism.
     * @param parallelism This is the number of processes.
     * @throws Exception When the number of processes requested is higher than the computer capability.
     */
    public void setParallelism(final int parallelism) throws Exception {
        if (parallelism <= Runtime.getRuntime().availableProcessors() * 2) {
            this.parallelism = parallelism;
        } else {
            throw new Exception("Parallelism too high");
        }
    }

    /**
     * Check that the memory requested is a correct parameter.
     * @param memory This is the memory requested.
     * @throws Exception When the memory requested is higher than the computer capability.
     */
    public void setMemory(final int memory) throws Exception {
        if (memory <= Runtime.getRuntime().maxMemory()) {
            this.memory = memory;
        } else {
            throw new Exception("Memory requested too high");
        }
    }

    /**
     * If is tweakable is true, then the setParallelism and setMemory are configurable.
     * @return boolean.
     */
    public final boolean isTweakble() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public final void setKeySize(final int keySize) {
        this.keySize = keySize;
    }

    @SuppressWarnings("all")
    private int getMemory() {
        return this.memory;
    }
}
