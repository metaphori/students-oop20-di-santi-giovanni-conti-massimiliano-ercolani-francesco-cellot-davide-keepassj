package model.crypto;

import java.util.Arrays;

import javax.crypto.Cipher;

public abstract class CryptoCipherAEAD implements CryptoCipher {

    protected byte[] associatedData;
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
