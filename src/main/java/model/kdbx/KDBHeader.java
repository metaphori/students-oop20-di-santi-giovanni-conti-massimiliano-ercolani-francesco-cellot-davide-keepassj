package model.kdbx;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Bytes;

import model.crypto.CipherFactory;
import model.crypto.KDFFactory;
import model.crypto.KDF;

public class KDBHeader {

    private static final byte[] SIGNATURE = {(byte) 0xdb, (byte) 0xdb, (byte) 0xdb, (byte) 0xdb};
    private static final byte[] END_OF_HEADER = {(byte) 0, (byte) 0, (byte) 0};
    private static final long DEFAULT_ROUNDS = 15;
    private Map<Integer, byte[]> fields;

    private final Map<String, String> ciphers = ImmutableMap.of(
            "8eb0132c227519353e44de6fc1df241d", "ChaCha20Poly1305",
            "4922bdc5a59a674fffb648a7b9e5b59c", "AESGCM",
            "31c1f2e6bf714350be5805216afc5aff", "AES"
            );

    private final Map<String, String> kdfs = ImmutableMap.of(
            "33d8bdb9f1bf67a7467bca59eccb18b0", "PBKDF2",
            "ef636ddf8c29444b91f7a9a403e30a0c", "Argon2",
            "9cacaaf3cce9c43908274ed3c3c6eb1c", "Scrypt"
            );

    private final Map<String, String> cipherDescriptions = ImmutableMap.of(
            "ChaCha20Poly1305", "ChaCha20-Poly1305 Is the most secure way to encrypt a message",
            "AESGCM", "AES-GCM Is the way to go when you don't want to use modern technologies",
            "AES", "AES-256-CBC-HMAC-SHA-512 Is one of the classical authenticated encryption scheme"
            );

    private final Map<String, String> kdfDescriptions = ImmutableMap.of(
            "Argon2", "Is the most secure key derivation function",
            "Scrypt", "Is one of the most secure key derivation functions",
            "PBKDF2", "Is one of the classical password based key derivation functions"
            );

    private EnumMap<Field, Integer> headerFields;

    @SuppressWarnings("MagicNumber")
    public KDBHeader() {
        this.headerFields = new EnumMap<>(Field.class);
        this.headerFields.put(Field.END_OF_HEADER, 0);
        this.headerFields.put(Field.COMMENT, 1);
        this.headerFields.put(Field.CIPHER_ID, 2);
        this.headerFields.put(Field.MASTER_SEED, 4);
        this.headerFields.put(Field.TRANSFORM_SEED, 5);
        this.headerFields.put(Field.TRANSFORM_ROUNDS, 6);
        this.headerFields.put(Field.ENCRYPTION_IV, 7);
        this.headerFields.put(Field.PROTECTED_STREAM_KEY, 8);
        this.headerFields.put(Field.STREM_START_BYTES, 9);
        this.headerFields.put(Field.KDF_PARAMETERS, 11);
        this.headerFields.put(Field.PUBLIC_CUSTOM_DATA, 12);
        this.headerFields.put(Field.KDF_ID, 13);
        this.headerFields.put(Field.KDF_PARALLELISM, 14);
        this.headerFields.put(Field.KDF_MEMORY, 15);
        this.fields = new HashMap<>();

        this.setDefaults();
    }

