import polynomial.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI extends Frame {
	JTextField txtPolynomial1 = new JTextField();
	JTextField txtPolynomial2 = new JTextField();
	JLabel txtResPolynomial = new JLabel("The result will be displayed here");
	Polynomial polynomial1;
	Polynomial polynomial2;

	public GUI() {
		JFrame frame = new JFrame("Polynomial");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		txtPolynomial1.setPreferredSize(new Dimension(200, 24));
		txtPolynomial2.setPreferredSize(new Dimension(200, 24));

		JButton btnAddition = new JButton("Add");
		JButton btnSubstraction = new JButton("Subtract");
		JButton btnMultiplication = new JButton("Multiply");
		JButton btnDivision = new JButton("Divide");
		JButton btnIntegration = new JButton("Integrate");
		JButton btnDifferentiate = new JButton("Differentiate");

		btnAddition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polynomial1 = new Polynomial(txtPolynomial1.getText());
				polynomial2 = new Polynomial(txtPolynomial2.getText());
				txtResPolynomial.setText(polynomial1.addition(polynomial2).PolynomialToString());
			}
		});

		btnSubstraction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polynomial1 = new Polynomial(txtPolynomial1.getText());
				polynomial2 = new Polynomial(txtPolynomial2.getText());
				txtResPolynomial.setText(polynomial1.subtraction(polynomial2).PolynomialToString());
			}

		});

		btnMultiplication.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polynomial1 = new Polynomial(txtPolynomial1.getText());
				polynomial2 = new Polynomial(txtPolynomial2.getText());
				txtResPolynomial.setText(polynomial1.multiply(polynomial2).PolynomialToString());
			}

		});

		btnDivision.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polynomial1 = new Polynomial(txtPolynomial1.getText());
				polynomial2 = new Polynomial(txtPolynomial2.getText());
				txtResPolynomial.setText(polynomial1.divide(polynomial2).PolynomialToString());
			}
		});

		btnIntegration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polynomial1 = new Polynomial(txtPolynomial1.getText());
				txtResPolynomial.setText(polynomial1.integrate().PolynomialToString());
			}
		});

		btnDifferentiate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				polynomial1 = new Polynomial(txtPolynomial1.getText());
				txtResPolynomial.setText(polynomial1.differentiate().PolynomialToString());
			}
		});

		JPanel pnlMainPanel = new JPanel();

		JPanel wrapper = new JPanel(new FlowLayout(0, 0, FlowLayout.CENTER));
		wrapper.add(txtPolynomial1);
		wrapper.add(txtPolynomial2);
		pnlMainPanel.add(wrapper);

		pnlMainPanel.setLayout(new BoxLayout(pnlMainPanel, BoxLayout.Y_AXIS));

		btnAddition.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnSubstraction.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnMultiplication.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnDivision.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnIntegration.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnDifferentiate.setAlignmentX(Component.LEFT_ALIGNMENT);

		pnlMainPanel.add(txtResPolynomial);
		pnlMainPanel.add(btnAddition);
		pnlMainPanel.add(btnSubstraction);
		pnlMainPanel.add(btnMultiplication);
		pnlMainPanel.add(btnDivision);
		pnlMainPanel.add(btnIntegration);
		pnlMainPanel.add(btnDifferentiate);

		frame.add(pnlMainPanel);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String Args[]) {
		new GUI();
	}
}
