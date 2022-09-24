package org.earl.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.earl.calculator.Buffer;
import com.earl.calculator.EndOfBufferException;
import com.earl.calculator.Lexer;
import com.earl.calculator.Parser;
import com.earl.calculator.Token;
import com.earl.calculator.TokenMismatchException;
import com.earl.calculator.UnexpectedCharacterException;
import com.earl.calculator.UnexpectedTokenException;

class TestParser {

	@Test
	void testSentence() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("20");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertEquals(20.0, parser.sentence());
	}

	@Test
	void testSentence1() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("20+35");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertEquals(55.0, parser.sentence());
	}

	@Test
	void testSentence2() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("-20 * 7");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertEquals(-140.0, parser.sentence());
	}

	@Test
	void testSentence3() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("20/-5");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertEquals(-4.0, parser.sentence());
	}

	@Test
	void testSentence4() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("2^8");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertEquals(256.0, parser.sentence());
	}

	@Test
	void testSentence5() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("2^(4+2+2)");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertEquals(256.0, parser.sentence());
	}

	@Test
	void testSentence6() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("20-10-5");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertEquals(5.0, parser.sentence());
	}

	@Test
	void testSentence7() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("20/5/2");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertEquals(2.0, parser.sentence());
	}

	@Test
	void testSentence8() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("20%4");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		UnexpectedCharacterException e = assertThrows(UnexpectedCharacterException.class, () -> {
			parser.sentence();
		});
		assertEquals('%', e.getActual());
	}

	@Test
	void testSentence9() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("20+");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		UnexpectedTokenException e = assertThrows(UnexpectedTokenException.class, () -> {
			parser.sentence();
		});
		assertEquals(Token.END_TOKEN, e.getActual());
	}

	@Test
	void testSentence10() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("23.5-4.5");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertEquals(19.0, parser.sentence());
	}

	@Test
	void testSentence11() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("2E-2");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertEquals(0.02, parser.sentence());
	}

	@Test
	void testSentence12() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("20/0");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertThrows(ArithmeticException.class, () -> {
			parser.sentence();
		});
	}

	@Test
	void testSentence13() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("2E");
		Lexer lexer = new Lexer(buffer);
		assertThrows(EndOfBufferException.class, () -> {
			new Parser(lexer);
		});
	}

	@Test
	void testSentence14() throws UnexpectedCharacterException, UnexpectedTokenException, TokenMismatchException {
		Buffer buffer = new Buffer("");
		Lexer lexer = new Lexer(buffer);
		Parser parser = new Parser(lexer);
		assertThrows(UnexpectedTokenException.class, () -> {
			parser.sentence();
		});
	}
}
