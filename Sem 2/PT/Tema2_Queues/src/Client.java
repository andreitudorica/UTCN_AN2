public class Client {
	private int service_time, waiting_time;

	public Client(int st) {
		this.service_time = st;
		this.waiting_time = 0;
	}

	public int getService_time() {
		return this.service_time;
	}

	public int getWaiting_time() {
		return this.waiting_time;
	}

	public void setService_time(int st) {
		this.service_time = st;
	}

	public void setWaiting_time(int wt) {
		this.waiting_time = wt;
	}
	
	@Override
	public String toString()
	{
		return " waits "+ this.getWaiting_time() + " seconds\n";
	}
}


