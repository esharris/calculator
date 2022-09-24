package org.earl.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.earl.calculator.Buffer;
import com.earl.calculator.Lexer;
import com.earl.calculator.Token;
import com.earl.calculator.UnexpectedCharacterException;

class TestLexer {

	@Test
	void testGetToken() throws UnexpectedCharacterException {
		final Buffer buffer = new Buffer("+ -\t*\n/\r^()0123456789-23.5 32. 0.5E2 0.9e-3 1 ");
		final Lexer lexer = new Lexer(buffer);

		assertEquals(Token.PLUS_TOKEN, lexer.getToken());
		assertEquals(Token.MINUS_TOKEN, lexer.getToken());
		assertEquals(Token.TIMES_TOKEN, lexer.getToken());
		assertEquals(Token.DIVIDE_TOKEN, lexer.getToken());
		assertEquals(Token.EXPONENT_TOKEN, lexer.getToken());
		assertEquals(Token.LEFT_PARENTHESIS_TOKEN, lexer.getToken());
		assertEquals(Token.RIGHT_PARENTHESIS_TOKEN, lexer.getToken());
		assertEquals(Token.NUMBER_TOKEN, lexer.getToken());
		assertEquals(123456789, lexer.getD());
		assertEquals(Token.MINUS_TOKEN, lexer.getToken());
		assertEquals(Token.NUMBER_TOKEN, lexer.getToken());
		assertEquals(23.5, lexer.getD());
		assertEquals(Token.NUMBER_TOKEN, lexer.getToken());
		assertEquals(32.0, lexer.getD());
		assertEquals(Token.NUMBER_TOKEN, lexer.getToken());
		assertEquals(50.0, lexer.getD());
		assertEquals(Token.NUMBER_TOKEN, lexer.getToken());
		assertEquals(0.0009, lexer.getD());
		assertEquals(Token.NUMBER_TOKEN, lexer.getToken());
		assertEquals(1.0, lexer.getD());
		assertEquals(Token.END_TOKEN, lexer.getToken());
	}

	@Test
	void testGetTokenFail() {
		final Buffer buffer = new Buffer("j");
		final Lexer lexer = new Lexer(buffer);

		final UnexpectedCharacterException e = assertThrows(UnexpectedCharacterException.class, () -> {
			lexer.getToken();
		});

		assertEquals('j', e.getActual());
	}

	@Test
	void testGetTokenFail2() {
		final Buffer buffer = new Buffer("10..2");
		final Lexer lexer = new Lexer(buffer);

		final UnexpectedCharacterException e = assertThrows(UnexpectedCharacterException.class, () -> {
			lexer.getToken();
			lexer.getToken();
		});

		assertEquals('.', e.getActual());
	}
}
