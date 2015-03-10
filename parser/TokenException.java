package contacts.parser;

public class TokenException extends Exception{
	
	public TokenException() {
		super("Incorrect XML syntax");
	}
	
	public TokenException(String message) {
		super("Incorrect XML syntax: " + message);
	}

}
