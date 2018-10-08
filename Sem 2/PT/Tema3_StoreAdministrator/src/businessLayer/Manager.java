package businessLayer;

import java.beans.IntrospectionException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

import dataAccessLayer.*;
import model.*;

public class Manager {
	private ArrayList<Customer> customers;
	private ArrayList<Order> orders;
	private ArrayList<Product> products;
	private CustomerDAO customerDAO;
	private OrderDAO orderDAO;
	private ProductDAO productDAO;

	public Manager() {
		customerDAO = new CustomerDAO();
		this.customers = (ArrayList<Customer>) customerDAO.findAll();
		orderDAO = new OrderDAO();
		this.orders = (ArrayList<Order>) orderDAO.findAll();
		productDAO = new ProductDAO();
		this.products = (ArrayList<Product>) productDAO.findAll();

	}

	public ArrayList<Customer> getCustomers() {
		return this.customers;
	}

	public ArrayList<Product> getProducts() {
		return this.products;
	}

	public ArrayList<Order> getOrders() {
		return this.orders;
	}

	public void editCustomer(int id, String name, String surname) {
		for (Customer c : customers) {
			if (c.getId() == id) {
				c.setName(name);
				c.setSurname(surname);
				customerDAO.update(c);
			}
		}
	}

	public void deleteCustomer(int id) {
		for (Customer c : customers) {
			if (c.getId() == id) {
				c.setIsDeleted(true);
				customerDAO.update(c);
			}
		}
	}

	public void addCustomer(String name, String surname)
			throws SQLException, SecurityException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, IntrospectionException, InvocationTargetException {
		int id=0;
		if(customers.size()>0)
			id = customers.get(customers.size() - 1).getId() + 1;
		Customer c = new Customer(id, name, surname, false);
		customers.add(c);
		customerDAO = new CustomerDAO();
		try {
			customerDAO.insertObject(c);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generateRecipt(int id) {
		double TotalPayment = 0;
		Customer customer = new Customer();
		for (Customer c : customers)
			if (c.getId() == id)
				customer = c;
		String fileName = "Recipt for " + customer.getName() + " " + customer.getSurname() + ".txt";
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
			writer.println("The recipt for " + customer.getName() + " " + customer.getSurname());

			for (Order o : orders)
				if (o.getCustomer_id() == id) {
					for (Product p : products) {
						if (p.getId() == o.getProduct_id()) {
							writer.println(p.getName() + "--------" + p.getPrice());
							TotalPayment += p.getPrice();
						}
					}
				}
			writer.println("TOTAL PAYMENT--------" + TotalPayment);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addOrder(int customerID, int productID) {
		int canPlaceOrder = 1;
		Product prod = new Product();
		for (Customer c : customers)
			if (c.getId() == customerID && c.getIsDeleted() == true)
				canPlaceOrder = 0;
		for (Product p : products)
			if (p.getId() == productID) {
				if (p.getIsDeleted() == true || p.getQuantity() == 0)
					canPlaceOrder = 0;
				prod = p;
			}
		if (canPlaceOrder == 1) {
			prod.setQuantity(prod.getQuantity() - 1);
			int id=0;
			if(orders.size()>0)
				id = orders.get(orders.size() - 1).getId() + 1;
			Order o = new Order(id, customerID, productID, false);
			orders.add(o);
			orderDAO = new OrderDAO();
			try {
				orderDAO.insertObject(o);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void deleteOrder(int id) {
		for (Order o : orders) {
			if (o.getId() == id) {
				o.setIsDeleted(true);
				orderDAO.update(o);
			}
		}
	}

	public void editProduct(int id, String name, int quantity, double price) {
		for (Product p : products) {
			if (p.getId() == id) {
				p.setName(name);
				p.setQuantity(quantity);
				p.setPrice(price);
				productDAO.update(p);
			}
		}
	}

	public void deleteProduct(int id) {
		for (Product p : products) {
			if (p.getId() == id) {
				p.setIsDeleted(true);
				productDAO.update(p);
			}
		}
	}

	public void addProduct(String name, int quantity, double d)
			throws SQLException, SecurityException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, IntrospectionException, InvocationTargetException {
		int id=0;
		if(products.size()>0)
			id = products.get(products.size() - 1).getId() + 1;
		Product p = new Product(id, name, quantity, false, d);
		products.add(p);
		productDAO = new ProductDAO();
		try {
			productDAO.insertObject(p);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
