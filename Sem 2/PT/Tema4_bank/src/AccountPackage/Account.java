package AccountPackage;

import java.io.Serializable;
import java.util.Observable;


public abstract class Account extends Observable implements Serializable  {
	private static final long serialVersionUID = 1L;
	private int sum, id;
	protected boolean isDeleted;

	public Account(int sum, int id) {
		this.sum = sum;
		this.id = id;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getId() {
		return id;
	}

	public abstract String deposit(int sum);

	public abstract String withdraw(int sum);

	public String delete() {
		this.isDeleted = true;
		this.setChanged();
		this.notifyObservers("Your account " + this.getId() + " has been deleted");
		return "Account succesfully deleted";
	}
}
