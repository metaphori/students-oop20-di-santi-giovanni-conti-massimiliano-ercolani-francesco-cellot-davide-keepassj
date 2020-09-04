package util;

public class PasswordStrengthImpl extends CheckCharacters{

    /*Array of char to contain the password*/
    private static char[] password;

	/*Variables useful to calculate password strength*/
	private int sum;
	private int sub;
	
	public static int getStrength(final String p) {
		password = p.toCharArray();	//convert string to char array

		return 0;
	}
	
    public int getLength() {
		return this.password.length;
	}

	/*Return the number of Uppercase letters*/
	private int getNUpper() {
		int count = 0;

		for (int i = 0; i < this.password.length; i++) {
			if (password[i] >= 'A' && password[i] <= 'Z') {
				count++;
			}
		}
		return count;
	}
	
	/*Return the number of lowercase letters*/
	private int getNLower() {
		int count = 0;

		for (int i = 0; i < password.length; i++) {
			if (password[i] >= 'a' && password[i] <= 'z') {
				count++;
			}
		}
		return count;
	}
	
	/*Return the number of digit*/
	private int getNNumbers() {
        return 0;
    }
	
	/*Return the number of special characters*/
	private int getNSpecial() {
        return 0;
    }

	
	
}
