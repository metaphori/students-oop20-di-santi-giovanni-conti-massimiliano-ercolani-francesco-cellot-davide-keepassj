package model.crypto;

public class KDFBadParameter extends Exception {
    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -2328662160777577137L;

    public KDFBadParameter(final String message) {
        super(message);
    }

    public KDFBadParameter(final String message, final Throwable cause) {
        super(message, cause);
    }
}
