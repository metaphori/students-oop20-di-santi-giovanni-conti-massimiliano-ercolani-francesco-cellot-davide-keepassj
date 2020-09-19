package controller;

/**
 * 
 * A class that implements DBDataSaver.
 * It takes the value inserted during the creation of the db
 * and gives them back with the getters at the end of the creation
 * to set them.
 *
 */
public class DBDataSaverImpl implements DBDataSaver{
    
    private byte[] dbName;
    private byte[] dbDesc;
    private String cipher;
    private String kdf;
    private long rounds;
    private Integer memory;
    private Integer parallelism;
    private boolean isTweakable;
    
    @Override
    public void takeDBName(final byte[] dbName) {
        this.dbName = dbName;
    }

    @Override
    public void takeDBDesc(final byte[] dbDesc) {
        this.dbDesc = dbDesc;
    }

    @Override
    public void takeCipher(final String cipher) {
        this.cipher = cipher;
    }

    @Override
    public void takeKdf(final String kdf) {
        this.kdf = kdf;
    }

    @Override
    public void takeRounds(final long rounds) {
        this.rounds = rounds;
    }

    @Override
    public void takeMemory(final Integer memory) {
        this.memory = memory;
    }

    @Override
    public void takeParallelism(final Integer parallelism) {
        this.parallelism = parallelism;
    }

    @Override
    public void isTweakable(final boolean isTweakable) {
        this.isTweakable = isTweakable;
    }

    @Override
    public byte[] getDBName() {
        return this.dbName;
    }

    @Override
    public byte[] getDBDesc() {
        return this.dbDesc;
    }

    @Override
    public String getCipher() {
        return this.cipher;
    }

    @Override
    public String getKdf() {
        return this.kdf;
    }

    @Override
    public long getRounds() {
        return this.rounds;
    }

    @Override
    public Integer getMemory() {
        return this.memory;
    }

    @Override
    public Integer getParallelism() {
        return this.parallelism;
    }

    @Override
    public boolean getTweakable() {
        return this.isTweakable;
    }

}
