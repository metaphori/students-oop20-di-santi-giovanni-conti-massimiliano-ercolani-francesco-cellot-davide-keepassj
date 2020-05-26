package model.kdbx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import model.crypto.CipherFactory;
import model.crypto.CryptoCipher;
import model.crypto.Util;

public class KDBReader extends KDBFile {

    private static final int SIGNATURE_LENGTH = 12;
    private final KDBHeader header;

    public final KDBHeader getHeader() {
        return header;
    }

    public KDBReader(final InputStream stream, final List<byte[]> credentials) {
        super(stream, credentials);
        header = new KDBHeader();
        this.readHeader();
        try {
            this.decrypt();
        } catch (IOException e) {
            System.out.println("Error decryption: " + e.toString());
        }
    }

    private void readHeader() {
        int fieldId = 0;
        int length = 0;
        byte[] data;
        this.inputByteBuffer.position(KDBReader.SIGNATURE_LENGTH);
        while (true) {
            fieldId = (int) this.inputByteBuffer.get();
            if (!this.header.checkField(fieldId)) {
                break;
            }
            length = this.inputByteBuffer.getShort();
            data = new byte[length];
            if (length > 0) {
                this.inputByteBuffer.get(data, 0, length);
                // Add data to header
                this.header.setField(fieldId, data);
            }
            if (fieldId == 0) {
                this.setHeaderLength(this.inputByteBuffer.position());
                break;
            }
        }
    }

    protected final void decrypt() throws IOException {
        super.decrypt();
        CipherFactory cipherFactory = new CipherFactory();
        CryptoCipher cipher = cipherFactory.getCipher(this.header.getCipher());
        cipher.setKey(this.masterKey);
        byte[] encrypted = new byte[this.inputByteBuffer.remaining()];
        this.inputByteBuffer.get(encrypted);
        /*
        System.out.println("Length encrypted: " + encrypted.length);
        System.out.println("IV: " + Hex.encodeHexString(this.header.getEncryptionIV()));
        System.out.println("Master key: " + Hex.encodeHexString(this.masterKey));
        */
        byte[] dec = cipher.decrypt(encrypted, this.header.getEncryptionIV());
        int lengthCheck = this.header.getStreamStartBytes().length;
        if (Arrays.equals(this.header.getStreamStartBytes(), 0, lengthCheck, dec, 0, lengthCheck)) {
            byte[] toHash = new byte[dec.length - lengthCheck];
            System.arraycopy(dec, lengthCheck, toHash, 0, toHash.length);
            HashedBlock hashedBlock = new HashedBlock(toHash);
        } else {
            throw new IOException("Corrupted data found during decryption");
        }
        // System.out.println(new String(dec));
    }

    protected final void makeMasterKey() {
        try {
            super.makeMasterKey();
            List<Byte> hashedKeys = new ArrayList<>();
            for (final byte[] k: this.keys) {
                for (final byte b: k) {
                    hashedKeys.add(b);
                }
            }
            // Ugly way
            byte[] composite = new byte[hashedKeys.size()];
            for (int i = 0; i < hashedKeys.size(); i++) {
                composite[i] = (byte) hashedKeys.toArray()[i];
            }
            final byte[] temporaryKey = Util.transformKey(Util.sha256(composite), this.header.getTransformSeed(),
                    this.header.getTransformRounds());
            byte[] masterSeed = this.header.getMasterSeed();
            byte[] masterKey = new byte[temporaryKey.length + masterSeed.length];
            System.arraycopy(masterSeed, 0, masterKey, 0, masterSeed.length);
            System.arraycopy(temporaryKey, 0, masterKey, masterSeed.length, temporaryKey.length);
            this.masterKey = Util.sha256(masterKey);
        } catch (NullPointerException e) {
            System.out.println("Error no keys: " + e.toString());
        }
    }
}
