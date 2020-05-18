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

public abstract class KDBFile {

    private Boolean opened;
    private int headerLength;

    public final List<byte[]> getKeys() {
        return keys;
    }

    public final void setKeys(final List<byte[]> keys) {
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

    protected final void setHeaderLength(final int headerLength) {
        this.headerLength = headerLength;
    }

    public final ByteBuffer getByteStream() {
        return inputByteBuffer;
    }

    public final void setByteStream(final ByteBuffer byteStream) {
        this.inputByteBuffer = byteStream;
    }

    /**
     * This is the master Key.
     */
    protected byte[] masterKey;
    /**
     * This is the ByteBuffer of the file.
     */
    protected ByteBuffer inputByteBuffer;
    /**
     * This is the list of keys used to decrypt the database.
     */
    protected List<byte[]> keys;
    /**
     * Abstract class to define common operation between all KDB versions.
     * @param stream This it the stream of the database.
     * @param credentials This is the list of credentials.
     */
    public KDBFile(final InputStream stream, final List<byte[]> credentials) {
        try {
            this.inputByteBuffer = ByteBuffer.wrap(stream.readAllBytes());
            this.inputByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        } catch (final IOException e) {
            System.out.println("Error reading stream KDBFile: " + e.toString());
        }
        this.keys = new ArrayList<>();
        this.opened = false;
        this.headerLength = 0;
        this.addKeys(credentials);
    }

    private void addKey(final byte[] key) {
        this.keys.add(Util.sha256(key));
    }

    private void addKeys(final List<byte[]> keys) {
        keys.stream().forEach(e -> this.addKey(e));
    }

    /**
     * Create master key to decrypt database.
     * @throws NullPointerException
     */
    protected void makeMasterKey() throws NullPointerException {
        if (this.keys.size() == 0) {
            throw new NullPointerException("Credentials empty in makeMasterKey");
        }
    }

    private void readFrom(final InputStream inputStream) {
        // TODO
    }

    /**
     * Decrypt the encrypted payload.
     * @throws IOException 
     */
    protected void decrypt() throws IOException {
        this.makeMasterKey();
        this.inputByteBuffer.position(this.getHeaderLength());
        this.inputByteBuffer.order(ByteOrder.BIG_ENDIAN);
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
