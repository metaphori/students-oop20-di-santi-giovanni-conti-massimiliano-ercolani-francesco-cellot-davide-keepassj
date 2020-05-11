package model.kdbx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class KDB4File {

    private static final int SIGNATURE_LENGTH = 12;
    private ByteBuffer inputByteBuffer;
    private KDB4Header header;
    private InputStream inputStream;
    public KDB4File(final InputStream stream) {
        this.inputStream = stream;
        try {
            this.inputByteBuffer = ByteBuffer.wrap(this.inputStream.readAllBytes());
        } catch (IOException e) {
            System.out.println("Error reading stream KDBFile: " + e.toString());
        }
        try {
            header = new KDB4Header();
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        this.inputByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    @SuppressWarnings("unused")
    public final void readHeader() {
        int fieldId = 0;
        int length = 0;
        byte[] data;
        this.inputByteBuffer.position(KDB4File.SIGNATURE_LENGTH);
        while (true) {
            fieldId = (int) inputByteBuffer.get();
            length = inputByteBuffer.getShort();
            data = new byte[length];
            if (length > 0 && fieldId >= 0 && fieldId <= 10) {
                inputByteBuffer.get(data, 0, length);
                System.out.println(new String(data));
                // Add data to header
                this.header.setField(fieldId, data);
            } else {
                System.out.println(Hex.encodeHex(this.header.getField(Field.CIPHERID)));
                this.setHeaderLength(inputByteBuffer.arrayOffset());
                break;
            }
        }
    }

    private void setHeaderLength(final int arrayOffset) {
        // TODO Auto-generated method stub
    }

    private void decrypt(final FileInputStream stream) {
       // String cipherName = this.header.getCiphers().get("AES");
       // TODO take cipher from CipherID header and factory it
    }
}
