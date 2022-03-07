package br.com.empresa.projeto.exception;

public class MatriculaNaoEncontradaException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public MatriculaNaoEncontradaException(String msg) {
		super(msg);
	}

	public MatriculaNaoEncontradaException(String msg, Throwable tr) {
		super(msg, tr);
		{

		}
	}
}
