package model.kdbx;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class KDB4File {

    private static final int SIGNATURE_LENGTH = 12;
    private ByteBuffer currentStream;
    private KDB4Header header;
    public KDB4File(final FileInputStream stream) {
        try {
            this.currentStream = ByteBuffer.wrap(stream.readAllBytes());
        } catch (IOException e) {
            System.out.println("Error reading stream KDBFile: " + e.toString());
        }
        try {
            header = new KDB4Header();
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        this.currentStream.order(ByteOrder.LITTLE_ENDIAN);
    }

    @SuppressWarnings("unused")
    private void readHeader(final FileInputStream stream) {
        int fieldId = 0;
        int length = 0;
        byte[] data;
        try {
            if (stream.skip(KDB4File.SIGNATURE_LENGTH) != KDB4File.SIGNATURE_LENGTH) {
                throw new IOException("Error KDB4File skipping header");
            }
            currentStream = ByteBuffer.wrap(stream.readAllBytes());
            while (true) {
                fieldId = (int) currentStream.get();
                length = currentStream.getShort();
                data = new byte[length];
                if (length > 0) {
                    currentStream.get(data, 0, length);
                    // Add data to header
                    this.header.setField(fieldId, data);
                } else if (length == 0) {
                    System.out.println(Hex.encodeHex(this.header.getField(Field.CIPHERID)));
                    this.setHeaderLength(currentStream.arrayOffset());
                }
            }
        } catch (IOException e) {
            System.out.println("Error KDB4File reading header: " + e.toString());
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
