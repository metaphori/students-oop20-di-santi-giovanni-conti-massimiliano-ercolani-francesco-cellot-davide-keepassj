package util;

public class PasswordStrengthImpl extends CheckCharacters {

private static char[] password;

    public static int getStrength(final String p) {
        password = p.toCharArray();

        return getSommatoria() - getDifferenza();
    }

    public static int getLength() {
        return password.length;
    }

    private static int getSommatoria() {
        int nCaratteri = getLength() * 6;
        int nUpper = (getLength() - countUpper(password)) * 2;
        int nLower = (getLength() - countLower(password)) * 2;
        int nNumbers = countNumbers(password) * 4;
        int nSpecial = countSpecial(password) * 6;
        return  nCaratteri + nUpper + nLower + nNumbers + nSpecial;
    }

    private static int getDifferenza() {
        int nConsUpper = getConsecutiveUpper(password) * 2;
        int nConsLower = getConsecutiveLower(password) * 2;
        int nConsNumber = getConsecutiveNumbers(password) * 2;
        int nRepeatChar = getRepeatChar(password) * 2;

        return nConsUpper + nConsLower + nConsNumber + nRepeatChar;
    }

}