    public final int readHeader(final byte[] fileData) throws IOException {
        // byte[] allBytes = inStream.readAllBytes();
        final ByteBuffer inputByteBuffer = ByteBuffer.wrap(fileData);
        inputByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        int fieldId = 0;
        int length = 0;
        byte[] data;
        inputByteBuffer.position(KDBHeader.SIGNATURE.length);
        while (true) {
            fieldId = -1;
            fieldId = (int) inputByteBuffer.get();
            if (!this.checkField(fieldId)) {
                break;
            }
            length = inputByteBuffer.getShort();
            data = new byte[length];
            if (length > 0) {
                inputByteBuffer.get(data, 0, length);
                // Add data to header
                this.setField(fieldId, data);
            }
            if (fieldId == 0) {
                return inputByteBuffer.position();
            }
        }
        throw new IOException();
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

    public final EnumMap<Field, Integer> getHeaderFields() {
        return headerFields;
    }

    public final Map<Integer, byte[]> getFields() {
        return fields;
    }

    public final Map<String, String> getCiphers() {
        return ciphers;
    }

    public final Map<String, String> getCipherDescriptions() {
        return cipherDescriptions;
    }

    public final Map<String, String> getKDFDescriptions() {
        return kdfDescriptions;
    }

    public final byte[] getFieldData(final Field field) {
        return this.fields.get(this.headerFields.get(field));
    }

    public final String getCipher() {
        return this.ciphers.get(new String(Hex.encodeHex(this.getFieldData(Field.CIPHER_ID))));
    }

    public final String getKDF() {
        return this.kdfs.get(new String(Hex.encodeHex(this.getFieldData(Field.KDF_ID))));
    }

	/**
	* Get suggested KDF rounds for a given KDF.
	* @param kdf
	* @return rounds.
	 */
	public final int getKDFRounds(final String kdf) {
		return KDFFactory.create(kdf).getDefaultRounds();
	}

	/**
	* Check if a KDF is tweakable, if is tweakable then memory and parallelism could be set.
	* @param kdf
	* @return is tweakable.
	 */
	public final boolean isKDFTweakable(final String kdf) {
		return KDFFactory.create(kdf).isTweakable();
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
        final ByteBuffer transformRound = ByteBuffer.wrap(this.getFieldData(Field.TRANSFORM_ROUNDS));
        transformRound.order(ByteOrder.LITTLE_ENDIAN);
        return transformRound.getLong();
    }

    public final int getKDFParallelism() {
        final ByteBuffer parallelismBuffer = ByteBuffer.wrap(this.getFieldData(Field.KDF_PARALLELISM));
        parallelismBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return parallelismBuffer.getInt();
    }

    public final long getKDFMemory() {
        final ByteBuffer memoryBuffer = ByteBuffer.wrap(this.getFieldData(Field.KDF_MEMORY));
        memoryBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return memoryBuffer.getLong();
    }

    /**
     * Write Header in ByteBuffer.
     * @return List of ByteBuffer.
     */
    public final byte[] writeHeader() {
        final byte[] dataBuffer = Bytes.toArray(this.fields.entrySet().stream()
                .map(value -> fieldToBytes(value.getKey(), value.getValue()))
                .map(buffer -> buffer.array())
                .map(byteArray -> Bytes.asList(byteArray))
                .flatMap(listArray -> listArray.stream())
                .collect(Collectors.toList()));
        // System.out.println(Hex.encodeHex(dataBuffer));
        // dataBuffer.forEach(a -> System.out.println(Hex.encodeHex(a)));
        return Bytes.concat(KDBHeader.SIGNATURE, dataBuffer, KDBHeader.END_OF_HEADER);
    }

    private ByteBuffer fieldToBytes(final int key, final byte[] data) {
        // Field ID + Length + Payload
        final ByteBuffer buffer = ByteBuffer.allocate(3 + data.length);
        System.out.println(key + ": " + Hex.encodeHexString(data));
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte) key);
        buffer.putShort((short) data.length);
        buffer.put(data);
        buffer.rewind();
        return buffer;
    }

    private void setDefaults() {
        final String defaultCipher = "ChaCha20Poly1305";
        final String defaultKDF = "Argon2";
        final SecureRandom random = new SecureRandom();
        final byte [] seed = new byte[CipherFactory.create(defaultCipher).getIVSize()];
        this.setCipher(defaultCipher);
        this.setKDF(defaultKDF);
        this.setTransformRounds(KDBHeader.DEFAULT_ROUNDS);
        random.nextBytes(seed);
        this.setTransformSeed(seed);
    }

    private void setTransformRounds(final long rounds) {
        final ByteBuffer transformRound = ByteBuffer.allocate(8);
        transformRound.order(ByteOrder.LITTLE_ENDIAN);
        transformRound.putLong(rounds);
        transformRound.rewind();
        this.setField(Field.TRANSFORM_ROUNDS, transformRound.array());
    }

    private void setTransformSeed(final byte[] seed) {
        this.setField(Field.TRANSFORM_SEED, seed);
    }

    public final void setField(final Field field, final byte[] value) {
        this.setField(this.headerFields.get(field), value);
        // return this;
        // this.fields.put(this.headerFields.get(field), value);
    }

    public final void setField(final int field, final byte[] value) {
        this.fields.remove(field);
        this.fields.put(field, value);
        // return this;
    }

    public final void setHeaderFields(final EnumMap<Field, Integer> headerFields) {
        this.headerFields = headerFields;
    }

    public final void setFields(final Map<Integer, byte[]> fields) {
        this.fields = fields;
    }

    public final void setCipher(final String cipher) {
        final String key = ciphers.entrySet().stream()
                .filter(c -> c.getValue().equals(cipher))
                .findFirst()
                .get()
                .getKey();
        try {
            this.setField(Field.CIPHER_ID, Hex.decodeHex(key));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

    public final void setKDF(final String kdf) {
        final String key = kdfs.entrySet().stream()
                .filter(c -> c.getValue().equals(kdf))
                .findFirst()
                .get()
                .getKey();
        try {
            this.setField(Field.KDF_ID, Hex.decodeHex(key));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

    public final void setMasterSeed(final byte[] masterSeed) {
        setField(Field.MASTER_SEED, masterSeed);
    }

    public final void setEncryptionIV(final byte[] iv) {
        this.setField(Field.ENCRYPTION_IV, iv);
    }

    public final void setKDFParallelism(final int parallelism) {
        final ByteBuffer parallelismBuffer = ByteBuffer.allocate(4);
        parallelismBuffer.order(ByteOrder.LITTLE_ENDIAN);
        parallelismBuffer.putInt(parallelism);
        parallelismBuffer.rewind();
        this.setField(Field.KDF_PARALLELISM, parallelismBuffer.array());
    }

    public final void setKDFMemory(final long memory) {
        final ByteBuffer memoryBuffer = ByteBuffer.allocate(8);
        memoryBuffer.order(ByteOrder.LITTLE_ENDIAN);
        memoryBuffer.putLong(memory);
        memoryBuffer.rewind();
        this.setField(Field.KDF_MEMORY, memoryBuffer.array());
    }

}
