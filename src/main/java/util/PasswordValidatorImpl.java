package util;

public class PasswordValidatorImpl implements PasswordValidator {

    /**
     * password This is the string that contain the password to check
     */
    private String password;

    /**
     * Set the variable
     */
    public PasswordValidatorImpl() {
        this.password = "";
    }

    /**
     * Check the validity of the password
     * @param password
     */
    @Override
    public final boolean isValid(final String password) {
        this.password = password;

        /*Password is valid only if contains at least one digit
         * and its length is at least eight characters*/
        if (password.matches("(?=.*[0-9]).*") && this.password.length() >= 8) {
            return true;
        }

        return false;
    }

}
