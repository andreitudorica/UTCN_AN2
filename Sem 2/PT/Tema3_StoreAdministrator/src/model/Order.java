package model;

public class Order {
	private int id;
	private int customer_id;
	private int product_id;
	private boolean isDeleted;

	public Order() {
		this.id = 0;
		this.customer_id = 0;
		this.product_id = 0;
		this.isDeleted = false;
	}

	public Order(int id, int order, int product, boolean isDeleted) {
		this.id = id;
		this.customer_id = order;
		this.product_id = product;
		this.isDeleted = isDeleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	

}
