package model.kdbx;

/**
 * KDBX Fields.
 */
public enum Field {
    /**
     * Marks the End of the header.
     */
    END_OF_HEADER,
    /**
     * Comment of the database.
     */
    COMMENT,
    /**
     * ID of the cipher used to encrypt the database.
     */
    CIPHERID,
    /**
     * Check if the database is compressed.
     */
    COMPRESSION_FLAGS,
    /**
     * MasterSeed.
     */
    MASTER_SEED,
    /**
     * TransformSeed.
     */
    TRANSFORM_SEED,
    /**
     * TransformRounds.
     */
    TRANSFORM_ROUNDS,
    /**
     * IV used during encryption/decryption.
     */
    ENCRYPTION_IV,
    /**
     * Check if the password is encrypted with Salsa20.
     */
    PROTECTED_STREAM_KEY,
    /**
     * Position of the start bytes.
     */
    STREM_START_BYTES,
    /**
     * InnerRandomStreamID.
     */
    INNER_RANDOM_STREAM_ID,
    /**
     * Key Derivation functions parameters.
     */
    KDF_PARAMETERS,
    /**
     * Public Custom Data
     */
    PUBLIC_CUSTOM_DATA
}
