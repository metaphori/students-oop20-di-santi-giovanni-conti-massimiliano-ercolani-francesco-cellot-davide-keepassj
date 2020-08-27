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

	
	
}
