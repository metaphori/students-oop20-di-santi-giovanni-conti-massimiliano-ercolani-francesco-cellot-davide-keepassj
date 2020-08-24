package model.crypto;

import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public abstract class CryptoCipherAEAD implements CryptoCipher {

    /**
     * Bytes of associated data to be authenticated (not encrypted).
     */
    protected byte[] associatedData;
    /**
     * Cipher used to encrypt/decrypt.
     */
    protected Cipher cipher;

    @Override
    public final void updateAssociatedData(final byte[] data) {
        this.associatedData = Arrays.copyOf(data, data.length);
    }

    protected final void updateAAD() {
        if (this.associatedData != null) {
            this.cipher.updateAAD(this.associatedData);
        }
    }

    protected final byte[] doFinal(final byte[] input) throws IllegalBlockSizeException, BadPaddingException {
        this.updateAAD();
        return this.cipher.doFinal(input);
    }

}
