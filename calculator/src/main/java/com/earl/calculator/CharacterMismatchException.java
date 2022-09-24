package com.earl.calculator;

/**
 * 
 * @author earlharris
 *
 */
public class CharacterMismatchException extends RuntimeException {

	private static final long serialVersionUID = -2051384570791500024L;

	final char expected;
	final char actual;

	public CharacterMismatchException(char expected, char actual) {
		super("character mismatch: expected '" + expected + "', but saw '" + actual + "'");
		this.expected = expected;
		this.actual = actual;
	}

	public char getExpected() {
		return expected;
	}

	public char getActual() {
		return actual;
	}
}
