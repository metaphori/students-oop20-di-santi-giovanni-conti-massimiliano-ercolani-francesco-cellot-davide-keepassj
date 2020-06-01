package model.crypto;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.google.common.primitives.Bytes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AES implements CryptoCipher {

    /*
     * Only a draft, but requested as a standard in TLS.
     * https://tools.ietf.org/html/draft-mcgrew-aead-aes-cbc-hmac-sha2-05
     */
    /**
     * BLOCKSIZE This is the Block Size of AES.
     */
    public static final int BLOCK_SIZE = 16;
    /**
     * IV_SIZE This is the IV Size of AES-CBC.
     */
    private static final int IV_SIZE = 16;
    /**
     * KEY_SIZE This is the key size of AES-CBC.
     */
    private static final int KEY_SIZE = 64;
    private static final int ENC_SIZE = 32;
    private static final int MAC_SIZE = 32;
    private static final int TAG_SIZE = 32;
    private Cipher cipher;
    private SecretKeySpec encKey;
    private SecretKeySpec macKey;
    private Mac hmac;
    private byte[] associatedData;
    private byte[] associatedDataLength; 

    /**
     * Construct an AES Object.
     */
    public AES() {
        try {
            this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
            this.hmac = Mac.getInstance("HmacSHA512");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.out.println("Error building AES object: " + e.toString());
        }
    }

    /**
     * Return the AES key.
     * @return key.
     */
    public final SecretKeySpec getKey() {
        return this.encKey;
    }

    /**
     * Set AES key.
     * @param key 16/24/32 bytes key.
     */
    public void setKey(final byte[] key) {
        final byte[] encKey = new byte[ENC_SIZE];
        final byte[] macKey = new byte[MAC_SIZE];
        System.arraycopy(key, 0, macKey, 0, macKey.length);
        System.arraycopy(key, macKey.length, encKey, 0, encKey.length);
        this.encKey = new SecretKeySpec(encKey, "AES");
        this.macKey = new SecretKeySpec(macKey, "HmacSHA512");
    }

    /**
     * Encrypt arbitrary with AES CBC plaintext.
     * @param plaintext This is the plaintext to encrypt.
     * @param iv This is the IV used in AES CBC to encrypt correctly the plaintext.
     * @return ciphertext.
     */
    @Override
    public final byte[] encrypt(final byte[] plaintext, final byte[] iv) {
        try {
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            this.cipher.init(Cipher.ENCRYPT_MODE, this.encKey, ivParameterSpec);
            this.hmac.init(this.macKey);

            final byte[] encrypted = this.cipher.doFinal(Util.pad(plaintext, AES.BLOCK_SIZE));
            // I set the iv only to test the correct tag using the parameters of the ietf draft.
            final byte[] tag = this.computeHmac(hmac, Bytes.concat(iv, encrypted));
            return Bytes.concat(encrypted, tag);
        } catch (InvalidKeyException | BadPaddingException  | IllegalBlockSizeException 
                | InvalidAlgorithmParameterException e) {
            System.out.println("Error AES encryption: " + e.toString());
        }
        return null;
    }

    /**
     * AES CBC Decrypt arbitrary ciphertext.
     * @param ciphertext This is the ciphertext to decrypt with AES CBC.
     * @param iv This is the IV used in AES CBC to decrypt correctly the ciphertext.
     * @return plaintext.
     * @throws BadPaddingException 
     */
    @Override
    public final byte[] decrypt(final byte[] ciphertext, final byte[] iv) throws AEADBadTagException {
        try {
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            this.cipher.init(Cipher.DECRYPT_MODE, this.encKey, ivParameterSpec);
            this.hmac.init(this.macKey);

            final byte[] encrypted = new byte[ciphertext.length - TAG_SIZE];
            System.arraycopy(ciphertext, 0, encrypted, 0, encrypted.length);
            final byte[] tag = new byte[TAG_SIZE];
            System.arraycopy(ciphertext, encrypted.length, tag, 0, tag.length);

            final byte[] tagComputed = this.computeHmac(hmac, Bytes.concat(iv, encrypted));
            if (!Arrays.equals(tag, tagComputed)) {
                throw new AEADBadTagException();
            }
            return Util.unpad(this.cipher.doFinal(encrypted));
        } catch (InvalidKeyException | IllegalBlockSizeException | InvalidAlgorithmParameterException 
                | BadPaddingException e) {
            if (e instanceof AEADBadTagException) {
                throw new AEADBadTagException("Error " + this.getClass() + " tag mismatch");
            } else {
                System.out.println("Error " + this.getClass() + " this shouldn't happen: " + e.toString());
            }
        }
        return null;
    }

    /**
     * Return IV Size.
     */
    @Override
    public final int getIVSize() {
        return AES.IV_SIZE;
    }

    /**
     * Return key size.
     */
    @Override
    public int getKeySize() {
        return AES.KEY_SIZE;
    }

    @Override
    public final void updateAssociatedData(final byte[] data) {
        this.associatedData = Arrays.copyOf(data, data.length);
        final ByteBuffer ad = ByteBuffer.allocate(8);
        ad.order(ByteOrder.BIG_ENDIAN);
        ad.putLong(data.length * 8);
        ad.rewind();
        this.associatedDataLength = ad.array();
    }


    private byte[] computeHmac(final Mac hmac, final byte[] encrypted) {
        final byte[] data = Bytes.concat(this.associatedData, encrypted, this.associatedDataLength);
        return Arrays.copyOfRange(hmac.doFinal(data), 0, TAG_SIZE);
    }
}
