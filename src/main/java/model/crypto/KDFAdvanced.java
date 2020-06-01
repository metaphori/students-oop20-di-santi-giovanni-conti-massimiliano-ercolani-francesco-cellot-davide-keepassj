package model.crypto;

public abstract class KDFAdvanced implements KDF {

    private int memory;
    private int parallelism;

    /**
     * Check parallelism.
     * @param parallelism This is the number of threads.
     * @return parallelism.
     * @throws Exception 
     */
    public int checkParallelism(final int parallelism) throws Exception {
        if (parallelism <= Runtime.getRuntime().availableProcessors() * 2) {
            return parallelism;
        }
        throw new Exception("Parallelism too high");
    }

    /**
     * Check that the memory requested is a correct parameter.
     * @param memory This is the memory requested.
     * @return memory.
     * @throws Exception
     */
    public int checkMemory(final int memory) throws Exception {
        if (memory <= Runtime.getRuntime().maxMemory()) {
            return memory;
        }
        throw new Exception("Memory requested too high");
    }

    public boolean isTweakble() {
        return true;
    }
}
