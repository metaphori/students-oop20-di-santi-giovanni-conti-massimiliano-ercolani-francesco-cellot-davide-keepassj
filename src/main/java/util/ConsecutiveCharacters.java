package util;

public class ConsecutiveCharacters {

    /**
     * Return the number of consecutive Uppercase letters.
     * @param password
     * @return int
     */
    public static int getConsecutiveUpper(final char[] password) {
        int count = 0;

        for (int i = 0; i < password.length - 1; i++) {
            //If the character is an Uppercase and the consecutive one too...
            if ((password[i] >= 'A' && password[i] <= 'Z')
                    && (password[i + 1] >= 'A' && password[i + 1] <= 'Z')) {
                count++;
            }
        }
        return count;
    }

    /**
     * Return the number of consecutive Lowercase letters.
     * @param password
     * @return int
     */
    public static int getConsecutiveLower(final char[] password) {
        int count = 0;

        for (int i = 0; i < password.length - 1; i++) {
            /*If the character is an Lowercase and the consecutive one too...*/
            if ((password[i] >= 'a' && password[i] <= 'z') 
                    && (password[i + 1] >= 'a' && password[i + 1] <= 'z')) {
                count++;
            }
        }
        return count;
    }

    /**
     * Return the number of consecutive numbers.
     * @param password
     * @return int
     */
    public static int getConsecutiveNumbers(final char[] password) {
        int count = 0;

        for (int i = 0; i < password.length - 1; i++) {
            if ((password[i] >= '0' && password[i] <= '9') 
                    && (password[i + 1] >= '0' && password[i + 1] <= '9')) {
                count++;
            }
        }
        return count;
    }

    /**
     * Return the number of repetitions of the same character consecutively.
     * @param password
     * @return int
     */
    public static int getRepeatChar(final char[] password) {
        int count = 0;

        for (int i = 0; i < password.length - 1; i++) {
            if (password[i] == password[i + 1]) {
                count++;
            }
        }
        return count;
    }

}
