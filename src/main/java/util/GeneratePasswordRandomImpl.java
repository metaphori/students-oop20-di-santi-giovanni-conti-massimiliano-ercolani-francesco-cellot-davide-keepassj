package util;

import java.util.Random;

public class GeneratePasswordRandomImpl extends PasswordValidatorImpl implements GeneratePasswordRandom{

	static Random rnd_method;

	
	public String generatePassword() {
		String psw;
		
		/*Genero password diverse casuali finchè non ne trovo una valida*/
		while(true) {
			psw = randomPassword(8);
			/*Uso la funzione isValid() della classe PasswordValidatorImpl*/
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
		
		/*Inserisco carattere scelto casualmente nel vettore*/
		for(int i=0; i<len; i++) {
			password[i] = values.charAt(rnd_method.nextInt(values.length()));
		}
		
		/*Converto il vettore di char in stringa*/
		String a = new String(password);
		
		return a;
	} 
	
	
}
