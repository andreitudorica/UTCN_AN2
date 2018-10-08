
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

import businessLayer.Manager;

public class GUI extends Frame {
	ArrayList<String> queue_strings;
	private JFrame frame;
	private JTabbedPane pnlMain;
	private JPanel pnlCustomers, pnlCustomersAdd, pnlCustomersEdit, pnlCustomersDelete, pnlCustomersGenerateRecipt,
			pnlOrders, pnlProducts, pnlProductsAdd,pnlProductsEdit, pnlProductsDelete, pnlOrdersAdd, pnlOrdersDelete;
	private JTextField txtGenerateReciptCustomerID, txtAddCustomerName, txtAddCustomerSurname, txtEditCustomerID,
			txtEditCustomerName, txtEditCustomerSurname, txtDeleteCustomerID, txtAddOrderCustomerID,
			txtAddOrderProductID, txtDeleteOrderID, txtAddProductName, txtAddProductPrice, txtAddProductQuantity,
			txtEditProductID, txtEditProductName, txtEditProductQuantity, txtEditProductPrice, txtDeleteProductID;
	private JButton btnGenerateReciptCustomer, btnAddCustomer, btnEditCustomer, btnDeleteCustomer, btnAddOrder,
			btnAddProduct, btnEditProduct, btnDeleteProduct, btnDeleteOrder;

	private JTable CustomersTable, ProductsTable, OrdersTable;
	private TableModel tm;

