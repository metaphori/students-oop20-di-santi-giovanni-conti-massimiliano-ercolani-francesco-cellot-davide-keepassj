package model.kdbx;

import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import java.util.EnumMap;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class KDB4Header {

    private EnumMap<Field, Integer> headerFields;
    private Map<Integer, byte[]> fields;
    private final Map<String, String> ciphers;
    private Map<Integer, String> protectedStreams;
    public final void setFields(final EnumMap<Field, Integer> headerFields) {
        this.headerFields = headerFields;
    }
    public final Map<String, String> getCiphers() {
        return ciphers;
    }
    public final Map<Integer, String> getProtectedStreams() {
        return protectedStreams;
    }
    public final void setProtectedStreams(final Map<Integer, String> protectedStreams) {
        this.protectedStreams = protectedStreams;
    }
    public final void setField(final Field field, final byte[] value) {
        this.fields.put(this.headerFields.get(field), value);
    }
    public final void setField(final int field, final byte[] value) {
        this.fields.put(field, value);
    }
    public final byte[] getField(final Field field) {
        return this.fields.get(this.headerFields.get(field));
    }
    public final byte[] getField(final int field) {
        return this.fields.get(field);
    }
    public final Map<Integer, byte[]> getFields() {
        return this.fields;
    }
    public final String getCipher() {
        return this.ciphers.get(new String(Hex.encodeHex(this.getField(Field.CIPHERID))));
    }

    KDB4Header() throws DecoderException {
        this.headerFields = new EnumMap<>(Field.class);
        this.headerFields.put(Field.END_OF_HEADER, 0);
        this.headerFields.put(Field.COMMENT, 1);
        this.headerFields.put(Field.CIPHERID, 2);
        this.headerFields.put(Field.COMPRESSIONFLAGS, 3);
        this.headerFields.put(Field.MASTER_SEED, 4);
        this.headerFields.put(Field.TRANSFORM_SEED, 5);
        this.headerFields.put(Field.TRANSFORM_ROUNDS, 6);
        this.headerFields.put(Field.ENCRYPTION_IV, 7);
        this.headerFields.put(Field.PROTECTED_STREAM_KEY, 8);
        this.headerFields.put(Field.STREM_START_BYTES, 9);
        this.headerFields.put(Field.INNER_RANDOM_STREAM_ID, 10);
        this.fields = new HashMap<>();
        this.ciphers = Map.ofEntries(
                entry("31c1f2e6bf714350be5805216afc5aff", "AES"),
                entry("ad68f29f576f4bb9a36ad47af965346c", "TwoFish"),
                entry("d6038a2b8b6f4cb5a524339a31dbb59a", "ChaCha20")
                );
        this.protectedStreams = Map.ofEntries(
                entry(1, "ArcFourVariant"),
                entry(2, "Salsa20")
                );
    }
}
