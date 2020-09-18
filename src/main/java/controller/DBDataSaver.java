package controller;

/**
 * 
 * Interface to save database info selected during creation
 *
 */
public interface DBDataSaver {

    /**
     * Save the database name
     * @param dbName is the database name
     */
    void takeDBName(byte [] dbName);
    
    /**
     * Save the database description
     * @param dbDesc is the database description
     */
    void takeDBDesc(byte [] dbDesc);
    
    /**
     * Save the database cipher
     * @param cipher
     */
    void takeCipher(String cipher);
    
    /**
     * Save the database key derivation function
     * @param kdf
     */
    void takeKdf(String kdf);
    
    /**
     * Save the database transform rounds
     * @param rounds
     */
    void takeRounds(Integer rounds);
    
    /**
     * Save the database memory usage
     * @param memory
     */
    void takeMemory(Integer memory);
    
    /**
     * Save the database parallelism
     * @param parallelism
     */
    void takeParallelism(Integer parallelism);
    
    
    
}
