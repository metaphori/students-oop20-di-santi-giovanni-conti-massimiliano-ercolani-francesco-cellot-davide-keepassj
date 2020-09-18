package controller;

public class DBDataSaverImpl implements DBDataSaver{
    
    byte[] dbName;
    byte[] dbDesc;
    String cipher;
    String kdf;
    Integer rounds;
    Integer memory;
    Integer parallelism;
    
    @Override
    public void takeDBName(final byte[] dbName) {
        this.dbName = dbName;
    }

    @Override
    public void takeDBDesc(final byte[] dbDesc) {
        this.dbDesc = dbDesc;
    }

    @Override
    public void takeCipher(String cipher) {
        this.cipher = cipher;
    }

    @Override
    public void takeKdf(String kdf) {
        this.kdf = kdf;
    }

    @Override
    public void takeRounds(Integer rounds) {
        this.rounds = rounds;
    }

    @Override
    public void takeMemory(Integer memory) {
        this.memory = memory;
    }

    @Override
    public void takeParallelism(Integer parallelism) {
        this.parallelism = parallelism;
    }

}
