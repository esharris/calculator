package com.earl.calculator;

/**
 * 
 * @author earlharris
 *
 */
public class UnexpectedCharacterException extends Exception {

	private static final long serialVersionUID = -6514730051853580721L;

	private final char actual;

	public UnexpectedCharacterException(char actual) {
		super("Unexpected character: '" + actual + "'");
		this.actual = actual;
	}

	public char getActual() {
		return actual;
	}
}
