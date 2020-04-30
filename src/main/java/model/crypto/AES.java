package model.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AES implements CryptoCipher {

    private byte[] key;
    private byte[] iv;
    private int ivSize;

    /**
     * Construct an AES Object.
     * @param key
     */
    public AES(final byte[] key) {
        this.key = key;
        this.ivSize = 16;
    }

    /**
     * Return the AES key.
     * @return key
     */
    public final byte[] getKey() {
        return key;
    }

    /**
     * Set AES key.
     * @param key
     */
    public void setKey(final byte[] key) {
        this.key = key;
    }

    @Override
    public final byte[] encrypt(final String plaintext) {
        try {
            this.iv = new byte[ivSize];
            SecureRandom random = new SecureRandom();
            random.nextBytes(this.iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.key, "AES");
            Cipher aes = Cipher.getInstance("AES/CBC/PKCS7Padding");
            aes.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            return aes.doFinal(plaintext.getBytes());
        } catch (Exception e) {
            System.out.println(e);
        }
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final String decrypt(final String ciphertext) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Encrypt plaintext with AES 256 CBC.
     * @param key
     * @param plaintext
     * @return ciphertext
     */
    public final String shit(final byte[] key, final String plaintext) {
        //TODO
        return null;
    }

}
