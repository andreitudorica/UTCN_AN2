package dataAccessLayer;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Customer;

public class CustomerDAO extends AbstractDAO<Customer> {
	public CustomerDAO() {
	}

	public List<Customer> findAll() {
		return super.findAll();
	}

	public Customer findById(int id) {
		return super.findById(id);
	}

	public List<Customer> createObjects(ResultSet resultSet) {
		return super.createObjects(resultSet);
	}

	public void insertObjects(Customer instance) throws SQLException, SecurityException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
		super.insertObject(instance);
	}

	public void update(Customer c) {
		super.update(c.getId(), c);
	}
}
