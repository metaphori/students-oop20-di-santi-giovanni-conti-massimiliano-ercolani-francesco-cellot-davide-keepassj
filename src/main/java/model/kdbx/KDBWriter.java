package model.kdbx;

import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Hex;

import model.crypto.CipherFactory;
import model.crypto.CryptoCipher;

public class KDBWriter {

    private final KDBHeader header;
    private final OutputStream stream;
    private SecureRandom random;

    /**
     * Class to write KDB to file.
     * @param header Header of the file with the various options.
     * @param stream OutputStream of the file.
     */
    public KDBWriter(final KDBHeader header, final OutputStream stream) {
        this.header = header;
        this.stream = stream;
        this.random = new SecureRandom();
    }

    /**
     * This method write the Database to the file.
     */
    public void write() {
        byte[] headerData = encrypt();
        try {
            this.stream.write(headerData);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private byte[] encrypt() {
        CipherFactory cipherFactory = new CipherFactory();
        String cipherType = this.header.getCipher();
        CryptoCipher cipher = cipherFactory.getCipher(cipherType);
        byte [] newIV = new byte[16];
        this.random.nextBytes(newIV);
        this.header.setEncryptionIV(newIV);
        // byte[] newKey = new byte[32];
        // this.random.nextBytes(newKey);
        byte[] headerData = this.header.dataToBytes();
        return headerData;
        // byte[] getbyte[] get
    }
}
