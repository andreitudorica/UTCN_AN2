package model;

public class OrderHasProduct {
	private int id;
	private int order_id;
	private int product_id;
	private boolean isDeleted;

	public OrderHasProduct() {
		this.id = 0;
		this.order_id = 0;
		this.product_id = 0;
		this.isDeleted = false;
	}

	public OrderHasProduct(int id, int order, int product, boolean isDeleted) {
		this.id = id;
		this.order_id = order;
		this.product_id = product;
		this.isDeleted = isDeleted;
	}

	public int getId() {
		return id;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

}
