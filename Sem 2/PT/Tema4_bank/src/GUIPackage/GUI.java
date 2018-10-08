package GUIPackage;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import BankPackage.Bank;

public class GUI extends Frame {

	private JFrame frame;
	private JTextField status;
	private JTabbedPane tabPanel;
	private JPanel pPanel,aPanel;
	private JLabel pLabel;
	private JTextField addPName,addPSurname,addPId;
	private JButton btnAddP,btnEditP,btnDeleteP;
	private JTable pTable;
	private JScrollPane scrollPTable;
	private JLabel aLabel;
	private DefaultComboBoxModel<String> aType;
	private JComboBox<String> addAType;
	private JTextField addAMainHolderId;
	private JTextField addASum;
	private DefaultComboBoxModel<String> yearSavings;
	private JComboBox<String> addAYear;
	private JTextField addAId,sum;
	private JButton btnAddA,btnEditA,btnDeleteA,btnDep,btnWDraw;
	private JTable aTable;
	private JScrollPane scrollATable;
	private Bank bank;
	private TableModel tm;


	public GUI() { // defining panels, layouts and fields
		bank = new Bank();
		tm = new TableModel();
		frame = new JFrame("Bank Administration");
		frame.setSize(650, 665);
		frame.setLayout(null);
		frame.addWindowListener(new WindowAdapter() { // listener for
			// closing
			public void windowClosing(WindowEvent windowEvent) {
				bank.serialize();
				System.exit(0);
			}
		});

		status = new JTextField("Status: Ready!");
		status.setSize(590, 25);
		status.setLocation(20, 585);
		status.setEditable(false);
		frame.add(status);

		tabPanel = new JTabbedPane();
		tabPanel.setSize(650, 565);
		frame.add(tabPanel);

		pPanel = new JPanel();
		pPanel.setLayout(null);
		pPanel.setSize(650, 565);
		createPTab();
		tabPanel.addTab("Person", pPanel);

		aPanel = new JPanel();
		aPanel.setLayout(null);
		aPanel.setSize(650, 565);
		createATab();
		tabPanel.addTab("Account", aPanel);

		frame.setVisible(true);

	}

