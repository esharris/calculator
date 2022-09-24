package com.earl.calculator;

/**
 * 
 * @author earlharris
 *
 */
public class TokenMismatchException extends Exception {

	private static final long serialVersionUID = 2983256993816056764L;

	final Token expected;
	final Token actual;

	public TokenMismatchException(Token expected, Token actual) {
		super("character mismatch: expected '" + expected + "', but saw '" + actual + "'");
		this.expected = expected;
		this.actual = actual;
	}

	public Token getExpected() {
		return expected;
	}

	public Token getActual() {
		return actual;
	}
}
