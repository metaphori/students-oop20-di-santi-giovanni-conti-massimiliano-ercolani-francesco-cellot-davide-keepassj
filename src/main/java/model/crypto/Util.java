package model.crypto;

/**
 * Interface with frequent cryptography utilities.
 *
 */
public interface Util {

    /**
     * Compute SHA256 of an arbitrary message.
     * @param message
     * @return sha256 of message
     */
    byte[] sha256(byte[] message);

    /**
     * Transform key with seed rounds times using AES ECB.
     * @param key
     * @param seed
     * @param rounds
     * @return key transformed
     */
    byte[] transformKey(byte[] key, byte[] seed, int rounds);

    /**
     * PCKS7 unpad.
     * @param message
     * @return unpadded message
     */
    byte[] unpad(byte[] message);

    /**
     * PCKS7 pad.
     * @param message
     * @return padded message
     */
    byte[] pad(byte[] message);

    /**
     * XOR two messages.
     * @param firstMessage
     * @param secondMessage
     * @return XOR operation
     */
    byte[] xor(byte[] firstMessage, byte[] secondMessage);

}
