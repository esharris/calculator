package com.earl.calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 
 * @author earlharris
 *
 */
public class Calculator2 {

	private static final int ORIGIN_X = 50;
	private static final int ORIGIN_Y = 50;

	private static final int LETTER_WIDTH = 20;
	private static final int LETTER_HEIGHT = 20;

	private static final int FIRST_ROW = ORIGIN_Y;
	private static final int SECOND_ROW = ORIGIN_Y + 50;
	private static final int THIRD_ROW = ORIGIN_Y + 100;
	private static final int FOURTH_ROW = ORIGIN_Y + 150;

	public static void main(String[] args) {

		final JFrame frame = new JFrame("Calculator");

		final JLabel title = new JLabel("expression: ");
		title.setBounds(ORIGIN_X, FIRST_ROW, 100, LETTER_HEIGHT);
		frame.add(title);

		/**
		 * the arithmetic expressions go here.
		 */
		final JTextField buffer = new JTextField("");
		buffer.setBounds(ORIGIN_X + 110, FIRST_ROW, 200, LETTER_HEIGHT);
		frame.add(buffer);

		/**
		 * Create a clear button
		 */
		final JButton clearButton = new JButton("clr");
		clearButton.setBounds(ORIGIN_X, SECOND_ROW, LETTER_WIDTH * 2, LETTER_HEIGHT);
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				buffer.setText("");
			}
		});
		frame.add(clearButton);

		/**
		 * Error messages go here.
		 */
		final JLabel message = new JLabel();
		message.setBounds(ORIGIN_X, FOURTH_ROW, 700, LETTER_HEIGHT);
		frame.add(message);

		/**
		 * The equals button that triggers the calculation.
		 */
		final JButton equalsButton = new JButton("=");
		equalsButton.setBounds(ORIGIN_X, THIRD_ROW, LETTER_WIDTH, LETTER_HEIGHT);
		equalsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
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
			}
		});
		frame.add(equalsButton);

		frame.setSize(800, 400);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}
}
