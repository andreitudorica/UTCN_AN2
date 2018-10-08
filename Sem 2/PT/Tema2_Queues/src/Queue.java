import java.util.ArrayList;
import java.util.logging.Logger;

public class Queue {
	private int waiting_time;
	private ArrayList<Client> clients;

	public Queue() {
		this.waiting_time = 0;
		this.clients = new ArrayList<Client>();
	}

	public int getWaiting_time() {
		return this.waiting_time;
	}

	public void addClient(Client c) {
		this.clients.add(c);

		c.setWaiting_time(this.waiting_time);
		this.waiting_time += c.getService_time();
	}

	public void next_time(Logger logger,int q,int t) {
		if (this.waiting_time > 0) {
			this.waiting_time--;
			for (Client c : this.clients) {
				if (c.getWaiting_time() > 0)
					c.setWaiting_time(c.getWaiting_time() - 1);
				else if (c.getService_time() > 0)
					c.setService_time(c.getService_time() - 1);
			}
			Client c = this.clients.get(0);
			if (c.getWaiting_time() == 0 && c.getService_time() == 0) {
				this.clients.remove(c);
				logger.info("A client left queue:" + q + " at momment:" + t);
			}
		}
	}

	@Override
	public String toString() {
		String res = new String("");
		for (Client c : this.clients) {
			if (c != this.clients.get(0))
				res += "client number " + Integer.valueOf(this.clients.indexOf(c)).toString() + c.toString();
			else
				res += "first client is being served. Remaining time:" + c.getService_time() + "\n";
		}
		res += "\nTOTAL WAIT:" + Integer.valueOf(this.waiting_time).toString();
		return res;
	}

}
