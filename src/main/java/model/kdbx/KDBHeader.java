package model.kdbx;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;
import org.apache.commons.codec.binary.Hex;

public abstract class KDBHeader {

    private EnumMap<Field, Integer> headerFields;
    private Map<Integer, byte[]> fields;
    private final Map<String, String> ciphers;
    private final Map<Integer, String> protectedStreams;

    public KDBHeader() {
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
        this.headerFields.put(Field.KDF_PARAMETERS, 11);
        this.headerFields.put(Field.PUBLIC_CUSTOM_DATA, 12);

        this.fields = new HashMap<>();

        this.ciphers = Map.ofEntries(
                entry("31c1f2e6bf714350be5805216afc5aff", "AES"),
                entry("ad68f29f576f4bb9a36ad47af965346c", "TwoFish"),
                entry("d6038a2b8b6f4cb5a524339a31dbb59a", "ChaCha20")
                );

        this.protectedStreams = Map.ofEntries(
                entry(1, "ArcFourVariant"),
                entry(2, "Salsa20"),
                entry(3, "ChaCha20")
                );
    }

    public final EnumMap<Field, Integer> getHeaderFields() {
        return headerFields;
    }

    public final void setHeaderFields(final EnumMap<Field, Integer> headerFields) {
        this.headerFields = headerFields;
    }

    public final Map<Integer, byte[]> getFields() {
        return fields;
    }

    public void setFields(final Map<Integer, byte[]> fields) {
        this.fields = fields;
    }

    public final Map<String, String> getCiphers() {
        return ciphers;
    }

    public final Map<Integer, String> getProtectedStreams() {
        return protectedStreams;
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

    public final String getCipher() {
        return this.ciphers.get(new String(Hex.encodeHex(this.getField(Field.CIPHERID))));
    }
}
