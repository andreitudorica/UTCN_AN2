import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI extends Frame {
	ArrayList<String> queue_strings;
	private int no_of_queues, length_of_simulation, minarr, maxarr, minserv, maxserv;
	private JFrame frame;
	private JPanel pnlMainPanel, pnlQueues, pnlDataIn, pnlStatistics;
	private JLabel lblNoOfQueues, lblLengthOfSimulation, lblMinArriving, lblMaxArriving, lblMinService, lblMaxService,
			lblNextClient, lblSimulationTime, lblAverageTime, lblPeakWaitingTime, lblPeakHour;
	private JTextField txtMaxService, txtMinService, txtMaxArriving, txtMinArriving, txtLengthOfSimulation,
			txtNoOfQueues = new JTextField();
	private ArrayList<JTextArea> queues;
	private Shop s;

	public void setupQueues() {
		no_of_queues = Integer.parseInt(txtNoOfQueues.getText());
		length_of_simulation = Integer.parseInt(txtLengthOfSimulation.getText());
		minarr = Integer.parseInt(txtMinArriving.getText());
		maxarr = Integer.parseInt(txtMaxArriving.getText());
		minserv = Integer.parseInt(txtMinService.getText());
		maxserv = Integer.parseInt(txtMaxService.getText());
	}

	public void rewriteGUI(ArrayList<String> queues_to_print, String nextClient, String simulationTime,
			String avrg, String ph, String pwt) 
	{
		for (int i = 0; i < queues_to_print.size(); i++)
			this.queues.get(i).setText(queues_to_print.get(i));
		this.lblAverageTime.setText(avrg);
		this.lblSimulationTime.setText(simulationTime);
		this.lblPeakHour.setText(ph);
		this.lblPeakWaitingTime.setText(pwt);
		this.lblNextClient.setText(nextClient);
	}

	public GUI() {
		queue_strings = new ArrayList<String>();
		frame = new JFrame("Queues");
		pnlMainPanel = new JPanel();
		pnlQueues = new JPanel();
		pnlDataIn = new JPanel();
		pnlStatistics = new JPanel();
		lblNoOfQueues = new JLabel("Number of queues:");
		txtNoOfQueues = new JTextField();
		lblLengthOfSimulation = new JLabel("Length of simulation:");
		txtLengthOfSimulation = new JTextField();
		lblMinArriving = new JLabel("Minimum arriving interval between clients:");
		txtMinArriving = new JTextField();
		lblMaxArriving = new JLabel("Maximum arriving interval between clients:");
		txtMaxArriving = new JTextField();
		lblMinService = new JLabel("Minimum service time for clients:");
		txtMinService = new JTextField();
		lblMaxService = new JLabel("Maximum service time for clients:");
		lblNextClient = new JLabel();
		lblSimulationTime = new JLabel();
		txtMaxService = new JTextField();
		lblAverageTime = new JLabel();
		lblPeakWaitingTime = new JLabel();
		lblPeakHour = new JLabel();
		queues = new ArrayList<JTextArea>();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1800, 1000);
		pnlMainPanel.setLayout(new BoxLayout(pnlMainPanel, BoxLayout.Y_AXIS));
		pnlDataIn.setLayout(new BoxLayout(pnlDataIn, BoxLayout.Y_AXIS));
		pnlStatistics.setLayout(new BoxLayout(pnlStatistics, BoxLayout.Y_AXIS));
		pnlQueues.setLayout(new BoxLayout(pnlQueues, BoxLayout.X_AXIS));
		JButton btnSimulate = new JButton("Simulate");
		txtLengthOfSimulation.setMaximumSize(new Dimension(200, 20));
		pnlDataIn.add(lblLengthOfSimulation);
		pnlDataIn.add(txtLengthOfSimulation);
		txtNoOfQueues.setMaximumSize(new Dimension(200, 20));
		pnlDataIn.add(lblNoOfQueues);
		pnlDataIn.add(txtNoOfQueues);
		txtMinArriving.setMaximumSize(new Dimension(200, 20));
		pnlDataIn.add(lblMinArriving);
		pnlDataIn.add(txtMinArriving);
		txtMaxArriving.setMaximumSize(new Dimension(200, 20));
		pnlDataIn.add(lblMaxArriving);
		pnlDataIn.add(txtMaxArriving);

		txtMinService.setMaximumSize(new Dimension(200, 20));
		pnlDataIn.add(lblMinService);
		pnlDataIn.add(txtMinService);

		txtMaxService.setMaximumSize(new Dimension(200, 20));
		pnlDataIn.add(lblMaxService);
		pnlDataIn.add(txtMaxService);

		pnlDataIn.setMaximumSize(new Dimension(300, 220));
		pnlMainPanel.add(pnlDataIn);

		txtLengthOfSimulation.setText("100");
		txtNoOfQueues.setText("4");
		txtMinArriving.setText("5");
		txtMaxArriving.setText("20");
		txtMinService.setText("5");
		txtMaxService.setText("20");

		pnlStatistics.add(lblPeakWaitingTime);
		pnlStatistics.add(lblPeakHour);
		pnlStatistics.add(lblAverageTime);
		GUI thisGUI = this;
		btnSimulate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setupQueues();
				pnlDataIn.setVisible(false);
				pnlDataIn.revalidate();
				no_of_queues = Integer.parseInt(txtNoOfQueues.getText());
				for (int i = 0; i < no_of_queues; i++) {
					JTextArea ta = new JTextArea("queue");
					queues.add(ta);
					ta.setMaximumSize(new Dimension(Math.round(1800 / no_of_queues), 200));
					pnlQueues.add(ta);
					pnlQueues.revalidate();
				}
				length_of_simulation = Integer.parseInt(txtLengthOfSimulation.getText());
				minarr = Integer.parseInt(txtMinArriving.getText());
				maxarr = Integer.parseInt(txtMaxArriving.getText());
				minserv = Integer.parseInt(txtMinService.getText());
				maxserv = Integer.parseInt(txtMaxService.getText());
				pnlMainPanel.revalidate();
				s = new Shop(length_of_simulation, minarr, maxarr, minserv, maxserv, no_of_queues, queue_strings,
						thisGUI);
				try {
					s.Simulate();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		pnlMainPanel.add(btnSimulate);
		pnlMainPanel.add(lblNextClient);
		pnlMainPanel.add(lblSimulationTime);
		pnlMainPanel.add(pnlQueues);
		pnlMainPanel.add(pnlStatistics);
		frame.add(pnlMainPanel);
		frame.setVisible(true);
	}

	public static void main(String[] args) throws InterruptedException {
		new GUI();
	}
}
