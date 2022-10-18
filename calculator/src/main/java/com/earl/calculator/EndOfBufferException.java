package com.earl.calculator;

/**
 * 
 * @author earlharris
 */
public class EndOfBufferException extends RuntimeException {

	private static final long serialVersionUID = -3793277262527047763L;

	private final char expected;

	/**
	 * 
	 * @param expected
	 */
	public EndOfBufferException(char expected) {
		super("character mismatch: expected '" + expected + "', but saw <end of buffer>");
		this.expected = expected;
	}

	/**
	 * 
	 */
	public EndOfBufferException() {
		super("character mismatch: expected <digit>, but saw <end of buffer>");
		this.expected = '0';
	}

	public char getExpected() {
		return expected;
	}
}
