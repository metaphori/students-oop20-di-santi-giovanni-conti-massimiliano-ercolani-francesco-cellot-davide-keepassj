package model.kdbx;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Hex;

import com.google.common.primitives.Bytes;

import model.crypto.CipherFactory;
import model.crypto.CryptoCipher;
import model.crypto.KDF;
import model.crypto.KDFFactory;
import model.crypto.Util;

public class KDBReader {

    private static final int SIGNATURE_LENGTH = 12;
    private final KDBHeader header;
    private List<byte[]> keys;
    private int headerLength;
    private ByteBuffer inputByteBuffer;
    private byte[] masterKey;

    public final KDBHeader getHeader() {
        return header;
    }

    public KDBReader(final InputStream stream, final List<byte[]> credentials) {
        try {
            this.inputByteBuffer = ByteBuffer.wrap(stream.readAllBytes());
            this.inputByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        } catch (final IOException e) {
            System.out.println("Error reading stream KDBFile: " + e.toString());
        }
        this.keys = new ArrayList<>();
        this.headerLength = 0;
        this.addKeys(credentials);
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
            fieldId = -1;
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
                this.headerLength = this.inputByteBuffer.position();
                break;
            }
        }
    }

    protected final void decrypt() throws IOException {
        this.makeMasterKey();
        this.inputByteBuffer.position(this.headerLength);
        this.inputByteBuffer.order(ByteOrder.BIG_ENDIAN);
        final CryptoCipher cipher = CipherFactory.create(this.header.getCipher());
        cipher.setKey(this.masterKey);
        final byte[] encrypted = new byte[this.inputByteBuffer.remaining()];
        this.inputByteBuffer.get(encrypted);
        /*
        System.out.println("Length encrypted: " + encrypted.length);
        System.out.println("IV: " + Hex.encodeHexString(this.header.getEncryptionIV()));
        System.out.println("Master key: " + Hex.encodeHexString(this.masterKey));
        */
        final byte[] dec = cipher.decrypt(encrypted, this.header.getEncryptionIV());
        final int lengthCheck = this.header.getStreamStartBytes().length;
        if (Arrays.equals(this.header.getStreamStartBytes(), 0, lengthCheck, dec, 0, lengthCheck)) {
            final byte[] toHash = new byte[dec.length - lengthCheck];
            System.arraycopy(dec, lengthCheck, toHash, 0, toHash.length);
            System.out.println(new String(toHash));
            // HashedBlock hashedBlock = new HashedBlock(toHash);
        } else {
            throw new IOException("Corrupted data found during decryption");
        }
        // System.out.println(new String(dec));
    }

    protected final void makeMasterKey() {
        this.header.writeHeader();
        System.out.println(this.keys.size());
        if (this.keys.size() == 0) {
            System.out.println("Error no keys: ");
        }
        byte[] composite = Bytes.toArray(this.keys.stream()
                .map(key -> Bytes.asList(key))
                .flatMap(x -> x.stream())
                .collect(Collectors.toList()));
        final byte[] temporaryKey = Util.transformKey(Util.sha256(composite), this.header.getTransformSeed(),
                this.header.getTransformRounds());
        byte[] masterSeed = this.header.getMasterSeed();
        this.masterKey = Util.sha256(Bytes.concat(masterSeed, temporaryKey));
    }

    private void addKey(final byte[] key) {
        this.keys.add(Util.sha256(key));
    }

    private void addKeys(final List<byte[]> keys) {
        keys.stream().forEach(e -> this.addKey(e));
    }
}
