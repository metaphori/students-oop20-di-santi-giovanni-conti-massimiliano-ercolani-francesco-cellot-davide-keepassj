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
    CIPHER_ID,
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
    KDF_PARAMETERS,
    /**
     * Public Custom Data.
     */
    PUBLIC_CUSTOM_DATA,
    /**
     * KDF ID.
     */
    KDF_ID,
    /**
     * Thread used in KDF.
     */
    KDF_PARALLELISM,
    /**
     * Amount of memory used in KDF.
     */
    KDF_MEMORY
}
