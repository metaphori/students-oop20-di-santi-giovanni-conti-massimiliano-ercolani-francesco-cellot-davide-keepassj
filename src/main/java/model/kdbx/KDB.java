package model.kdbx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
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

public class KDB {

    private KDBHeader header;
    private File database;
    private InputStream inStream;
    private OutputStream outStream;
    private ByteBuffer inputByteBuffer;
    private SecureRandom random;
    private byte[] compositeKey;

    /**
     * Create KDB Object.
     * @param database
     * @param credentials
     * @throws FileNotFoundException 
     */
    public KDB(final File database, final List<byte[]> credentials) throws FileNotFoundException {
        this.database = database;
        this.random = new SecureRandom();
        this.composeKey(credentials);
    }

    public KDB(final File database, final List<byte[]> credentials, final KDBHeader header) throws IOException {
        this(database, credentials);
        this.header = header;
    }

    public final void read() throws FileNotFoundException {
        this.header = new KDBHeader();
        this.inStream = new FileInputStream(this.database);
        int offset = 0;
        try {
            offset = this.header.readHeader(this.inStream);
        } catch (IOException e) {
            System.out.println("Error file has invalid header");
            return;
        }
        this.inStream = new FileInputStream(this.database);
        try {
            this.inStream.skip(offset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] ciphertext = null;
        try {
            ciphertext = this.inStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final byte[] plaintext = decrypt(ciphertext);
        System.out.println(new String(plaintext));
    }

    public final void write(final byte[] plaintext) throws FileNotFoundException {
        this.outStream = new FileOutputStream(this.database);
        final byte[] ciphertext = encrypt(plaintext);
        try {
            this.outStream.write(Bytes.concat(this.header.writeHeader(), ciphertext));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private byte[] decrypt(final byte[] ciphertext) {
        final String cipherType = this.header.getCipher();
        final CryptoCipher cipher = CipherFactory.create(cipherType);

        final String kdfType = this.header.getKDF();
        final KDF kdf = KDFFactory.create(kdfType);

        final byte[] key = kdf.generateKey(this.compositeKey, this.header.getTransformSeed(), (int) this.header.getTransformRounds());
        cipher.setKey(key);
        return cipher.decrypt(ciphertext, this.header.getEncryptionIV());
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

        final byte[] ciphertext = cipher.encrypt(plaintext, newIV);
        return ciphertext;
    }

    private void composeKey(final List<byte[]> credentials) {
        this.compositeKey = Util.sha256(Bytes.toArray(credentials.stream()
                .map(key -> Util.sha256(key))
                .map(key -> Bytes.asList(key))
                .flatMap(x -> x.stream())
                .collect(Collectors.toList())));
    }
}
