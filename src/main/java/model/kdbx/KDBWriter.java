package model.kdbx;

import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Hex;

import com.google.common.primitives.Bytes;

import model.crypto.CipherFactory;
import model.crypto.CryptoCipher;
import model.crypto.KDF;
import model.crypto.KDFFactory;
import model.crypto.Util;

public class KDBWriter {

    private final KDBHeader header;
    private final OutputStream stream;
    private SecureRandom random;
    private byte[] compositeKey;

    /**
     * Class to write KDB to file.
     * @param header Header of the file with the various options.
     * @param stream OutputStream of the file.
     * @param credentials These are the list of credentials to use.
     */
    public KDBWriter(final KDBHeader header, final OutputStream stream, final List<byte[]> credentials) {
        this.header = header;
        this.stream = stream;
        this.random = new SecureRandom();
        this.composeKey(credentials);
    }

    /**
     * This method write the Database to the file.
     * @param plaintext This is the plaintext to encrypt.
     */
    public void write(final byte[] plaintext) {
        byte[] headerData = encrypt(plaintext);
        try {
            this.stream.write(headerData);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private byte[] encrypt(final byte[] plaintext) {
        final String cipherType = this.header.getCipher();
        final CryptoCipher cipher = CipherFactory.create(cipherType);

        final String kdfType = this.header.getKDF();
        final KDF kdf = KDFFactory.create(kdfType);

        final byte [] newIV = new byte[16];
        this.random.nextBytes(newIV);
        this.header.setEncryptionIV(newIV);

        final byte[] key = kdf.generateKey(this.compositeKey, this.header.getTransformSeed(), (int) this.header.getTransformRounds());
        cipher.setKey(key);

        final byte[] headerData = this.header.dataToBytes();
        final byte[] ciphertext = cipher.encrypt(plaintext, newIV);
        return Bytes.concat(headerData, ciphertext);
    }

    private void composeKey(final List<byte[]> credentials) {
        this.compositeKey = Util.sha256(Bytes.toArray(credentials.stream()
                .map(key -> Util.sha256(key))
                .map(key -> Bytes.asList(key))
                .flatMap(x -> x.stream())
                .collect(Collectors.toList())));
    }
}
