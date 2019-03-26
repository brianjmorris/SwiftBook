package co.swiftbook.exception;

public class ApiObjectException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ApiObjectException(String msg) {
		super(msg);
	}
	
}
