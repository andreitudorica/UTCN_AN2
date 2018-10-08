package PersonPackage;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class Person  implements Serializable,Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name, surname;
	private boolean isDeleted;
	private int id;

	public Person(String name, String surname, boolean isDeleted, int id) {
		this.name = name;
		this.surname = surname;
		this.isDeleted = isDeleted;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String toString() {
		return this.name + " " + this.surname;

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("Dear "+this.toString()+","+arg1.toString());
	}
}
