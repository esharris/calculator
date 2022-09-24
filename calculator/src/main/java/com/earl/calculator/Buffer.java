package com.earl.calculator;

/**
 * 
 * @author earlharris
 *
 */
public class Buffer {

	String sequence;
	int i;

	public Buffer(String sequence) {
		this.sequence = sequence;
		i = 0;
	}

	public boolean endOfBuffer() {
		return i >= sequence.length();
	}

	public char peek() {
		return sequence.charAt(i);
	}

	public void match(char c) {
		if (!endOfBuffer()) {
			if (peek() == c) {
				i++;
			} else {
				throw new CharacterMismatchException(c, peek());
			}
		} else {
			throw new EndOfBufferException(c);
		}
	}

	public void matchDigit() {
		if (!endOfBuffer()) {
			if ('0' <= peek() && peek() <= '9') {
				i++;
			} else {
				throw new NotADigitException(peek());
			}
		} else {
			throw new EndOfBufferException();
		}
	}
}
