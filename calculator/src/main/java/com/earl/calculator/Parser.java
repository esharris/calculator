package com.earl.calculator;

import java.util.Arrays;

/**
 * 
 * @author earlharris
 *
 */
public class Parser {

	private final Lexer lexer;
	Token peek;

	public Parser(Lexer lexer) throws UnexpectedCharacterException {
		this.lexer = lexer;
		peek = lexer.getToken();
	}

	public Lexer getLexer() {
		return lexer;
	}

	/**
	 * <sentence> ::= <expr> END_TOKEN
	 * 
	 * @throws UnexpectedCharacterException
	 * @throws UnexpectedTokenException
	 * @throws TokenMismatchException
	 */
	public double sentence() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		double result = expr();
		match(Token.END_TOKEN);
		return result;
	}

	/**
	 * <expr> ::= <product> ((PLUS_TOKEN|MINUS_TOKEN) <product>)*
	 * 
	 * @throws UnexpectedCharacterException
	 * @throws UnexpectedTokenException
	 * @throws TokenMismatchException
	 */
	private double expr() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		double result = product();
		while (peek == Token.PLUS_TOKEN || peek == Token.MINUS_TOKEN) {
			double temp;
			Token operatorToken = peek;
			if (peek == Token.PLUS_TOKEN) {
				match(Token.PLUS_TOKEN);
			} else {
				match(Token.MINUS_TOKEN);
			}
			temp = product();
			// left associativity
			if (operatorToken == Token.PLUS_TOKEN) {
				result += temp;
			} else {
				result -= temp;
			}
		}
		return result;
	}

	/**
	 * <product> ::= <power> ((TIMES_TOKEN|DIVIDE_TOKEN) <power>)*
	 * 
	 * @throws UnexpectedCharacterException
	 * @throws UnexpectedTokenException
	 * @throws TokenMismatchException
	 */
	private double product() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		double result = power();
		while (peek == Token.TIMES_TOKEN || peek == Token.DIVIDE_TOKEN) {
			double temp;
			Token operatorToken = peek;
			if (peek == Token.TIMES_TOKEN) {
				match(Token.TIMES_TOKEN);
			} else {
				match(Token.DIVIDE_TOKEN);
			}
			temp = power();
			// left associativity
			if (operatorToken == Token.TIMES_TOKEN) {
				result *= temp;
			} else {
				/**
				 * Regarding floating point values, dividing by 0.0 will not throw an exception.
				 */
				if (temp == 0.0) {
					throw new ArithmeticException("Divide by 0");
				} else {
					result /= temp;
				}
			}
		}
		return result;
	}

	/**
	 * <power> ::= <value> (EXPONENT_TOKEN <power>)?
	 * 
	 * @throws UnexpectedCharacterException
	 * @throws UnexpectedTokenException
	 * @throws TokenMismatchException
	 */
	private double power() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		double result = value();
		// right associative
		// https://stackoverflow.com/questions/47429513/why-is-exponentiation-applied-right-to-left
		if (peek == Token.EXPONENT_TOKEN) {
			double temp;
			match(Token.EXPONENT_TOKEN);
			temp = power();
			result = Math.pow(result, temp);
		}
		return result;
	}

	/**
	 * <value> ::= NUMBER_TOKEN | MINUS_TOKEN NUMBER_TOKEN | LEFT_PARENTHESIS <expr>
	 * RIGHT_PARENTHESIS
	 * 
	 * @throws UnexpectedCharacterException
	 * @throws UnexpectedTokenException
	 * @throws TokenMismatchException
	 */
	private double value() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		double result;
		switch (peek) {
		case NUMBER_TOKEN:
			match(Token.NUMBER_TOKEN);
			result = lexer.getD();
			break;

		case MINUS_TOKEN:
			match(Token.MINUS_TOKEN);
			match(Token.NUMBER_TOKEN);
			result = -1 * lexer.getD();
			break;

		case LEFT_PARENTHESIS_TOKEN:
			match(Token.LEFT_PARENTHESIS_TOKEN);
			result = expr();
			match(Token.RIGHT_PARENTHESIS_TOKEN);
			break;

		default:
			throw new UnexpectedTokenException(
					Arrays.asList(Token.NUMBER_TOKEN, Token.MINUS_TOKEN, Token.LEFT_PARENTHESIS_TOKEN), peek);
		}
		return result;
	}

	private void match(Token expected) throws UnexpectedCharacterException, TokenMismatchException {
		if (peek == expected) {
			peek = lexer.getToken();
		} else {
			throw new TokenMismatchException(expected, peek);
		}
	}
}
