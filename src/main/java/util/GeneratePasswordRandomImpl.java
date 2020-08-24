package util;

import java.util.Random;

public class GeneratePasswordRandomImpl extends PasswordValidatorImpl implements GeneratePasswordRandom{

	static Random rnd_method;

	public GeneratePasswordRandomImpl() {
		
		
	}
	
	public String generatePassword() {
		String psw;
		
		/*Generate different random password until find a valid one*/
		while(true) {
			psw = randomPassword(8);
			/*Use isValid() function of the class: "PasswordValidatoImpl"*/
			if(isValid(psw))
				return psw;
		}
	}
	
	private static String randomPassword(int len) {
		rnd_method = new Random();
		char[] password = new char[len];
		String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
		String Small_chars = "abcdefghijklmnopqrstuvwxyz"; 
		String numbers = "0123456789"; 
		String symbols = "!@#$%^&*_=+-/.?";
		String values = Capital_chars + Small_chars + numbers + symbols;
		
		/*Insert randomly chosen character in the vector*/
		for(int i=0; i<len; i++) {
			password[i] = values.charAt(rnd_method.nextInt(values.length()));
		}
		
		/*Convert the char vector to string*/
		String a = new String(password);
		
		return a;
	} 
	
}
