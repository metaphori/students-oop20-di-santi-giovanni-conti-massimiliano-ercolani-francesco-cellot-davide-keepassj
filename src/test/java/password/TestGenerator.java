package password;

import static org.junit.Assert.*;
import org.junit.*;

import util.GeneratePasswordRandom;
import util.GeneratePasswordRandomImpl;

public class TestGenerator {

    private String password;
    private GeneratePasswordRandom generate;

    @Test
    public void testGenerator() {
        generate = new GeneratePasswordRandomImpl();

        password = generate.generatePassword();
        System.out.println("Password generata: " + password);
    }

}
