package model;

public class Product {
	private int id, quantity;
	private String name;
	private boolean isDeleted;
	private double price;

	public Product() {
		this.id = 0;
		this.name = null;
		this.quantity = 0;
		this.isDeleted = false;
		this.price=0;
	}

	public Product(int id, String name, int quantity, boolean isDeleted,double price) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.isDeleted = isDeleted;
		this.price=price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
