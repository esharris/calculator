package com.earl.calculator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 
 * @author earlharris
 *
 */
public class Calculator {

	private static final int ORIGIN_X = 50;
	private static final int ORIGIN_Y = 50;
	private static final int LETTER_WIDTH = 20;
	private static final int LETTER_HEIGHT = 20;

	private static final int FIRST_ROW = ORIGIN_Y;
	private static final int SECOND_ROW = ORIGIN_Y + 50;
	private static final int THIRD_ROW = ORIGIN_Y + 100;
	private static final int FOURTH_ROW = ORIGIN_Y + 150;
	private static final int FIFTH_ROW = ORIGIN_Y + 200;

	public static void main(String[] args) {

		final JFrame frame = new JFrame("Calculator");

		final JLabel title = new JLabel("expression: ");
		title.setBounds(ORIGIN_X, FIRST_ROW, 100, LETTER_HEIGHT);
		frame.add(title);

		/**
		 * the arithmetic expressions go here.
		 */
		final JLabel buffer = new JLabel("");
		buffer.setBounds(ORIGIN_X + 110, FIRST_ROW, 200, LETTER_HEIGHT);
		frame.add(buffer);

		/**
		 * Create a button for each digit. They are the same size.
		 */
		final JButton[] digitButtons = new JButton[10];
		for (int i = 0; i < 10; i++) {
			digitButtons[i] = new JButton(String.valueOf(i));
			digitButtons[i].setBounds(ORIGIN_X + 25 * i, SECOND_ROW, LETTER_WIDTH, LETTER_HEIGHT);
			final int j = i;
			digitButtons[i].addActionListener(e -> buffer.setText(buffer.getText() + String.valueOf(j)));
			frame.add(digitButtons[i]);
		}

		/**
		 * Create a delete button
		 */
		final JButton deleteButton = new JButton("del");
		deleteButton.setBounds(ORIGIN_X, THIRD_ROW, LETTER_WIDTH * 2, LETTER_HEIGHT);
		deleteButton.addActionListener(e -> {
			final String s = buffer.getText();
			if (!s.isEmpty()) {
				buffer.setText(s.substring(0, s.length() - 1));
			}
		});
		frame.add(deleteButton);

		/**
		 * Create a clear button
		 */
		final JButton clearButton = new JButton("clr");
		clearButton.setBounds(ORIGIN_X + 25 * 2, THIRD_ROW, LETTER_WIDTH * 2, LETTER_HEIGHT);
		clearButton.addActionListener(e -> buffer.setText(""));
		frame.add(clearButton);

		/**
		 * The special characters that deserve a button, which just each echo their
		 * character in the buffer.
		 */
		final String[] specials = { "+", "-", "*", "/", "^", "(", ")", ".", "E" };

		/**
		 * Create a button for each special character. They line up with deleteButton.
		 */
		final JButton[] specialButtons = new JButton[specials.length];

		for (int j = 0; j < specials.length; j++) {
			specialButtons[j] = new JButton(specials[j]);
			specialButtons[j].setBounds(ORIGIN_X + 25 * (j + 4), THIRD_ROW, LETTER_WIDTH, LETTER_HEIGHT);
			final int k = j; // required for action listener.
			specialButtons[j].addActionListener(e -> buffer.setText(buffer.getText() + specials[k]));
			frame.add(specialButtons[j]);
		}

		/**
		 * Error messages go here.
		 */
		final JLabel message = new JLabel();
		message.setBounds(ORIGIN_X, FIFTH_ROW, 700, LETTER_HEIGHT);
		frame.add(message);

		/**
		 * The equals button that triggers the calculation.
		 */
		final JButton equalsButton = new JButton("=");
		equalsButton.setBounds(ORIGIN_X, FOURTH_ROW, LETTER_WIDTH, LETTER_HEIGHT);
		equalsButton.addActionListener(e -> {
			Buffer buffer1 = new Buffer(buffer.getText());
			Lexer lexer = new Lexer(buffer1);
			try {
				Parser parser = new Parser(lexer);
				double result = parser.sentence();
				buffer.setText(Double.toString(result));
				message.setText("Parsed!");
			} catch (EndOfBufferException e1) {
				message.setText(e1.getMessage());
			} catch (ArithmeticException e1) {
				message.setText(e1.getMessage());
			} catch (UnexpectedCharacterException e1) {
				message.setText(e1.getMessage());
			} catch (UnexpectedTokenException e1) {
				message.setText(e1.getMessage());
			} catch (TokenMismatchException e1) {
				message.setText(e1.getMessage());
			} catch (Exception e1) {
				message.setText("System error: " + e1.getMessage());
				e1.printStackTrace();
			}
		});
		frame.add(equalsButton);

		frame.setSize(800, 400);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}
}
