package model.kdbx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.exception.NullArgumentException;

import model.crypto.Util;

public class KDBFile {

    /**
     * This is the master Key.
     */
    private byte[] masterKey;
    /**
     * This is the ByteBuffer of the file.
     */
    private ByteBuffer inputByteBuffer;
    /**
     * This is the list of keys used to decrypt the database.
     */
    private List<byte[]> keys;
    /**
     * Abstract class to define common operation between all KDB versions.
     */
    public KDBFile(Builder builder) {
        ;
    }

    public static class Builder {

        private byte[] masterKey;
        private OutputStream outStream;
        private InputStream inStream;
        private List<byte[]> keys;

        public static Builder newInstance() {
            return new Builder();
        }

        public final Builder setOutStream(final OutputStream stream) {
            this.outStream = stream;
            return this;
        }

        public final Builder setInStream(final InputStream stream) {
            this.inStream = stream;
            return this;
        }

        public final KDBFile build() {
            return new KDBFile(this);
        }
    }

}
