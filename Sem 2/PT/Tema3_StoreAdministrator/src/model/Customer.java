package model;

public class Customer {
	private String name, surname;
	private int id;
	private boolean isDeleted;

	public Customer() {
		this.id = 0;
		this.name = null;
		this.surname = null;
		this.isDeleted=false;
	}

	public Customer(int id, String name, String surname,boolean isDeleted) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.isDeleted=isDeleted;
	}

	public String getName() {
		return this.name;
	}

	public String getSurname() {
		return this.surname;
	}

	public boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return Integer.toString(this.id) + ' ' + this.name + ' ' + this.surname + " ---deleted " + this.isDeleted+'\n';
	}
}
