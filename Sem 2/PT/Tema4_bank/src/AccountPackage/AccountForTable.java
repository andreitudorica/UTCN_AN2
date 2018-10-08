package AccountPackage;

import PersonPackage.Person;

public class AccountForTable {
	private String type,name;
	private int id,sum;
	private boolean isDeleted;
	public AccountForTable(Person p,Account c)
	{
		this.id=c.getId();
		this.name=p.toString();
		this.sum=c.getSum();
		this.isDeleted=c.isDeleted;
		if(c.getClass()==SavingAccount.class)
			this.type="Saving";
		else
			this.type="Spending";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	
}