	public GUI() {
		Manager manager = new Manager();
		frame = new JFrame("Depot Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1800, 1000);
		pnlMain = new JTabbedPane();
		tm = new TableModel();

		////////////////////////////////////////////////////////////////////// Customers
		pnlCustomers = new JPanel();
		pnlMain.addTab("Customers", pnlCustomers);
		pnlCustomers.setMaximumSize(new Dimension(800, 800));

		pnlCustomersAdd = new JPanel();
		pnlCustomersDelete = new JPanel();
		pnlCustomersEdit = new JPanel();
		pnlCustomersGenerateRecipt = new JPanel();
		pnlCustomers.setLayout(new BoxLayout(pnlCustomers, BoxLayout.Y_AXIS));
		pnlCustomersAdd.setLayout(new BoxLayout(pnlCustomersAdd, BoxLayout.X_AXIS));
		pnlCustomersDelete.setLayout(new BoxLayout(pnlCustomersDelete, BoxLayout.X_AXIS));
		pnlCustomersEdit.setLayout(new BoxLayout(pnlCustomersEdit, BoxLayout.X_AXIS));
		pnlCustomersGenerateRecipt.setLayout(new BoxLayout(pnlCustomersGenerateRecipt, BoxLayout.X_AXIS));

		txtAddCustomerName = new JTextField("Name");
		txtAddCustomerSurname = new JTextField("Surname");
		btnAddCustomer = new JButton("Add customer");
		txtAddCustomerName.setMaximumSize(new Dimension(200, 20));
		txtAddCustomerSurname.setMaximumSize(new Dimension(200, 20));

		btnAddCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					manager.addCustomer(txtAddCustomerName.getText(), txtAddCustomerSurname.getText());
					CustomersTable.setModel(tm.CreateModel(manager.getCustomers()));

				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IntrospectionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		txtEditCustomerID = new JTextField("ID");
		txtEditCustomerName = new JTextField("Name");
		txtEditCustomerSurname = new JTextField("SURNAME");
		btnEditCustomer = new JButton("Edit customer");
		txtEditCustomerID.setMaximumSize(new Dimension(50, 20));
		txtEditCustomerName.setMaximumSize(new Dimension(200, 20));
		txtEditCustomerSurname.setMaximumSize(new Dimension(200, 20));
		pnlCustomers.setMaximumSize(new Dimension(800, 800));
		btnEditCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.editCustomer(Integer.parseInt(txtEditCustomerID.getText()), txtEditCustomerName.getText(),
						txtEditCustomerSurname.getText());
				CustomersTable.setModel(tm.CreateModel(manager.getCustomers()));
			}
		});

		txtDeleteCustomerID = new JTextField("ID");
		txtDeleteCustomerID.setMaximumSize(new Dimension(50, 20));
		btnDeleteCustomer = new JButton("Delete customer");
		btnDeleteCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.deleteCustomer(Integer.parseInt(txtDeleteCustomerID.getText()));
				CustomersTable.setModel(tm.CreateModel(manager.getCustomers()));
			}
		});

		txtGenerateReciptCustomerID = new JTextField("ID");
		txtGenerateReciptCustomerID.setMaximumSize(new Dimension(50, 20));
		btnGenerateReciptCustomer = new JButton("Generate Recipt");
		btnGenerateReciptCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.generateRecipt(Integer.parseInt(txtGenerateReciptCustomerID.getText()));
			}
		});

		CustomersTable = new JTable(tm.CreateModel(manager.getCustomers()));

		pnlCustomersGenerateRecipt.add(txtGenerateReciptCustomerID);
		pnlCustomersGenerateRecipt.add(btnGenerateReciptCustomer);
		pnlCustomersDelete.add(txtDeleteCustomerID);
		pnlCustomersDelete.add(btnDeleteCustomer);
		pnlCustomersEdit.add(txtEditCustomerID);
		pnlCustomersEdit.add(txtEditCustomerName);
		pnlCustomersEdit.add(txtEditCustomerSurname);
		pnlCustomersEdit.add(btnEditCustomer);
		pnlCustomersAdd.add(txtAddCustomerName);
		pnlCustomersAdd.add(txtAddCustomerSurname);
		pnlCustomersAdd.add(btnAddCustomer);
		pnlCustomers.add(pnlCustomersAdd);
		pnlCustomers.add(pnlCustomersEdit);
		pnlCustomers.add(pnlCustomersDelete);
		pnlCustomers.add(pnlCustomersGenerateRecipt);
		pnlCustomers.add(new JScrollPane(CustomersTable));
		//////////////////////////////////////////////////////////////////////////////// Orders
		pnlOrders = new JPanel();
		pnlMain.addTab("Orders", pnlOrders);
		pnlOrders.setMaximumSize(new Dimension(800, 800));

		pnlOrdersAdd = new JPanel();
		pnlOrdersDelete = new JPanel();

		pnlOrders.setLayout(new BoxLayout(pnlOrders, BoxLayout.Y_AXIS));
		pnlOrdersAdd.setLayout(new BoxLayout(pnlOrdersAdd, BoxLayout.X_AXIS));
		pnlOrdersDelete.setLayout(new BoxLayout(pnlOrdersDelete, BoxLayout.X_AXIS));

		txtAddOrderCustomerID = new JTextField("CUST");
		txtAddOrderProductID = new JTextField("PROD");
		btnAddOrder = new JButton("Add Order");
		txtAddOrderCustomerID.setMaximumSize(new Dimension(50, 20));
		txtAddOrderProductID.setMaximumSize(new Dimension(50, 20));

		btnAddOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					manager.addOrder(Integer.parseInt(txtAddOrderCustomerID.getText()),
							Integer.parseInt(txtAddOrderProductID.getText()));
					OrdersTable.setModel(tm.CreateModel(manager.getOrders()));
					ProductsTable.setModel(tm.CreateModel(manager.getProducts()));
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		txtDeleteOrderID = new JTextField("ID");
		txtDeleteOrderID.setMaximumSize(new Dimension(50, 20));
		btnDeleteOrder = new JButton("Delete Order");
		btnDeleteOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.deleteOrder(Integer.parseInt(txtDeleteOrderID.getText()));
				OrdersTable.setModel(tm.CreateModel(manager.getOrders()));
			}
		});
		OrdersTable = new JTable(tm.CreateModel(manager.getOrders()));

		pnlOrdersDelete.add(txtDeleteOrderID);
		pnlOrdersDelete.add(btnDeleteOrder);
		pnlOrdersAdd.add(txtAddOrderCustomerID);
		pnlOrdersAdd.add(txtAddOrderProductID);
		pnlOrdersAdd.add(btnAddOrder);
		pnlOrders.add(pnlOrdersAdd);
		pnlOrders.add(pnlOrdersDelete);
		pnlOrders.add(new JScrollPane(OrdersTable));

		///////////////////////////////////////////////////////////////////////////////// Products
		pnlProducts = new JPanel();
		pnlMain.addTab("Products", pnlProducts);

		pnlProducts.setMaximumSize(new Dimension(800, 800));
		pnlProductsAdd = new JPanel();
		pnlProductsDelete = new JPanel();
		pnlProductsEdit = new JPanel();

		pnlProducts.setLayout(new BoxLayout(pnlProducts, BoxLayout.Y_AXIS));
		pnlProductsAdd.setLayout(new BoxLayout(pnlProductsAdd, BoxLayout.X_AXIS));
		pnlProductsDelete.setLayout(new BoxLayout(pnlProductsDelete, BoxLayout.X_AXIS));
		pnlProductsEdit.setLayout(new BoxLayout(pnlProductsEdit, BoxLayout.X_AXIS));

		txtAddProductName = new JTextField("NAME");
		txtAddProductPrice = new JTextField("PRICE");
		txtAddProductQuantity = new JTextField("QUANTITY");
		btnAddProduct = new JButton("Add Product");
		txtAddProductName.setMaximumSize(new Dimension(200, 20));
		txtAddProductPrice.setMaximumSize(new Dimension(50, 20));
		txtAddProductQuantity.setMaximumSize(new Dimension(70, 20));

		btnAddProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					manager.addProduct(txtAddProductName.getText(), Integer.parseInt(txtAddProductQuantity.getText()),
							Double.parseDouble(txtAddProductPrice.getText()));
					ProductsTable.setModel(tm.CreateModel(manager.getProducts()));
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IntrospectionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		txtEditProductID = new JTextField("ID");
		txtEditProductName = new JTextField("PRODUCT NAME");
		txtEditProductQuantity = new JTextField("QUANTITY");
		txtEditProductPrice = new JTextField("PRICE");
		btnEditProduct = new JButton("Edit Product");
		txtEditProductID.setMaximumSize(new Dimension(50, 20));
		txtEditProductName.setMaximumSize(new Dimension(200, 20));
		txtEditProductQuantity.setMaximumSize(new Dimension(50, 20));
		txtEditProductPrice.setMaximumSize(new Dimension(50, 20));
		pnlProducts.setMaximumSize(new Dimension(800, 800));
		btnEditProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.editProduct(Integer.parseInt(txtEditProductID.getText()), txtEditProductName.getText(),
						Integer.parseInt(txtEditProductQuantity.getText()),
						Double.parseDouble(txtEditProductPrice.getText()));
				ProductsTable.setModel(tm.CreateModel(manager.getProducts()));
			}
		});

		txtDeleteProductID = new JTextField("ID");
		txtDeleteProductID.setMaximumSize(new Dimension(50, 20));
		btnDeleteProduct = new JButton("Delete Product");
		btnDeleteProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.deleteProduct(Integer.parseInt(txtDeleteProductID.getText()));
				ProductsTable.setModel(tm.CreateModel(manager.getProducts()));
			}
		});
		ProductsTable = new JTable(tm.CreateModel(manager.getProducts()));

		pnlProductsDelete.add(txtDeleteProductID);
		pnlProductsDelete.add(btnDeleteProduct);
		pnlProductsEdit.add(txtEditProductID);
		pnlProductsEdit.add(txtEditProductName);
		pnlProductsEdit.add(txtEditProductQuantity);
		pnlProductsEdit.add(txtEditProductPrice);
		pnlProductsEdit.add(btnEditProduct);
		pnlProductsAdd.add(txtAddProductName);
		pnlProductsAdd.add(txtAddProductQuantity);
		pnlProductsAdd.add(txtAddProductPrice);
		pnlProductsAdd.add(btnAddProduct);
		pnlProducts.add(pnlProductsAdd);
		pnlProducts.add(pnlProductsEdit);
		pnlProducts.add(pnlProductsDelete);
		pnlProducts.add(new JScrollPane(ProductsTable));

		frame.add(pnlMain);
		frame.setVisible(true);
	}

	public static void main(String[] args) throws InterruptedException {
		new GUI();
	}
}
