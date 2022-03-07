package br.com.empresa.projeto.exception;

public class ServiceException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ServiceException(String msg) {
		super(msg);
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
