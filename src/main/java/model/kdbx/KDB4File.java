package model.kdbx;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.codec.DecoderException;

public class KDB4File extends KDBFile {

    private static final int SKIP = 12;
    private ByteBuffer currentStream;
    private KDB4Header header;
    public KDB4File(final FileInputStream stream) {
        super(stream);
        try {
            header = new KDB4Header();
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        currentStream.order(ByteOrder.LITTLE_ENDIAN);
    }

    @SuppressWarnings("unused")
    private void readHeader(final FileInputStream stream) {
        int fieldId = 0;
        int length = 0;
        byte[] data;
        try {
            stream.skip(KDB4File.SKIP);
            currentStream = ByteBuffer.wrap(stream.readAllBytes());
            while (true) {
                fieldId = (int) currentStream.get();
                length = currentStream.getShort();
                data = new byte[length];
                if (length > 0) {
                    currentStream.get(data, 0, length);
                    // Add data to header
                    // TODO
                } else if (length == 0) {
                    this.setHeaderLength(currentStream.arrayOffset());
                }
            }
        } catch (IOException e) {
            System.out.println("Error KDB4File reading header: " + e.toString());
            e.printStackTrace();
        }
    }

    private void decrypt(final FileInputStream stream) {
       // String cipherName = this.header.getCiphers().get("AES");
       // TODO take cipher from CipherID header and factory it
    }
}
