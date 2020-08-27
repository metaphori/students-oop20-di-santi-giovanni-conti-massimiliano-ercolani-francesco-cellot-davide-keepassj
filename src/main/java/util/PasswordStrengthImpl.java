package util;

public class PasswordStrengthImpl implements PasswordStrength{

	/*Array of char*/
	private char[] password;
	
	@Override
	public int getStrength(String p) {
		password = p.toCharArray();
		
		return 0;
	}
	
	public int getLength() {
		return this.password.lenght;
	}

	/*Return the number of Uppercase letters*/
	private int getNUpper() {
		int count=0;
		
		for(int i=0; i<this.password.length; i++) {
			if(password[i] >= 'A' && password[i] <= 'Z')
				count++;
		}
		return count;
	}
	
	/*Return the number of lowercase letters*/
	private int getNLower();
	
	/*Return the number of digit*/
	private int getNNumbers();
	
	/*Return the number of special characters*/
	private int getNSpecial();

	
}
