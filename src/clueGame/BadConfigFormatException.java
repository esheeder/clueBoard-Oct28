package clueGame;

public class BadConfigFormatException extends Exception{

	public BadConfigFormatException(String message) {
		super(message);
	}
	public BadConfigFormatException(){
		
	}
	@Override
	public String toString() {
		return "Bad config file.";
	}
	

}
