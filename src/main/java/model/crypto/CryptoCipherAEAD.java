package model.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class CryptoCipherAEAD implements CryptoCipher {

    /**
     * Bytes of associated data to be authenticated (not encrypted).
     */
    protected byte[] associatedData;
    /**
     * Cipher used to encrypt/decrypt.
     */
    protected Cipher cipher;
    protected SecretKeySpec encKey;
    protected SecretKeySpec macKey;
    protected Mac hmac;

    @Override
    public final void updateAssociatedData(final byte[] data) {
        this.associatedData = Arrays.copyOf(data, data.length);
    }

    protected final void updateAAD() {
        if (this.associatedData != null) {
            this.cipher.updateAAD(this.associatedData);
        }
    }

    protected final void initCipher(final int mode, final byte[] iv) throws InvalidKeyException, InvalidAlgorithmParameterException {
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        this.cipher.init(mode, this.encKey, ivParameterSpec);
        this.hmac.init(this.macKey);
    }
}
