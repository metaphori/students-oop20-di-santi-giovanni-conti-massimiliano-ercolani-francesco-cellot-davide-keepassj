package model.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES implements CryptoCipher {

    /**
     * BLOCKSIZE This is the Block Size of AES.
     */
    public static final int BLOCKSIZE = 16;

    private final byte[] iv;
    private Cipher cipher;
    private SecureRandom random;
    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec aesKey;

    /**
     * Construct an AES Object.
     * @param key This is 16/24/32 bytes key
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     */
    public AES(final byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this(key, null);
    }

    /**
     * Construct an AES Object.
     * @param key This is a 16/24/32 bytes key.
     * @param iv This is the IV to use for the encryption. If null than a random one is generated.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public AES(final byte[] key, final byte[] iv) {
        this.cipher = null;
        this.random = new SecureRandom();
        this.iv = new byte[AES.BLOCKSIZE];
        try {
            this.random = new SecureRandom();
            this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
            if (iv == null) {
                this.random.nextBytes(this.iv);
            } else {
                System.arraycopy(iv, 0, this.iv, 0, iv.length);
            }
            this.ivParameterSpec = new IvParameterSpec(this.iv);
            this.aesKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.out.println("Error building AES object: " + e.toString());
        }
    }

    /**
     * Return the AES key.
     * @return key.
     */
    public final SecretKeySpec getKey() {
        return this.aesKey;
    }

    /**
     * Set AES key.
     * @param key 16/24/32 bytes key.
     */
    public void setKey(final byte[] key) {
        this.aesKey = new SecretKeySpec(key, "AES");
    }

    /**
     * Set IV for AES encryption.
     * @param iv
     */
    public void setIv(final byte[] iv) {
        this.ivParameterSpec = new IvParameterSpec(iv);
    }

    /**
     * Encrypt arbitrary with AES CBC plaintext.
     * @param plaintext This is the plaintext to encrypt.
     * @return ciphertext.
     */
    @Override
    public final byte[] encrypt(final byte[] plaintext) {
            try {
                this.cipher.init(Cipher.ENCRYPT_MODE, aesKey, this.ivParameterSpec);
                final byte[] plaintextPadded = Util.pad(plaintext, AES.BLOCKSIZE);
                final byte[] encrypted = this.cipher.doFinal(plaintextPadded);
                final byte[] ciphertext = new byte[AES.BLOCKSIZE + encrypted.length];
                System.arraycopy(this.iv, 0, ciphertext, 0, AES.BLOCKSIZE);
                System.arraycopy(encrypted, 0, ciphertext, AES.BLOCKSIZE, encrypted.length);
                return ciphertext;
            } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                    | BadPaddingException e) {
                System.out.println("Error AES encryption: " + e.toString());
                e.printStackTrace();
            }
        return null;
    }

    /**
     * @param ciphertext This is the ciphertext to decrypt with AES CBC.
     * It must be in the form IV + Ciphertext to be decrypted correctly.
     * @return plaintext
     */
    @Override
    public final byte[] decrypt(final byte[] ciphertext) {
        try {
            System.arraycopy(ciphertext, 0, this.iv, 0, AES.BLOCKSIZE);
            final int encryptedSize = ciphertext.length - AES.BLOCKSIZE;
            final byte[] encrypted = new byte[encryptedSize];
            System.arraycopy(ciphertext, AES.BLOCKSIZE, encrypted, 0, encryptedSize);
            this.ivParameterSpec = new IvParameterSpec(this.iv);
            this.cipher.init(Cipher.DECRYPT_MODE, this.aesKey, this.ivParameterSpec);
            return Util.unpad(this.cipher.doFinal(encrypted));
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException | IllegalAccessException e) {
            System.out.println("Error AES decryption: " + e.toString());
            e.printStackTrace();
        }
        return null;
    }

}
