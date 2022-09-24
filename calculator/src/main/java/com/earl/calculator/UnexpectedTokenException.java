package com.earl.calculator;

import java.util.List;

/**
 * 
 * @author earlharris
 *
 */
public class UnexpectedTokenException extends Exception {

	private static final long serialVersionUID = 6338792991308298931L;

	private final Token actual;

	public UnexpectedTokenException(List<Token> tokenList, Token actual) {
		super("Expected " + listToString(tokenList) + ", but saw '" + actual + "'");
		this.actual = actual;
	}

	public Token getActual() {
		return actual;
	}

	private static String listToString(List<Token> tokenList) {
		StringBuilder result = new StringBuilder();
		String delimiter = "";
		for (int i = 0; i < tokenList.size(); i++) {
			result.append(delimiter);
			if (i == tokenList.size() - 1) {
				result.append("or ");
			}
			result.append("'" + tokenList.get(i) + "'");
			delimiter = ", ";
		}
		return result.toString();
	}
}
