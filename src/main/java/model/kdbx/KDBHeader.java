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
import model.crypto.KDFBadParameter;

public class KDBHeader {

    private static final byte[] SIGNATURE = {(byte) 0xdb, (byte) 0xdb, (byte) 0xdb, (byte) 0xdb};
    private static final byte[] END_OF_HEADER = {(byte) 0, (byte) 0, (byte) 0};
    private final Map<Integer, byte[]> fields;
    private final EnumMap<Field, Integer> headerFields;
    private KDF currentKDF;

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

    /**
     * Parse the database header.
     * @param fileData byte array of the database to parse.
     * @return position of the ciphertext.
     * @throws IOException If the header is corrupted.
     */
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

    /**
     * Byte array representation of the header.
     * @return byte array of the header.
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

    /**
     * Get the cipher descriptions.
     * @return Map of nameCipher:descriptionCipher.
     */
    public final Map<String, String> getCipherDescriptions() {
        return cipherDescriptions;
    }

    /**
     * Get the KDF descriptions.
     * @return Map of nameKDF:descriptionKDF.
     */
    public final Map<String, String> getKDFDescriptions() {
        return kdfDescriptions;
    }

    /**
     * Get current cipher.
     * @return cipher algorithm.
     */
    public final String getCipher() {
        return this.ciphers.get(new String(Hex.encodeHex(this.getFieldData(Field.CIPHER_ID))));
    }

    /**
     * Get current KDF.
     * @return KDF algorithm.
     */
    public final String getKDF() {
        return this.kdfs.get(new String(Hex.encodeHex(this.getFieldData(Field.KDF_ID))));
    }

    /**
     * Get suggested KDF rounds for a given KDF.
     * @param kdf This is the KDF algorithm.
     * @return kdf rounds.
     */
    public final int getKDFRounds(final String kdf) {
        return KDFFactory.create(kdf).getDefaultRounds();
    }

    /**
     * Check if a KDF is tweakable, if is tweakable then memory and parallelism could be set.
     * @param kdf KDF algorithm.
     * @return is tweakable.
     */
    public final boolean isKDFTweakable(final String kdf) {
        return KDFFactory.create(kdf).isTweakable();
    }

    /**
     * Get master seed.
     * @return seed.
     */
    public final byte[] getMasterSeed() {
        return this.getFieldData(Field.MASTER_SEED);
    }

    /**
     * Get IV.
     * @return IV.
     */
    public final byte[] getEncryptionIV() {
        return this.getFieldData(Field.ENCRYPTION_IV);
    }

    /**
     * Get salt.
     * @return salt.
     */
    public final byte[] getTransformSeed() {
        return this.getFieldData(Field.TRANSFORM_SEED);
    }

    public final byte[] getStreamStartBytes() {
        return this.getFieldData(Field.STREM_START_BYTES);
    }

    /**
     * Get the selected KDF rounds.
     * @return rounds.
     */
    public final int getTransformRounds() {
        final ByteBuffer transformRound = ByteBuffer.wrap(this.getFieldData(Field.TRANSFORM_ROUNDS));
        transformRound.order(ByteOrder.LITTLE_ENDIAN);
        return transformRound.getInt();
    }

    /**
     * Get the selected KDF parallelism, appliable only if the KDF is tweakable.
     * @return parallelism.
     */
    public final int getKDFParallelism() {
        final ByteBuffer parallelismBuffer = ByteBuffer.wrap(this.getFieldData(Field.KDF_PARALLELISM));
        parallelismBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return parallelismBuffer.getInt();
    }

    /**
     * Get the maximum number of threads that a KDF could use.
     * @param kdf KDF algorithm.
     * @return threads.
     */
    public final int getKDFMaxParallelism(final String kdf) {
        return KDFFactory.create(kdf).getMaxParallelism();
    }

    /**
     * Get the selected KDF memory in use, appliable only if the KDF is tweakable.
     * @return memory.
     */
    public final int getKDFMemory() {
        final ByteBuffer memoryBuffer = ByteBuffer.wrap(this.getFieldData(Field.KDF_MEMORY));
        memoryBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return memoryBuffer.getInt();
    }

    /**
     * Get the maximum memory that a KDF could use.
     * @param kdf KDF algorithm.
     * @return memory.
     */
    public final int getKDFMaxMemory(final String kdf) {
        return KDFFactory.create(kdf).getMaxMemory();
    }

