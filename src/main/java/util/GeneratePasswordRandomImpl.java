package util;

import java.util.Random;

public class GeneratePasswordRandomImpl extends PasswordValidatorImpl implements GeneratePasswordRandom {

    /**.
     * password This is the string that contain the password to check
     */
    private String psw;
    private static Random random;

    /**.
     * Set the variable
     */
    public GeneratePasswordRandomImpl() {
        this.psw = "";
    }

    /**.
     * Create the random password and check the validity
     * with the method of PasswordValidatorImpl class
     * @return String
     */
     public final String generatePassword() {

        //Generate different random password until find a valid one
        while (true) {
            psw = randomPassword(8);
            //Use isValid() function of the class: "PasswordValidatoImpl"
            if (isValid(psw)) {
               return psw;
            }
        }
    }

     /**.
      * Private method to create the password
      * @param len
      * @return String
      */
    private static String randomPassword(final int len) {
        random = new Random();
        char[] password = new char[len];
        String upperLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        String lowerLetters = "abcdefghijklmnopqrstuvwxyz"; 
        String numbers = "0123456789"; 
        String symbols = "!@#$%^&*_=+-/.?";
        String values = upperLetters + lowerLetters + numbers + symbols;

        //Insert randomly chosen character in the vector
        for (int i = 0; i < len; i++) {
            password[i] = values.charAt(random.nextInt(values.length()));
        }

        //Convert the char vector to string
        String a = new String(password);

        return a;
    } 

}
