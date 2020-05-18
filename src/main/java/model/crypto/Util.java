package model.crypto;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
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
    public static final byte[] sha256(final byte[] message) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error SHA-256 Object: " + e.toString());
        }
        return digest.digest(message);
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
    public static byte[] unpad(final byte[] message) throws BadPaddingException { 
        int padded = message[message.length - 1];
        for (int i = message.length - 1; i >= message.length - padded; i--) {
            if (message[i] != (byte) padded) {
                throw new BadPaddingException("PKCS7 Padding error");
            }
        }
        byte[] unpadded = new byte[message.length - padded];
        System.arraycopy(message, 0, unpadded, 0, unpadded.length);
        return unpadded;
    }

    /**
     * PCKS7 pad.
     * @param message The message to pad.
     * @param blockSize The block size of the message to pad.
     * @return padded The message PKCS7 padded. 
     */
    public static byte[] pad(final byte[] message, final int blockSize) {
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

    /**
     * TODO USE AES-KDF.
     * @param composite
     * @param transformSeed
     * @param transformRounds
     * @return key.
     */
    public static byte[] transformKey(final byte[] composite, final byte[] transformSeed,
            final long transformRounds) {
        Cipher aes = null;
        SecretKeySpec seed = null;
        byte[] key = new byte[composite.length];
        System.arraycopy(composite, 0, key, 0, composite.length);
        try {
            aes = Cipher.getInstance("AES/ECB/NoPadding");
            seed = new SecretKeySpec(transformSeed, "AES");
            aes.init(Cipher.ENCRYPT_MODE, seed);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: No AES ECB: " + e.toString());
        } catch (NoSuchPaddingException e) {
            System.out.println("Error: No AES ECB No Padding: " + e.toString());
        } catch (InvalidKeyException e) {
            System.out.println("AES ECB Invalid key: " + e.toString());
        }
        for (int i = 0; i < transformRounds; i++) {
            try {
                key = aes.doFinal(key);
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
        }
        return Util.sha256(key);
    }

    /**
     * Compute HMAC256 of a message.
     * @param key This is the key used to compute the MAC.
     * @param message This is the message to be authenticated.
     * @return tag.
     */
    public static byte[] hmac256(final byte[] key, final byte[] message) {
        Mac sha256HMAC = null;
        try {
            sha256HMAC = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
        try {
            sha256HMAC.init(secretKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return sha256HMAC.doFinal(message);
    }

}
