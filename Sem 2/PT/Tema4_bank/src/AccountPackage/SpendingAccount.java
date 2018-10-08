package AccountPackage;

public class SpendingAccount extends Account {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpendingAccount(int sum, int id) {
		super(sum, id);
	}

	public String deposit(int sum) {
		this.setSum(sum + this.getSum());
		this.setChanged();
		this.notifyObservers("In your account " + this.getId() + " the sum " + sum + " has been deposited.");
		return "Sum " + sum + " has been added to the SPENDING account " + this.getId();
	}

	public String withdraw(int sum) {
		if (sum > this.getSum())
			return "Not enough moeny in account " + this.getId();
		this.setSum(this.getSum() - sum);
		this.setChanged();
		this.notifyObservers("From your account " + this.getId() + " the sum " + sum + " has been withdrawn.");
		return "The requested sum has been withdrawn.";
	}
}
