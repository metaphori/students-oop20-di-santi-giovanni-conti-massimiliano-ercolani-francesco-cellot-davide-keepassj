package model.crypto;

import javax.annotation.processing.SupportedAnnotationTypes;

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
     * @param parallelism This is the number of threads.
     * @throws Exception 
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
     * @throws Exception
     */
    public void setMemory(final int memory) throws Exception {
        if (memory <= Runtime.getRuntime().maxMemory()) {
            this.memory = memory;
        } else {
            throw new Exception("Memory requested too high");
        }
    }

    public final boolean isTweakble() {
        return true;
    }

    public final void setKeySize(final int keySize) {
        this.keySize = keySize;
    }

    public final int getMemory() {
        return this.memory;
    }
}
