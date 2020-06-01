package model.crypto;

import java.util.Arrays;

import javax.crypto.Cipher;

public abstract class CryptoCipherAEAD implements CryptoCipher {

    /**
     * Bytes of associated data to be authenticated (not encrypted).
     */
    protected byte[] associatedData;
    /**
     * Cipher used to encrypt/decrypt.
     */
    protected Cipher cipher;

    public final void updateAssociatedData(final byte[] data) {
        this.associatedData = Arrays.copyOf(data, data.length);
    }

    protected final void updateAAD() {
        if (this.associatedData != null) {
            this.cipher.updateAAD(this.associatedData);
        }
    }
}
