import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
public class Shop {
	private ArrayList<Queue> queues;
	private ArrayList<String> to_be_printed;
	private int peak_waiting_time, peak_hour, average_waiting_time, no_of_queues;
	private int LOS, length_of_simulation, min_arriving_interval, max_arriving_interval, min_service_time,
			max_service_time;
	private GUI gui;
	private String lblSimulationTime;
	private String lblNextClient;
	private String lblAverageTime;
	private String lblPeakWaitingTime;
	private String lblPeakHour;
	private Logger logger;
    FileHandler fh; 
    
	public Shop(int los, int miat, int maat, int mist, int mast, int no_of_queues, ArrayList<String> where_to_print,GUI gui) 
	{
		this.gui=gui;
		this.lblPeakHour=new String("0");
		this.length_of_simulation = los;
		LOS = length_of_simulation;
		this.min_arriving_interval = miat;
		this.max_arriving_interval = maat;
		this.min_service_time = mist;
		this.max_service_time = mast;
		this.to_be_printed = where_to_print;
		this.no_of_queues = no_of_queues;
		this.logger = Logger.getLogger("queues");
		this.queues = new ArrayList<Queue>(no_of_queues);
		for (int i = 0; i < no_of_queues; i++) {
			Queue q = new Queue();
			this.queues.add(q);
		}
		this.peak_hour = 0;
		this.peak_waiting_time = 0;
		this.average_waiting_time = 0;
	}

	public double getAverage() {
		double avrg=this.average_waiting_time;
		avrg*=100;
		avrg=avrg/(this.LOS - this.length_of_simulation) / this.no_of_queues;
		avrg=Math.round(avrg);
		avrg/=100;
		return avrg;
	}

	public int getPeakHour() {
		return this.peak_hour;
	}

	public int getPeakWaitingTime() {
		return this.peak_waiting_time;
	}

	public void Simulate() throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			
			public void run() {
				try {
					fh = new FileHandler("D:/Andrei/Scoala/PT2017_30421_Tudorica_Andrei/Tema2_Queues/log.log");
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
		        logger.addHandler(fh);
		        SimpleFormatter formatter = new SimpleFormatter();  
		        fh.setFormatter(formatter);
				logger.info("Queue simulation");
				int client_arrival = 0;
				while (length_of_simulation > 0) {
					if (client_arrival == 0) {
						client_arrival = (int) (Math.random() * (max_arriving_interval - min_arriving_interval) + 1)
								+ min_arriving_interval;
						int st = (int) (Math.random() * (max_service_time - min_service_time) + 1) + min_service_time;
						Client c = new Client(st);
						addClient(c); 
					} else
						client_arrival--;
					int current_total_waiting_time=0;
					for (Queue q : queues)
						current_total_waiting_time += q.getWaiting_time();
					average_waiting_time+=current_total_waiting_time;
					if(current_total_waiting_time>peak_waiting_time)
					{
						peak_waiting_time=current_total_waiting_time;
						peak_hour=LOS-length_of_simulation;
					}
					next_time();
					send_to_GUI();
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					lblSimulationTime="Simulation Time Remaining:" + Integer.toString(length_of_simulation) + " seconds";
					lblNextClient="Next client:" + Integer.toString(client_arrival) + " seconds";
					lblAverageTime="Average waiting time:" + Double.valueOf(getAverage()).toString() + " seconds";
					lblPeakHour=
							"Peak hour of simulation:" + Integer.valueOf(getPeakHour()).toString() + " seconds";
					lblPeakWaitingTime="Peak waiting time of the simulation:"
							+ Integer.valueOf(getPeakWaitingTime()).toString() + " seconds";

					length_of_simulation--;
				}
				lblSimulationTime="SIMULATION ENDED";
				lblNextClient="SIMULATION ENDED";
			}
		});
		thread.start();

	}

	public void addClient(Client c) {
		int minq = 0, minwt = 10000;
		for (Queue q : this.queues) {
			if (q.getWaiting_time() > this.peak_waiting_time) {
				this.peak_waiting_time = q.getWaiting_time();
				this.peak_hour = this.LOS - this.length_of_simulation;
			}
			if (q.getWaiting_time() < minwt) {
				minwt = q.getWaiting_time();
				minq = this.queues.indexOf(q);
			}
		}
		this.queues.get(minq).addClient(c);
		logger.info("A new client joined queue:" + minq + " at momment:" + (LOS-length_of_simulation)); 
	}

	public void next_time() {
		ArrayList<Thread> threads = new ArrayList<Thread>(no_of_queues);
		for (Queue q : this.queues) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					q.next_time(logger,queues.indexOf(q),(LOS - length_of_simulation));
				}
			});
			threads.add(thread);
			thread.start();
		}
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public synchronized void send_to_GUI() {
		this.to_be_printed = new ArrayList<String>();
		for (int i = 0; i < no_of_queues; i++) {
			String s = new String("QUEUE " + i + "\n\n" + this.queues.get(i).toString());
			this.to_be_printed.add(s);
		}
		gui.rewriteGUI(this.to_be_printed,this.lblNextClient,this.lblSimulationTime,this.lblAverageTime,this.lblPeakHour,this.lblPeakWaitingTime);
	}

}


