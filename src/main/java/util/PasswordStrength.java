package util;

public interface PasswordStrength {

	/*Method that return the password strength, passed by param*/
	int getStrength(String password);
	
	/*Method that return the number of characters*/
	int getLength();
	
}
