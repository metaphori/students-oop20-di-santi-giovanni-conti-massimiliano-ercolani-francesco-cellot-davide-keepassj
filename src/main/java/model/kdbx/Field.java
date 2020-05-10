package model.kdbx;

/**
 * KDBX Fields.
 */
public enum Field {
    /**
     * Marks the End of the header.
     */
    EndOfHeader,
    /**
     * Comment of the database.
     */
    Comment,
    /**
     * ID of the cipher used to encrypt the database.
     */
    CipherID,
    /**
     * Check if the database is compressed.
     */
    CompressionFlags,
    /**
     * MasterSeed.
     */
    MasterSeed,
    /**
     * TransformSeed.
     */
    TransformSeed,
    /**
     * TransformRounds.
     */
    TransformRounds,
    /**
     * IV used during encryption/decryption.
     */
    EncryptionIV,
    /**
     * Check if the password is encrypted with Salsa20.
     */
    ProtectedStreamKey,
    /**
     * Position of the start bytes.
     */
    StreamStartBytes,
    /**
     * InnerRandomStreamID.
     */
    InnerRandomStreamID
}
