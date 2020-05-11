package model.kdbx;

import java.io.InputStream;

public class KDB4File extends KDBFile {

    private static final int SIGNATURE_LENGTH = 12;
    private KDB4Header header;

    public final KDB4Header getHeader() {
        return header;
    }

    public KDB4File(final InputStream stream) {
        super(stream);
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
            length = this.inputByteBuffer.getShort();
            data = new byte[length];
            if (length > 0 && fieldId >= 0 && fieldId <= 10) {
                this.inputByteBuffer.get(data, 0, length);
                // Add data to header
                this.header.setField(fieldId, data);
            } else {
                this.setHeaderLength(this.inputByteBuffer.arrayOffset());
                break;
            }
        }
    }

    public void setHeaderLength(final int arrayOffset) {
        // TODO Auto-generated method stub
    }

    private void decrypt(final InputStream stream) {
       // String cipherName = this.header.getCiphers().get("AES");
       // TODO take cipher from CipherID header and factory it
    }
}
