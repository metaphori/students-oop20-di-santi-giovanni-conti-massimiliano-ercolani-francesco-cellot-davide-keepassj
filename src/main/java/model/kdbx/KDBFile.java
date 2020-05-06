package model.kdbx;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class KDBFile {

    private final List<Byte[]> keys;
    private final Boolean opened;
    private final int headerLength;
    public KDBFile(final List<Byte[]> keys) {
        super();
        this.keys = keys;
        this.opened = false;
        this.headerLength = 0;
    }

    private void readFrom(final InputStream inputStream) {
        // TODO
    }

    private void decrypt(final InputStream inputStream) {
        // TODO
    }

    private void makeMasterKey() {
        // TODO
    }

    public void addKeyHash(final Byte[] keyHash) {
        // TODO
    }

    public void writeTo(final OutputStream outputStream) {
        // TODO
    }

    public void addCredentials(final Map<String, Byte[]> credentials) {
        // TODO
    }

    public void clearCredentials() {
        // TODO
    }

    public void close() {
        // TODO
    }

    public void seek(final int offset) {
        // TODO
    }

}
