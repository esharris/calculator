package com.earl.calculator;

/**
 * 
 * @author earlharris
 *
 */
public class NotADigitException extends RuntimeException {

	private static final long serialVersionUID = -6746236655084343531L;

	final char actual;

	/**
	 * 
	 * @param actual
	 */
	public NotADigitException(char actual) {
		super("Expected digit, but saw '" + actual + "'");
		this.actual = actual;
	}

	public char getActual() {
		return actual;
	}
}
