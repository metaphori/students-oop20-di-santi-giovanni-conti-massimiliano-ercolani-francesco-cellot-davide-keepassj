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

    private String dbName;
    private String dbDesc;
    private String cipher;
    private String kdf;
    private Integer rounds;
    private Integer memory;
    private Integer parallelism;
    private boolean isTweakable;

    @Override
    public final void takeDBName(final String dbName) {
        this.dbName = dbName;
    }

    @Override
    public final void takeDBDesc(final String dbDesc) {
        this.dbDesc = dbDesc;
    }

    @Override
    public final void takeCipher(final String cipher) {
        this.cipher = cipher;
    }

    @Override
    public final void takeKdf(final String kdf) {
        this.kdf = kdf;
    }

    @Override
    public final void takeRounds(final Integer rounds) {
        this.rounds = rounds;
    }

    @Override
    public final void takeMemory(final Integer memory) {
        this.memory = memory;
    }

    @Override
    public final void takeParallelism(final Integer parallelism) {
        this.parallelism = parallelism;
    }

    @Override
    public final void isTweakable(final boolean isTweakable) {
        this.isTweakable = isTweakable;
    }

    @Override
    public final String getDBName() {
        return this.dbName;
    }

    @Override
    public final String getDBDesc() {
        return this.dbDesc;
    }

    @Override
    public final String getCipher() {
        return this.cipher;
    }

    @Override
    public final String getKdf() {
        return this.kdf;
    }

    @Override
    public final Integer getRounds() {
        return this.rounds;
    }

    @Override
    public final Integer getMemory() {
        return this.memory;
    }

    @Override
    public final Integer getParallelism() {
        return this.parallelism;
    }

    @Override
    public final boolean isTweakable() {
        return this.isTweakable;
    }

}
