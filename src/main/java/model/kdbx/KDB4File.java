package model.kdbx;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import model.crypto.Util;

public class KDB4File extends KDBFile {

    private static final int SIGNATURE_LENGTH = 12;
    private final KDB4Header header;

    public final KDB4Header getHeader() {
        return header;
    }

    public KDB4File(final InputStream stream, final List<byte[]> credentials) {
        super(stream, credentials);
        header = new KDB4Header();
        this.readHeader();
    }

    private void readHeader() {
        int fieldId = 0;
        int length = 0;
        byte[] data;
        this.inputByteBuffer.position(KDB4File.SIGNATURE_LENGTH);
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
            } else {
                this.setHeaderLength(this.inputByteBuffer.arrayOffset());
                break;
            }
        }
    }

    private void decrypt(final InputStream stream) {
       // String cipherName = this.header.getCiphers().get("AES");
       // TODO take cipher from CipherID header and factory it
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
            final byte[] temporaryKey = Util.transformKey(composite, this.header.getTransformSeed(),
                    this.header.getTransformRounds());
            byte[] masterSeed = this.header.getMasterSeed();
            byte[] masterKey = new byte[temporaryKey.length + masterSeed.length];
            System.arraycopy(masterSeed, 0, masterKey, 0, masterSeed.length);
            System.arraycopy(temporaryKey, 0, masterKey, masterSeed.length, temporaryKey.length);
            this.masterKey = masterKey;
        } catch (NullPointerException e) {
            System.out.println("Error no keys: " + e.toString());
        }
    }
}
