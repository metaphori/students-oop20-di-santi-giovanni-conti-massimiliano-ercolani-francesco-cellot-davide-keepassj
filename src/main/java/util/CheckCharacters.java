package util;

public class CheckCharacters extends ConsecutiveCharacters {

    /*Return the number of Uppercase letters*/
    public static int countUpper(final char[] password) {
        int count = 0;

        for (int i = 0; i < password.length; i++) {
            if (password[i] >= 'A' && password[i] <= 'Z') {
                count++;
            }
        }
        return count;
    }

    /*Return the number of lowercase letters*/
    public static int countLower(final char[] password) {
        int count = 0;

        for (int i = 0; i < password.length; i++) {
            if (password[i] >= 'a' && password[i] <= 'z') {
                count++;
            }
        }
        return count;
    }

    /*Return the number of digit*/
    public static int countNumbers(final char[] password) {
        int count = 0;

        for (int i = 0; i < password.length; i++) {
            if (password[i] >= '0' && password[i] <= '9') {
                count++;
            }
        }
        return count;
    }

    /*Return the number of special characters*/
    public static int countSpecial(final char[] password) {
        int count = 0;
        String symbols = "!@#$%^&*_=+-/.?";

        for (int i = 0; i < password.length; i++) {
            if (password[i] == '!' || password[i] == '@' || password[i] == '#' || password[i] == '$' || password[i] == '%'
                    || password[i] == '^' || password[i] == '&' || password[i] == '*' || password[i] == '_' || password[i] == '='
                    || password[i] == '+' || password[i] == '-' || password[i] == '/' || password[i] == '.' || password[i] == '?') {
                count++;
            }
        }
        return count;
    }

}
