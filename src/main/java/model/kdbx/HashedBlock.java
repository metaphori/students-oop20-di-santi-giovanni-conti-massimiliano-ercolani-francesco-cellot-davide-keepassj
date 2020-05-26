package model.kdbx;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

import model.crypto.Util;

public class HashedBlock {

    private static final int HASH_SIZE = 32;
    private ByteBuffer inputBuffer;
    // private ByteArrayOutputStream outputStream;

    public HashedBlock(final byte[] data) {
        inputBuffer = ByteBuffer.wrap(data);
        this.readBlock();
    }

    private void readBlock() {
        while (true) {
            try {
                byte[] data = this.nextBlock();
                if (data == null) {
                    break;
                }
            } catch (IOException e) {
                System.out.println("Error reading hash block: " + e.toString());
            }
        }
    }

    private byte[] nextBlock() throws IOException {
        int index = readInt();
        byte[] blockHash = new byte[HASH_SIZE];
        this.inputBuffer.get(blockHash);
        int length = readInt();
        if (length > 0) {
            byte[] data = new byte[length];
            this.inputBuffer.get(data);
            if (Arrays.equals(Util.sha256(data), blockHash)) {
                // System.out.println("Hash Block Authenticated");
                return data;
            } else {
                throw new IOException("Hash Block corrupted");
            }
        }
        return null;
    }

    private int readInt() {
        this.inputBuffer.order(ByteOrder.LITTLE_ENDIAN);
        final int res = this.inputBuffer.getInt();
        this.inputBuffer.order(ByteOrder.BIG_ENDIAN);
        return res;
    }
}
