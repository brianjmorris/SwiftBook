package co.swiftbook.exception;

public class RestClientException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RestClientException(String msg) {
		super(msg);
	}

}
