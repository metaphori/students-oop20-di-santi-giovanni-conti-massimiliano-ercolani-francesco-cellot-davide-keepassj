package model.crypto;

public final class KDFFactory {

    private KDFFactory() {
    }

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
