package util;

public class PasswordValidatorImpl implements PasswordValidator{

	private String password;
	
	public PasswordValidatorImpl() {
		this.password = "";
	}
	
	@Override
	public boolean isValid(final String password) {
		this.password = password;
		
		/*La password è valida solo se contiene una cifra
		 * e la sua lunghezza è minimo di otto caratteri*/
		if(password.matches("(?=.*[0-9]).*") && this.password.length() >= 8)
			return true;
		
		return false;
	}

}
