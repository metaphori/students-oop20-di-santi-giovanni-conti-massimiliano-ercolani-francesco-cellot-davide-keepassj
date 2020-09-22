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
    void takeDBName(String dbName);
    
    /**
     * Save the database description
     * @param dbDesc is the database description
     */
    void takeDBDesc(String dbDesc);
    
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
    
    /**
     * Save tweakable info
     * @param isTweakable
     */
    void isTweakable(boolean isTweakable);
    
    /**
     * Get database name
     * @return database name
     */
    String getDBName();
    
    /**
     * Get database description
     * @return database description
     */
    String getDBDesc();
    
    /**
     * Get database cipher  
     * @return database cipher
     */
    String getCipher();
    
    /**
     * Get database kdf
     * @return database kdf
     */
    String getKdf();
    
    /**
     * Get database key transform rounds
     * @return kdf
     */
    Integer getRounds();
    
    /**
     * Get database memory usage
     * @return memory usage
     */
    Integer getMemory();
    
    /**
     * Get parallelism 
     * @return parallelism
     */
    Integer getParallelism();
    
    /**
     * Get if chosen kdf is tweakable or not
     * @return true or false
     */
    boolean getTweakable();
    
    
}
