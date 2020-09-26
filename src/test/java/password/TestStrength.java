package password;

import org.junit.Test;

import util.PasswordStrengthImpl;

public class TestStrength {

    private String password;

    @Test
    public void testStrength() {
        password = "ciaociao8";
        System.out.println("Stringa: " + password);
        System.out.println("Strength: " + PasswordStrengthImpl.getStrength(password));

        password = "cioew5ncqow320dc";
        System.out.println("Stringa: " + password);
        System.out.println("Strength: " + PasswordStrengthImpl.getStrength(password));
    }

}
