package model.kdbx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

public abstract class KDBFile {

    private List<Byte[]> keys;
    private Boolean opened;
    private int headerLength;
    public final List<Byte[]> getKeys() {
        return keys;
    }

    public final void setKeys(final List<Byte[]> keys) {
        this.keys = keys;
    }

    public final Boolean getOpened() {
        return opened;
    }

    public final void setOpened(final Boolean opened) {
        this.opened = opened;
    }

    public final int getHeaderLength() {
        return headerLength;
    }

    public final void setHeaderLength(final int headerLength) {
        this.headerLength = headerLength;
    }

    public final ByteBuffer getByteStream() {
        return byteStream;
    }

    public final void setByteStream(final ByteBuffer byteStream) {
        this.byteStream = byteStream;
    }

    private ByteBuffer byteStream;
    /**
     * Abstract class to define common operation between all KDB versions.
     * @param stream This it the stream of the database.
     */
    public KDBFile(final FileInputStream stream) {
        try {
            byteStream = ByteBuffer.wrap(stream.readAllBytes());
        } catch (IOException e) {
            System.out.println("Error reading stream KDBFile: " + e.toString());
        }
        this.keys = null;
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
