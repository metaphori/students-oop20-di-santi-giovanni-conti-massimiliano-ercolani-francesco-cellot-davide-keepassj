package util;

import java.util.Random;

public class GeneratePasswordRandomImpl extends PasswordValidatorImpl implements GeneratePasswordRandom {

    private static Random random;

    public GeneratePasswordRandomImpl() {
    }

     public String generatePassword() {
        String psw;

        /*Generate different random password until find a valid one*/
        while (true) {
            psw = randomPassword(8);
            /*Use isValid() function of the class: "PasswordValidatoImpl"*/
            if (isValid(psw)) {
               return psw;
            }
        }
    }

    private static String randomPassword(final int len) {
        random = new Random();
        char[] password = new char[len];
        String upperLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        String lowerLetters = "abcdefghijklmnopqrstuvwxyz"; 
        String numbers = "0123456789"; 
        String symbols = "!@#$%^&*_=+-/.?";
        String values = upperLetters + lowerLetters + numbers + symbols;

        /*Insert randomly chosen character in the vector*/
        for (int i = 0; i < len; i++) {
            password[i] = values.charAt(random.nextInt(values.length()));
        }

        /*Convert the char vector to string*/
        String a = new String(password);

        return a;
    } 

}
