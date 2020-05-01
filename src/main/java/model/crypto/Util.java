package model.crypto;

import java.util.Arrays;
/**
 * Class with frequent cryptography utilities.
 *
 */
public class Util {

    /**
     * Compute SHA256 of an arbitrary message.
     * @param message This is the message to be hashed.
     * @return sha256 of message.
     */
    byte[] sha256(final byte[] message) {
        //TODO
        return null;
    }

    /**
     * Transform key with seed rounds times using AES ECB.
     * @param key
     * @param seed
     * @param rounds
     * @return key transformed
     */
    byte[] transformKey(final byte[] key, final byte[] seed, final int rounds) {
        // TODO
        return null; }

    /**
     * PCKS7 unpad.
     * @param message The message to unpad.
     * @return unpadded The message PKCS7 unpadded.
     */
    static byte[] unpad(final byte[] message) throws IllegalAccessException { 
        int padded = message[message.length - 1];
        for (int i = message.length - 1; i >= message.length - padded; i--) {
            if (message[i] != (byte) padded) {
                throw new IllegalAccessException("PKCS7 Padding error");
            }
        }
        byte[] unpadded = new byte[message.length - padded];
        System.arraycopy(message, 0, unpadded, 0, unpadded.length);
        return unpadded;
    }

    /**
     * PCKS7 pad.
     * @param message The message to pad.
     * @return padded The message PKCS7 padded. 
     */
    static byte[] pad(final byte[] message, final int blockSize) {
        int paddingLength = blockSize - (message.length % blockSize);
        byte[] paddedMessage = new byte[message.length + paddingLength];
        System.arraycopy(message, 0, paddedMessage, 0, message.length);
        Arrays.fill(paddedMessage, message.length, paddedMessage.length, (byte) paddingLength);
        return paddedMessage;
    }

    /**
     * XOR two messages.
     * @param firstMessage
     * @param secondMessage
     * @return XOR operation
     */
    byte[] xor(final byte[] firstMessage, final byte[] secondMessage) {
        // TODO
        return null;
    }

}
