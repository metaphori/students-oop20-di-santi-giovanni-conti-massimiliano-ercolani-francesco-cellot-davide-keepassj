package model.kdbx;

import java.io.OutputStream;

import model.crypto.CipherFactory;
import model.crypto.CryptoCipher;

public class KDBWriter {

    private final KDBHeader header;
    private final OutputStream stream;

    /**
     * Class to write KDB to file.
     * @param header Header of the file with the various options.
     * @param stream OutputStream of the file.
     */
    public KDBWriter(final KDBHeader header, final OutputStream stream) {
        this.header = header;
        this.stream = stream;
    }

    /**
     * This method write the Database to the file.
     */
    public void write() {
    }

    private int encrypt() {
        CipherFactory cipherFactory = new CipherFactory();
        CryptoCipher cipher = cipherFactory.getCipher("AES", null);
        return 0;
    }
}
