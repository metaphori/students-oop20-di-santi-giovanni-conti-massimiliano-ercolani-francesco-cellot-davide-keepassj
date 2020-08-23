package util;

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
	
	
}
