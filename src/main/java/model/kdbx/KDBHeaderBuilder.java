package model.kdbx;

public class KDBHeaderBuilder {

    private KDBHeader header;

    public final KDBHeaderBuilder newInstance() {
        this.header = new KDBHeader();
        return new KDBHeaderBuilder();
    }

    public final KDBHeader setCipher(final String cipher) {
        this.header.setCipher(cipher);
        return this.header;
    }

    public final KDBHeader setCompressionFlag(final byte[] flag) {
        this.header.setCompressionFlag(flag);
        return this.header;
    }

    public final KDBHeader setMasterSeed(final byte[] masterSeed) {
        this.header.setMasterSeed(masterSeed);
        return this.header;
    }

    public final KDBHeader setEncryptionIV(final byte[] iv) {
        this.header.setEncryptionIV(iv);
        return this.header;
    }

}
