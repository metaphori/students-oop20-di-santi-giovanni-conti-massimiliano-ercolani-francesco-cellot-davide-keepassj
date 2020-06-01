package model.kdbx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.AEADBadTagException;

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
    private SecureRandom random;
    private CryptoCipher cipher;
    private KDF kdf;
    private byte[] compositeKey;

    /**
     * Create KDB Object.
     * @param database File of the database file.
     * @param credentials List of the credentials.
     * @throws FileNotFoundException When the database doesn't exist.
     */
    public KDB(final File database, final List<byte[]> credentials) throws FileNotFoundException {
        this.database = database;
        this.random = new SecureRandom();
        this.composeKey(credentials);
    }

    /**
     * Create KDB Object and database file.
     * @param database File where the database must be saved.
     * @param credentials List of the credentials.
     * @param header KDBHeader to use.
     * @throws IOException When is not possible to write on the file.
     */
    public KDB(final File database, final List<byte[]> credentials, final KDBHeader header) throws IOException {
        this(database, credentials);
        this.header = header;
    }

    /**
     * Decrypt the database.
     * @return plaintext.
     * @throws FileNotFoundException When the file doesn't exist.
     * @throws AEADBadTagException When the decryption fails.
     */
    public final byte[] read() throws FileNotFoundException, AEADBadTagException {
        this.header = new KDBHeader();
        this.inStream = new FileInputStream(this.database);
        int offset = 0;
        byte[] data = null;
        try {
            data = this.inStream.readAllBytes();
            offset = this.header.readHeader(data);
        } catch (IOException e) {
            System.out.println("Error file has invalid header");
            return null;
        }
        byte[] ciphertext = Arrays.copyOfRange(data, offset, data.length);
        final byte[] plaintext = decrypt(ciphertext);
        System.out.println(new String(plaintext));
        return plaintext;
    }

    /**
     * Encrypt the plaintext inside the database.
     * @param plaintext This is the XML data of the file to be encrypted.
     * @throws FileNotFoundException When the file doesn't exist.
     */
    public final void write(final byte[] plaintext) throws FileNotFoundException {
        this.outStream = new FileOutputStream(this.database);
        final byte[] ciphertext = encrypt(plaintext);
        try {
            this.outStream.write(Bytes.concat(this.header.writeHeader(), ciphertext));
        } catch (IOException e) {
            System.out.println("Error writing to the file");
        }
    }

    private byte[] decrypt(final byte[] ciphertext) throws AEADBadTagException {
        this.initializeCryptoAlgorithms();
        this.initializeCipher();
        return cipher.decrypt(ciphertext, this.header.getEncryptionIV());
    }

    private byte[] encrypt(final byte[] plaintext) {
        this.initializeCryptoAlgorithms();
        final byte [] newIV = new byte[cipher.getIVSize()];
        this.random.nextBytes(newIV);
        this.header.setEncryptionIV(newIV);
        this.initializeCipher();
        return this.cipher.encrypt(plaintext, newIV);
    }

    private void initializeCryptoAlgorithms() {
        final String cipherType = this.header.getCipher();
        final String kdfType = this.header.getKDF();
        this.cipher = CipherFactory.create(cipherType);
        this.kdf = KDFFactory.create(kdfType);
        this.kdf.setKeySize(this.cipher.getKeySize());
    }

    private void initializeCipher() {
        final byte[] key = kdf.generateKey(this.compositeKey, this.header.getTransformSeed(), (int) this.header.getTransformRounds());
        this.cipher.setKey(key);
        this.cipher.updateAssociatedData(this.header.writeHeader());
    }

    private void composeKey(final List<byte[]> credentials) {
        this.compositeKey = Util.sha256(Bytes.toArray(credentials.stream()
                .map(key -> Util.sha256(key))
                .map(key -> Bytes.asList(key))
                .flatMap(x -> x.stream())
                .collect(Collectors.toList())));
    }
}
