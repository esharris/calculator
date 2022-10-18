package com.earl.calculator;

/**
 * 
 * @author earlharris
 *
 */
public class Lexer {

	final private Buffer buffer;
	private double d;

	/**
	 * 
	 * @param buffer
	 */
	public Lexer(Buffer buffer) {
		this.buffer = buffer;
	}

	public Token getToken() throws UnexpectedCharacterException {
		Token result = null;
		boolean done = false;
		while (!done) {
			if (buffer.endOfBuffer()) {
				result = Token.END_TOKEN;
				done = true;
			} else {
				final char c = buffer.peek();
				switch (c) {
				case ' ':
					buffer.match(' ');
					break;

				case '\t':
					buffer.match('\t');

					break;

				case '\n':
					buffer.match('\n');
					break;

				case '\r':
					buffer.match('\r');
					break;

				case '+':
					buffer.match('+');
					result = Token.PLUS_TOKEN;
					done = true;
					break;

				case '-':
					buffer.match('-');
					result = Token.MINUS_TOKEN;
					done = true;
					break;

				case '*':
					buffer.match('*');
					result = Token.TIMES_TOKEN;
					done = true;
					break;

				case '/':
					buffer.match('/');
					result = Token.DIVIDE_TOKEN;
					done = true;
					break;

				case '^':
					buffer.match('^');
					result = Token.EXPONENT_TOKEN;
					done = true;
					break;

				case '(':
					buffer.match('(');
					result = Token.LEFT_PARENTHESIS_TOKEN;
					done = true;
					break;

				case ')':
					buffer.match(')');
					result = Token.RIGHT_PARENTHESIS_TOKEN;
					done = true;
					break;

				default:
					if ('0' <= c && c <= '9') {
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append(c);
						buffer.matchDigit();
						while (!buffer.endOfBuffer() && ('0' <= buffer.peek() && buffer.peek() <= '9')) {
							stringBuilder.append(buffer.peek());
							buffer.matchDigit();
						}
						if (!buffer.endOfBuffer() && buffer.peek() == '.') {
							stringBuilder.append(buffer.peek());
							buffer.match('.');
							while (!buffer.endOfBuffer() && ('0' <= buffer.peek() && buffer.peek() <= '9')) {
								stringBuilder.append(buffer.peek());
								buffer.matchDigit();
							}
						}
						if (!buffer.endOfBuffer() && (buffer.peek() == 'e' || buffer.peek() == 'E')) {
							stringBuilder.append(buffer.peek());
							if (buffer.peek() == 'e') {
								buffer.match('e');
							} else {
								buffer.match('E');
							}
							if (!buffer.endOfBuffer() && buffer.peek() == '-') {
								stringBuilder.append(buffer.peek());
								buffer.match('-');
							}
							if (!buffer.endOfBuffer() && ('0' <= buffer.peek() && buffer.peek() <= '9')) {
								stringBuilder.append(buffer.peek());
							}
							buffer.matchDigit();
							while (!buffer.endOfBuffer() && ('0' <= buffer.peek() && buffer.peek() <= '9')) {
								stringBuilder.append(buffer.peek());
								buffer.matchDigit();
							}
						}
						d = Double.parseDouble(stringBuilder.toString());
						result = Token.NUMBER_TOKEN;
						done = true;
					} else {
						throw new UnexpectedCharacterException(c);
					}
				} // switch
			} // if
		} // while
		return result;
	}

	public double getD() {
		return d;
	}
}
