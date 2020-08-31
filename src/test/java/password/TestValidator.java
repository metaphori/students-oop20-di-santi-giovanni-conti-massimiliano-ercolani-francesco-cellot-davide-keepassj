package password;

import static org.junit.Assert.*;
import org.junit.*;

import util.PasswordValidator;
import util.PasswordValidatorImpl;

public class TestValidator {

    private String password;
    private PasswordValidator validate;

    @Test
    public void validatorTest() {
        validate = new PasswordValidatorImpl();

        /*assert statements*/
        this.password = "ProvaPassword01";
        assertTrue(validate.isValid(password));
        this.password = "ProvaPassword";
        assertFalse(validate.isValid(password));
    }

    @Test
    public void validatorLengthTest() {
        validate = new PasswordValidatorImpl();

        /*assert statements*/
        this.password = "C3-P0";
        assertFalse(validate.isValid(password));
        this.password = "Password45";
        assertTrue(validate.isValid(password));
    }

}
