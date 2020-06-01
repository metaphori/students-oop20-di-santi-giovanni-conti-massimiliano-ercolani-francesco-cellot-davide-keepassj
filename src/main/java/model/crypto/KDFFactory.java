package model.crypto;

/**
 * Factory Class to create object of KDF type.
 */
public final class KDFFactory {

    private KDFFactory() {
    }

    /**
     * Create object of KDF type.
     * @param kdf This is the string of the KDF to use.
     * @return KDF.
     */
    public static KDF create(final String kdf) {
        if (kdf == null) {
            return null;
        }
        if (kdf.equalsIgnoreCase("PBKDF2")) {
            return new PBKDF();
        } else if (kdf.equalsIgnoreCase("Argon2")) {
            return new Argon2KDF();
        } else {
            return new SCryptKDF();
        }
    }
}
