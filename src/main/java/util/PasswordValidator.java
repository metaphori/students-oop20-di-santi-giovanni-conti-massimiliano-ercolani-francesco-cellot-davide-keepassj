package util;

/** Interface to create the validator
 * 
 * The isValid() method checks
 * the validity of the password
 *
 */
public interface PasswordValidator {

    /**
     * Validator
     * @param password
     * @return boolean
     */
    boolean isValid(String password);

}