	/**
	 * Person tab first name, last name, gender, year born, id(auto incremented)
	 */
	private void createPTab() {
		pLabel = new JLabel("Name                                       Surname                                 ID");
		pLabel.setSize(new Dimension(600, 20));
		pLabel.setLocation(20, 455);
		pPanel.add(pLabel);

		addPName = new JTextField();
		addPName.setSize(new Dimension(100, 20));
		addPName.setLocation(20, 475);
		addPName.setToolTipText("Name");
		pPanel.add(addPName);

		addPSurname = new JTextField();
		addPSurname.setSize(new Dimension(100, 20));
		addPSurname.setLocation(170, 475);
		addPSurname.setToolTipText("Surname");
		pPanel.add(addPSurname);

		addPId = new JTextField();
		addPId.setSize(new Dimension(50, 20));
		addPId.setLocation(320, 475);
		addPId.setToolTipText("ID");
		addPId.setEditable(false);
		pPanel.add(addPId);

		btnAddP = new JButton("Add person");
		btnAddP.setSize(120, 20);
		btnAddP.setLocation(20, 505);
		btnAddP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				status.setText(bank.addPerson(addPName.getText(), addPSurname.getText()));
				pTable.setModel(tm.CreateModel(bank.getPersons()));
			}
		});
		pPanel.add(btnAddP);

		btnEditP = new JButton("Edit person");
		btnEditP.setSize(120, 20);
		btnEditP.setLocation(255, 505);
		btnEditP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				status.setText(bank.editPerson(Integer.parseInt(addPId.getText()),addPName.getText(), addPSurname.getText()));
				pTable.setModel(tm.CreateModel(bank.getPersons()));
			}
		});
		pPanel.add(btnEditP);

		btnDeleteP = new JButton("Delete person");
		btnDeleteP.setSize(120, 20);
		btnDeleteP.setLocation(490, 505);
		btnDeleteP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				status.setText(bank.deletePerson(Integer.parseInt(addPId.getText())));
				pTable.setModel(tm.CreateModel(bank.getPersons()));
				aTable.setModel(tm.CreateModel(bank.getAccounts()));
			}
		});
		pPanel.add(btnDeleteP);

		pTable = new JTable(tm.CreateModel(bank.getPersons())); // to be
																// completed
		pTable.setSize(590, 430);
		pTable.setEnabled(false);
		pTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = pTable.rowAtPoint(evt.getPoint());
				int col = pTable.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					addPId.setText(pTable.getValueAt(row, 2).toString());

				}
			}
		});

		scrollPTable = new JScrollPane(pTable);
		scrollPTable.setSize(590, 430);
		scrollPTable.setLocation(20, 20);
		pPanel.add(scrollPTable);
	}

	/**
	 * Account tab type, holder, initial sum, year, id(auto incremented)
	 */
	private void createATab() {
		aLabel = new JLabel(
				"Type                                         Main Holder ID                       Initial sum                    Years                             ID");
		aLabel.setSize(600, 20);
		aLabel.setLocation(20, 430);
		aPanel.add(aLabel);

		aType = new DefaultComboBoxModel<String>();
		aType.addElement(new String("Savings"));
		aType.addElement(new String("Spending"));
		addAType = new JComboBox<String>(aType);
		addAType.setSize(100, 20);
		addAType.setSelectedIndex(0);
		addAType.setLocation(20, 450);
		aPanel.add(addAType);

		addAMainHolderId = new JTextField();
		addAMainHolderId.setSize(100, 20);
		addAMainHolderId.setLocation(170, 450);
		addAMainHolderId.setToolTipText("Main Holder ID");
		aPanel.add(addAMainHolderId);

		addASum = new JTextField();
		addASum.setSize(70, 20);
		addASum.setLocation(320, 450);
		addASum.setToolTipText("Initial sum");
		aPanel.add(addASum);

		yearSavings = new DefaultComboBoxModel<String>();
		for (int i = 1; i <= 50; i++) {
			yearSavings.addElement(new String(Integer.toString(i)));
		}
		addAYear = new JComboBox<String>(yearSavings);
		addAYear.setSize(70, 20);
		addAYear.setSelectedIndex(0);
		addAYear.setLocation(440, 450);
		aPanel.add(addAYear);

		addAId = new JTextField();
		addAId.setSize(50, 20);
		addAId.setLocation(560, 450);
		addAId.setToolTipText("ID");
		addAId.setEditable(false);
		aPanel.add(addAId);

		btnAddA = new JButton("Add Account");
		btnAddA.setSize(120, 20);
		btnAddA.setLocation(20, 480);
		btnAddA.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				status.setText(bank.addAccount(Integer.parseInt(addAMainHolderId.getText()), Integer.parseInt(addASum.getText()),addAType.getSelectedIndex(),addAYear.getSelectedIndex()+1));
				aTable.setModel(tm.CreateModel(bank.getAccounts()));
			}
		});
		aPanel.add(btnAddA);

		btnEditA = new JButton("Edit Account");
		btnEditA.setSize(120, 20);
		btnEditA.setLocation(255, 480);
		btnEditA.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
			}
		});
		aPanel.add(btnEditA);

		btnDeleteA = new JButton("Delete Account");
		btnDeleteA.setSize(120, 20);
		btnDeleteA.setLocation(490, 480);
		btnDeleteA.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				status.setText(bank.deleteAccount(Integer.parseInt(addAId.getText())));
				aTable.setModel(tm.CreateModel(bank.getAccounts()));
			}
		});
		aPanel.add(btnDeleteA);

		sum = new JTextField();
		sum.setSize(50, 20);
		sum.setLocation(20, 510);
		sum.setToolTipText("Sum to be deposited or withdrawn");
		aPanel.add(sum);

		btnDep = new JButton("Deposit");
		btnDep.setSize(120, 20);
		btnDep.setLocation(120, 510);
		btnDep.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				status.setText(bank.depositAccount(Integer.parseInt(sum.getText()),Integer.parseInt(addAId.getText())));
				aTable.setModel(tm.CreateModel(bank.getAccounts()));
			}
		});
		aPanel.add(btnDep);

		btnWDraw = new JButton("Withdraw");
		btnWDraw.setSize(120, 20);
		btnWDraw.setLocation(290, 510);
		btnWDraw.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				status.setText(bank.withdrawAccount(Integer.parseInt(sum.getText()),Integer.parseInt(addAId.getText())));
				aTable.setModel(tm.CreateModel(bank.getAccounts()));
			}
		});
		aPanel.add(btnWDraw);

		aTable = new JTable(tm.CreateModel(bank.getAccounts())); // to be completed
		aTable.setSize(590, 405);
		aTable.setEnabled(false);
		aTable.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = aTable.rowAtPoint(evt.getPoint());
				int col = aTable.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					addAId.setText(aTable.getValueAt(row, 2).toString());

				}
			}
		});

		scrollATable = new JScrollPane(aTable);
		scrollATable.setSize(590, 405);
		scrollATable.setLocation(20, 20);
		aPanel.add(scrollATable);
	}

	public static void main(String[] args) throws InterruptedException {
		new GUI();
	}
}
