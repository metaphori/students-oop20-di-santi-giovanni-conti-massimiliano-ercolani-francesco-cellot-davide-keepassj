package model.crypto;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Arrays;

import javax.crypto.AEADBadTagException;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class AEADAES implements CryptoCipher {

    /**
     * cipher This is the cipher in use.
     */
    protected Cipher cipher;
    /**
     * hmac This is the MAC in use.
     */
    protected Mac hmac;
    /**
     * encKey This is the encryption/decryption key.
     */
    protected SecretKeySpec encKey;
    /**
     * macKey This is the authentication key.
     */
    protected SecretKeySpec macKey;
    /**
     * TODO.
     */
    protected byte[] associatedData;
    /**
     * TODO.
     */
    protected byte[] associatedDataLength; 

    /**
     * {@inheritDoc}
     */
    public void updateAssociatedData(final byte[] data) {
        this.associatedData = Arrays.copyOf(data, data.length);
        final ByteBuffer ad = ByteBuffer.allocate(8);
        ad.order(ByteOrder.BIG_ENDIAN);
        ad.putLong(data.length * 8);
        ad.rewind();
        this.associatedDataLength = ad.array();
    }

    protected final void initCipher(final int mode, final byte[] iv) throws InvalidKeyException, InvalidAlgorithmParameterException {
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        this.cipher.init(mode, this.encKey, ivParameterSpec);
        this.hmac.init(this.macKey);
    }

    protected final void setKey(final byte[] key, final int encSize, final int macSize, final String cipherName,
            final String macName) {
        final byte[] encKey = new byte[encSize];
        final byte[] macKey = new byte[macSize];
        System.arraycopy(key, 0, macKey, 0, macKey.length);
        System.arraycopy(key, macKey.length, encKey, 0, encKey.length);
        this.encKey = new SecretKeySpec(encKey, cipherName);
        this.macKey = new SecretKeySpec(macKey, macName);
    }

    protected final SecretKeySpec getKey() {
        return this.encKey;
    }
}