    /**
     * Set cipher to use.
     * @param cipher Cipher algorithm.
     */
    public final void setCipher(final String cipher) {
        final String key = ciphers.entrySet().stream()
                .filter(c -> c.getValue().equals(cipher))
                .findFirst()
                .get()
                .getKey();
        try {
            this.setField(Field.CIPHER_ID, Hex.decodeHex(key));
        } catch (final DecoderException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the KDF to use.
     * @param kdf KDF algorithm.
     */
    public final void setKDF(final String kdf) {
        final String key = kdfs.entrySet().stream()
                .filter(c -> c.getValue().equals(kdf))
                .findFirst()
                .get()
                .getKey();
        try {
            this.setField(Field.KDF_ID, Hex.decodeHex(key));
            final KDF k = KDFFactory.create(kdf);
            if (k.isTweakable()) {
                try {
                    this.setKDFParallelism(k.getDefaultParallelism());
                    this.setKDFMemory(k.getDefaultMemory());
                } catch (final KDFBadParameter e) {
                    System.out.println(e.toString() + "This shouldn't happen");
                }
            }
        } catch (final DecoderException e) {
            e.printStackTrace();
        }
        this.setTransformRounds(this.getKDFRounds(kdf));
    }

    public final void setMasterSeed(final byte[] masterSeed) {
        setField(Field.MASTER_SEED, masterSeed);
    }

    /**
     * Set the encryption IV of the header.
     * @param iv This is the IV.
     */
    public final void setEncryptionIV(final byte[] iv) {
        this.setField(Field.ENCRYPTION_IV, iv);
    }

    /**
     * Set the number of threads that the selected KDF should use. Appliable only if the KDF is tweakable.
     * @param parallelism Number of threads.
     * @throws KDFBadParameter if the parallelism is too high or too low.
     */
    public final void setKDFParallelism(final int parallelism) throws KDFBadParameter {
        currentKDF = KDFFactory.create(this.getKDF());
        if (currentKDF.isTweakable()) {
            currentKDF.setParallelism(parallelism);
            final ByteBuffer parallelismBuffer = ByteBuffer.allocate(Integer.BYTES);
            parallelismBuffer.order(ByteOrder.LITTLE_ENDIAN);
            parallelismBuffer.putInt(parallelism);
            parallelismBuffer.rewind();
            this.setField(Field.KDF_PARALLELISM, parallelismBuffer.array());
        }
    }

    /**
     * Set the memory that the selected KDF should use. Appliable only if the KDF is tweakable.
     * @param memory This is the memory to use.
     * @throws KDFBadParameter if the memory is too high or too low.
     */
    public final void setKDFMemory(final int memory) throws KDFBadParameter {
        currentKDF = KDFFactory.create(this.getKDF());
        if (currentKDF.isTweakable()) {
            currentKDF.setMemory(memory);
            final ByteBuffer memoryBuffer = ByteBuffer.allocate(Integer.BYTES);
            memoryBuffer.order(ByteOrder.LITTLE_ENDIAN);
            memoryBuffer.putInt(memory);
            memoryBuffer.rewind();
            this.setField(Field.KDF_MEMORY, memoryBuffer.array());
        }
    }

    /**
     * Set the number of rounds that the selected KDF should use.
     * @param rounds This is the number of rounds.
     */
    public void setTransformRounds(final int rounds) {
        final ByteBuffer transformRound = ByteBuffer.allocate(Integer.BYTES);
        transformRound.order(ByteOrder.LITTLE_ENDIAN);
        transformRound.putInt(rounds);
        transformRound.rewind();
        this.setField(Field.TRANSFORM_ROUNDS, transformRound.array());
    }

    /**
     * Set the database name.
     * @param comment This is the name of the database.
     */
    public final void setComment(final byte[] comment) {
        setField(Field.COMMENT, comment);
    }

    /**
     * Set the description of the database.
     * @param data This is the description of the database.
     */
    public final void setPublicCustomData(final byte[] data) {
        setField(Field.PUBLIC_CUSTOM_DATA, data);
    }

    private boolean checkField(final int fieldId) {
        final int max = this.headerFields.values().stream()
                .max((entry1, entry2) -> entry1 > entry2 ? 1 : -1)
                .get();
        final int min = this.headerFields.values().stream()
                .min((entry1, entry2) -> entry2 < entry2 ? 1 : -1)
                .get();
        return fieldId >= min && fieldId <= max;
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
        random.nextBytes(seed);
        this.setTransformSeed(seed);
    }

    private void setTransformSeed(final byte[] seed) {
        this.setField(Field.TRANSFORM_SEED, seed);
    }

    private void setField(final Field field, final byte[] value) {
        this.setField(this.headerFields.get(field), value);
    }

    private void setField(final int field, final byte[] value) {
        this.fields.remove(field);
        this.fields.put(field, value);
    }

    private byte[] getFieldData(final Field field) {
        return this.fields.get(this.headerFields.get(field));
    }

}
