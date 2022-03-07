package br.com.empresa.projeto.exception;

public class ParametroInvalidoException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public ParametroInvalidoException(String msg) {
		super(msg);
	}

	public ParametroInvalidoException(String msg, Throwable tr) {
		super(msg, tr);
		{
		}
	}
}