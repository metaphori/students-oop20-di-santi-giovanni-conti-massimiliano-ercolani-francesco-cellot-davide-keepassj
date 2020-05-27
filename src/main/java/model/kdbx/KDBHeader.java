package model.kdbx;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.codec.binary.Hex;

import com.google.common.primitives.Bytes;

public class KDBHeader {

    private EnumMap<Field, Integer> headerFields;
    private Map<Integer, byte[]> fields;
    private final Map<String, String> ciphers;
    private final Map<Integer, String> protectedStreams;

    public KDBHeader() {
        this.headerFields = new EnumMap<>(Field.class);
        this.headerFields.put(Field.END_OF_HEADER, 0);
        this.headerFields.put(Field.COMMENT, 1);
        this.headerFields.put(Field.CIPHERID, 2);
        this.headerFields.put(Field.COMPRESSION_FLAGS, 3);
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

    public final void setFields(final Map<Integer, byte[]> fields) {
        this.fields = fields;
    }

    public final Map<String, String> getCiphers() {
        return ciphers;
    }

    public final Map<Integer, String> getProtectedStreams() {
        return protectedStreams;
    }

    public final void setField(final Field field, final byte[] value) {
        this.setField(this.headerFields.get(field), value);
        // this.fields.put(this.headerFields.get(field), value);
    }

    public final void setField(final int field, final byte[] value) {
        this.fields.remove(field);
        this.fields.put(field, value);
    }

    public final byte[] getFieldData(final Field field) {
        return this.fields.get(this.headerFields.get(field));
    }

    public final String getCipher() {
        return this.ciphers.get(new String(Hex.encodeHex(this.getFieldData(Field.CIPHERID))));
    }

    public final boolean getCompressionFlag() {
        return this.getFieldData(Field.COMPRESSION_FLAGS)[0] == 1;
    }

    public final boolean checkField(final int fieldId) {
        final int max = this.headerFields.values().stream()
                                                    .max((entry1, entry2) -> entry1 > entry2 ? 1 : -1)
                                                    .get();
        final int min = this.headerFields.values().stream()
                                                    .min((entry1, entry2) -> entry2 < entry2 ? 1 : -1)
                                                    .get();
        return fieldId >= min && fieldId <= max;
    }

    public final byte[] getMasterSeed() {
        return this.getFieldData(Field.MASTER_SEED);
    }

    public final byte[] getEncryptionIV() {
        return this.getFieldData(Field.ENCRYPTION_IV);
    }

    public final byte[] getTransformSeed() {
        return this.getFieldData(Field.TRANSFORM_SEED);
    }

    public final byte[] getStreamStartBytes() {
        return this.getFieldData(Field.STREM_START_BYTES);
    }

    public final long getTransformRounds() {
        ByteBuffer transformRound = ByteBuffer.wrap(this.getFieldData(Field.TRANSFORM_ROUNDS));
        transformRound.order(ByteOrder.LITTLE_ENDIAN);
        return transformRound.getLong();
    }

    public final void setCipher(final byte[] cipher) {
        this.setField(Field.CIPHERID, cipher);
    }

    public final void setCompressionFlag(final byte[] flag) {
        this.setField(Field.COMPRESSION_FLAGS, flag);
    }

    public final void setMasterSeed(final byte[] masterSeed) {
        this.setField(Field.MASTER_SEED, masterSeed);
    }

    public final void setEncryptionIV(final byte[] iv) {
        this.setField(Field.ENCRYPTION_IV, iv);
    }

    /**
     * Write Header in ByteBuffer.
     * @return List of ByteBuffer.
     */
    public final byte[] dataToBytes() {
        byte[] dataBuffer = Bytes.toArray(this.fields.entrySet().stream()
                .map(value -> headerInfo(value.getKey(), value.getValue()))
                .map(buffer -> buffer.array())
                .map(byteArray -> Bytes.asList(byteArray))
                .flatMap(listArray -> listArray.stream())
                .collect(Collectors.toList()));
                /*
                .collect(
                        () -> new ByteArrayOutputStream(),
                        (outputStream, value) -> {
                            try {
                                outputStream.write(value);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        },
                        (a, b) -> { }).toByteArray();
                        */
        // System.out.println(Hex.encodeHex(dataBuffer));
        // dataBuffer.forEach(a -> System.out.println(Hex.encodeHex(a)));
        return dataBuffer;
    }

    private ByteBuffer headerInfo(final int key, final byte[] data) {
        // Field ID + Length + Payload
        ByteBuffer buffer = ByteBuffer.allocate(3 + data.length);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte) key);
        buffer.putShort((short) data.length);
        buffer.put(data);
        buffer.rewind();
        return buffer;
    }

    /*
     * USELESS PROBABLY
    private int dataToKey(final byte[] data) {
        return this.fields.entrySet().stream()
                                     .filter(entry -> data.equals(entry.getValue()))
                                     .findFirst()
                                     .get()
                                     .getKey();
    }
    */

}
