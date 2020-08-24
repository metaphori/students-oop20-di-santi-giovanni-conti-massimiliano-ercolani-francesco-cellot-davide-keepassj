package util;

public class PasswordValidatorImpl implements PasswordValidator{

	private String password;
	
	public PasswordValidatorImpl() {
		this.password = "";
	}
	
	@Override
	public boolean isValid(final String password) {
		this.password = password;
		
		/*Password is valid only if contains at least one digit
		 * and its length is at least eight characters*/
		if(password.matches("(?=.*[0-9]).*") && this.password.length() >= 8)
			return true;
		
		return false;
	}

}
